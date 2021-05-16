package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.dao.AccountDao;
import com.perdijimen.bethabank.dao.CategoryDao;
import com.perdijimen.bethabank.dao.TransactionDao;
import com.perdijimen.bethabank.model.Category;
import com.perdijimen.bethabank.model.Transaction;
import com.perdijimen.bethabank.model.response.AnalyticResponse;
import com.perdijimen.bethabank.model.response.BalanceAnalyticResponse;
import com.perdijimen.bethabank.model.response.CategoryAnalytic;
import com.perdijimen.bethabank.model.response.CategoryAnalyticResponse;
import com.perdijimen.bethabank.repository.AccountRepository;
import com.perdijimen.bethabank.services.AnalyticService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AnalyticServiceImpl implements AnalyticService {

    private AccountDao accountDao;
    private TransactionDao transactionDao;
    private CategoryDao categoryDao;

    public AnalyticServiceImpl( AccountDao accountDao,
                              TransactionDao transactionDao, CategoryDao categoryDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
        this.categoryDao = categoryDao;
    }


    @Override
    public List<AnalyticResponse> getAnalytics(Long idAccount, Boolean typePeriod) {
        LocalDate startDate;
        LocalDate endDate;
        int year = LocalDate.now().getYear();
        List<AnalyticResponse> analytic = new ArrayList<>();

        if(idAccount != null && accountDao.findById(idAccount).isPresent()){
            //Si es tipo mes
            if(typePeriod){
                startDate = LocalDate.of(year, 1, 1);
                endDate = LocalDate.of(year, 12, 31);
            }else{
                int startYear = accountDao.findById(idAccount).get().getCreated_at().getYear();
                startDate =  LocalDate.of(startYear, 1, 31);
                endDate = LocalDate.of(year, 12, 31);
            }
            List<Transaction> transactionList = transactionDao.getAnalyticTransactions(idAccount, startDate, endDate);

            analytic = typePeriod ? monthAnalytics(transactionList) : yearAnalytics(transactionList);
        }
        return analytic;
    }

    @Override
    public CategoryAnalyticResponse getAnalyticsCategory(Long idAccount) {
        int actualMonth = LocalDate.now().getMonthValue();
        int actualYear =  LocalDate.now().getYear();
        CategoryAnalyticResponse analytic = new CategoryAnalyticResponse(0.0, new ArrayList<>());

        List<Category> categoryList = categoryDao.findAll();

        if(!categoryList.isEmpty() && idAccount != null){

            for (Category category: categoryList) {
                analytic.getCategoryAnalytic().add(new CategoryAnalytic(category.getId(), category.getName(), 0.0));
            }

            List<Transaction> transactionList = transactionDao.getAnalyticTransactionsCategory(idAccount,
                    LocalDate.of(actualYear, actualMonth, 1).minusDays(1));

            if(!transactionList.isEmpty()){
                for (Transaction transaction: transactionList) {

                    Optional<CategoryAnalytic> categorySelect = analytic.getCategoryAnalytic().stream()
                            .filter(c -> transaction.getCategory().getId().equals(c.getIdCategory())).findFirst();

                    double amountTotalCategory =  categorySelect.get().getExpenses();

                    for(int i = 0; i<analytic.getCategoryAnalytic().size(); i++){
                        if(analytic.getCategoryAnalytic().get(i).getIdCategory() == categorySelect.get().getIdCategory()){
                            categorySelect.get().setExpenses(transaction.getAmount() + amountTotalCategory);
                            analytic.getCategoryAnalytic().set(i, categorySelect.get());
                        }
                    }
                    analytic.setTotalExpenses(analytic.getTotalExpenses() + transaction.getAmount());
                }
            }
        }
        return analytic;
    }

    @Override
    public List<BalanceAnalyticResponse> getAnalyticsBalance(Long id, Boolean type, LocalDate start, LocalDate end) {
        List<BalanceAnalyticResponse> analytic = new ArrayList<>();

        if(id != null && type != null){
            start = start == null ? LocalDate.now().minusYears(1): start;
            end = end == null ? LocalDate.now() : end;

            List<Transaction> transactionList = transactionDao.getAnalyticTransactions(id, type, start, end);

            analytic = dateAnalytics(transactionList);
        }

        return analytic;
    }

    private List<BalanceAnalyticResponse> dateAnalytics (List<Transaction> transactionList){

        List<BalanceAnalyticResponse> analytic = new ArrayList<>();
        double inCome = 0.0;
        double expenses = 0.0;
        Long id = 1L;

        if(!transactionList.isEmpty()){
            //Selección la fecha
            int daySelect = transactionList.get(0).getTransaction_date().getDayOfMonth();
            int monthSelect = transactionList.get(0).getTransaction_date().getMonthValue();
            int yearSelect = transactionList.get(0).getTransaction_date().getYear();

            analytic.add(new BalanceAnalyticResponse(id,LocalDate.of(yearSelect,monthSelect,daySelect),0.0));

            for (Transaction transaction: transactionList) {
                //Si la fecha seleccionada es igual que la fecha de la transacción
                if(yearSelect ==  transaction.getTransaction_date().getYear() &&
                        monthSelect == transaction.getTransaction_date().getMonthValue() &&
                        daySelect == transaction.getTransaction_date().getDayOfMonth()){

                    if(transaction.getIncome()){
                        inCome += transaction.getAmount();
                    }else{
                        expenses += transaction.getAmount();
                    }

                }else {
                    inCome = 0.0;
                    expenses = 0.0;
                    daySelect = transaction.getTransaction_date().getDayOfMonth();
                    monthSelect = transaction.getTransaction_date().getMonthValue();
                    yearSelect = transaction.getTransaction_date().getYear();

                    analytic.add(new BalanceAnalyticResponse(++id,LocalDate.of(yearSelect,monthSelect,daySelect),0.0));
                }
                //Cálculo del balance
                double total = transaction.getTotal_amount();
                double subtrac = inCome-expenses;

                Optional<BalanceAnalyticResponse> analitycSelect = analytic.stream()
                        .filter(c -> Objects.equals(transaction.getTransaction_date(), c.getDate())).findFirst();
                analitycSelect.get().setBalance(subtrac + total);
            }
        }
        return analytic;
    }

    private List<AnalyticResponse> monthAnalytics ( List<Transaction> transactionList){

        List<AnalyticResponse> analytic = new ArrayList<>();

        if(!transactionList.isEmpty()){
            //Selección mes de comienzo del analytics
            int monthSelect = transactionList.get(0).getTransaction_date().getMonthValue();
            analytic.add(new AnalyticResponse(LocalDate.of(transactionList.get(0).getTransaction_date().getYear() ,
                    monthSelect , 1),0.0, 0.0));
            for (int i = 0; i<transactionList.size(); i++) {
                //Si el mes seleccionado es igual que el mes de la transacción
                if(monthSelect == transactionList.get(i).getTransaction_date().getMonthValue()){
                    if(transactionList.get(i).getIncome()){
                        analytic.get(analytic.size()-1).setInCome(analytic.get(analytic.size()-1).getInCome() + transactionList.get(i).getAmount());
                    }else{
                        analytic.get(analytic.size()-1).setExpense(analytic.get(analytic.size()-1).getExpense() + transactionList.get(i).getAmount());
                    }
                }else{
                    monthSelect = transactionList.get(i).getTransaction_date().getMonthValue();

                    if (transactionList.get(i).getIncome()){
                        analytic.add(new AnalyticResponse(LocalDate.of(transactionList.get(i).getTransaction_date().getYear() ,
                                monthSelect , 1),transactionList.get(i).getAmount(), 0.0));
                    }else{
                        analytic.add(new AnalyticResponse(LocalDate.of(transactionList.get(i).getTransaction_date().getYear() ,
                                monthSelect , 1),0.0, transactionList.get(i).getAmount()));
                    }
                }
            }
        }
        return analytic;
    }

    private List<AnalyticResponse> yearAnalytics (List<Transaction> transactionList){

        List<AnalyticResponse> analytic = new ArrayList<>();

        if(!transactionList.isEmpty()){
            //Selección año de comienzo del analytics
            int yearSelect = transactionList.get(0).getTransaction_date().getYear();
            analytic.add(new AnalyticResponse(LocalDate.of(yearSelect,1 , 1),0.0, 0.0));
            for (int i = 0; i<transactionList.size(); i++) {
                //Si el año seleccionado es igual que el año de la transacción
                if(yearSelect == transactionList.get(i).getTransaction_date().getYear()){
                    if(transactionList.get(i).getIncome()){
                        analytic.get(analytic.size()-1).setInCome(analytic.get(analytic.size()-1).getInCome() + transactionList.get(i).getAmount());
                    }else{
                        analytic.get(analytic.size()-1).setExpense(analytic.get(analytic.size()-1).getExpense() + transactionList.get(i).getAmount());
                    }
                }else{
                    yearSelect = transactionList.get(i).getTransaction_date().getYear();

                    if (transactionList.get(i).getIncome()){
                        analytic.add(new AnalyticResponse(LocalDate.of(yearSelect ,
                                1 , 1),transactionList.get(i).getAmount(), 0.0));
                    }else{
                        analytic.add(new AnalyticResponse(LocalDate.of(yearSelect ,
                                1 , 1),0.0, transactionList.get(i).getAmount()));
                    }
                }
            }
        }
        return analytic;
    }
}
