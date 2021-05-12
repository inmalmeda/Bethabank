package com.perdijimen.bethabank;

import com.perdijimen.bethabank.model.*;
import com.perdijimen.bethabank.repository.CardRepository;
import com.perdijimen.bethabank.repository.CategoryRepository;
import com.perdijimen.bethabank.repository.TransationRepository;
import com.perdijimen.bethabank.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BethabankApplication implements CommandLineRunner {

	final UserRepository userRepository;
	final CardRepository cardRepository;
	final CategoryRepository categoryRepository;
	final TransationRepository transationRepository;

	public BethabankApplication(UserRepository userRepository, CardRepository cardRepository, CategoryRepository categoryRepository, TransationRepository transationRepository) {
		this.userRepository = userRepository;
		this.cardRepository = cardRepository;
		this.categoryRepository = categoryRepository;
		this.transationRepository = transationRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BethabankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<User> userList = createDataUser();
		List<Card> cardList = createDataCard();
		List<Transaction> transactionList = createDataTransation();
		List<Category> categoryList = createDataCategory();

		relationUserCard(userList, cardList);
		relationCategoryTransation(categoryList,transactionList);

		Account accoun1 = new Account("Cuenta corriente","ES78 23424234234234234234324",200000.00,LocalDate.now(),LocalDate.now());
		Account accoun2 = new Account("Cuenta corriente","ES78 34534535445324543434453",300000.00,LocalDate.now(),LocalDate.now());


	}
	private List<User> createDataUser () {
		List<User> userList = new ArrayList<>();

		userList.add(new User("Inma","Jimenez",LocalDate.now(),"", "inma@email.com", "", "", "","Malaga","Spain",LocalDate.now(), LocalDate.now()));
		userList.add(new User("Miguel","Perdiguero",LocalDate.now(),"", "miguel@email.com", "", "", "","Malaga","Spain",LocalDate.now(), LocalDate.now()));
		return userList;
	}

	private List<Card> createDataCard () {
		List<Card> cardList = new ArrayList<>();

		cardList.add( new Card("34534534353453","333","credit",LocalDate.now(),LocalDate.now(),LocalDate.now(),"2233"));
		cardList.add( new Card("43535435345435","232","debit",LocalDate.now(),LocalDate.now(),LocalDate.now(),"4455"));
		cardList.add(new Card("456565675667676","111","credit",LocalDate.now(),LocalDate.now(),LocalDate.now(),"5566"));
		return cardList;
	}

	private void relationUserCard (List<User> userList, List<Card> cardList) {
		List<Card> cardList1 = Arrays.asList(cardList.get(0) , cardList.get(1));
		List<Card> cardList2 = Arrays.asList(cardList.get(2));

		userList.get(0).setCardList(cardList1);
		userList.get(1).setCardList(cardList2);

		cardList.get(0).setUser(userList.get(0));
		cardList.get(1).setUser(userList.get(0));
		cardList.get(2).setUser(userList.get(1));

		userRepository.save(userList.get(0));
		userRepository.save(userList.get(1));
	}
	private List<Category> createDataCategory () {
		List<Category> categoryList = new ArrayList<>();

		categoryList.add(new Category("Hogar"));
		categoryList.add(new Category("Gasolina"));
		categoryList.add(new Category("Supermercado"));
		categoryList.add(new Category("Colegio"));

		return categoryList;
	}

	private List<Transaction> createDataTransation () {
		List<Transaction> transationList = new ArrayList<>();

		transationList.add(new Transaction(200.35,LocalDate.now(),"" , true));
		transationList.add(new Transaction(150.35,LocalDate.now(),"",true));
		transationList.add(new Transaction(432.45,LocalDate.now(),"",false));

		return transationList;
	}

	private void relationCategoryTransation (List<Category> categoryList, List<Transaction> transactionList) {

		transactionList.get(0).setCategory(categoryList.get(0));
		transactionList.get(1).setCategory(categoryList.get(1));
		transactionList.get(2).setCategory(categoryList.get(1));

		categoryList.get(0).setTransactionList(Arrays.asList(transactionList.get(0)));
		categoryList.get(1).setTransactionList(Arrays.asList(transactionList.get(1),transactionList.get(2)));

		for (Category category: categoryList) {
			categoryRepository.save(category);
		}

		transationRepository.save(transactionList.get(0));
		transationRepository.save(transactionList.get(1));
	}
}
