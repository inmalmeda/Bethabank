package com.perdijimen.bethabank;

import com.perdijimen.bethabank.model.*;
import com.perdijimen.bethabank.repository.*;
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
	final AccountRepository accountRepository;

	public BethabankApplication(UserRepository userRepository, CardRepository cardRepository, CategoryRepository categoryRepository, TransationRepository transationRepository, AccountRepository accountRepository) {
		this.userRepository = userRepository;
		this.cardRepository = cardRepository;
		this.categoryRepository = categoryRepository;
		this.transationRepository = transationRepository;
		this.accountRepository = accountRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BethabankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<User> userList = createDataUser();
		List<Account> accountList = createDataAccount();
		List<Card> cardList = createDataCard();
		List<Transaction> transactionList = createDataTransation();
		List<Category> categoryList = createDataCategory();

		accountList = relationAccount(userList, cardList, transactionList, accountList);
		cardList = relationCard(userList, cardList, transactionList, accountList);
		userList = relationUser(userList, cardList, accountList);
		transactionList = relationTransation(categoryList, transactionList, cardList, accountList);
		categoryList = relationCategory(categoryList, transactionList);


		for (User user: userList) {
			userRepository.save(user);
		}

		for (Account account: accountList) {
			accountRepository.save(account);
		}

		for (Card card: cardList) {
			cardRepository.save(card);
		}

		for (Category category: categoryList) {
			categoryRepository.save(category);
		}

		for (Transaction transaction: transactionList) {
			transationRepository.save(transaction);
		}

	}


	private List<Account> relationAccount (List<User> userList, List<Card> cardList, List<Transaction> transactionList, List<Account> accounts) {
		List<Account> accountList = accounts;

		accountList.get(0).setCardList(Arrays.asList(cardList.get(0)));
		accountList.get(0).setTransactionList(Arrays.asList(transactionList.get(0)));
		accountList.get(0).setUserList(Arrays.asList(userList.get(0)));
		accountList.get(0).setTitularUser(userList.get(0));

		return accountList;
	}

	private List<Card> relationCard (List<User> userList, List<Card> cards, List<Transaction> transactionList, List<Account> accountList) {
		List<Card> cardList = cards;

		cardList.get(0).setAccount(accountList.get(0));
		cardList.get(0).setUser(userList.get(0));
		cardList.get(0).setTransactionList(Arrays.asList(transactionList.get(0)));

		return cardList;
	}

	private List<User> relationUser (List<User> users, List<Card> cards, List<Account> accountList) {
		List<User> userList = users;

		userList.get(0).setCardList(Arrays.asList(cards.get(0)));
		userList.get(0).setOwnerAccountList(Arrays.asList(accountList.get(0)));

		return userList;
	}

	private List<Transaction> relationTransation (List<Category> categoryList, List<Transaction> transations,  List<Card> cards, List<Account> accountList) {
		List<Transaction> transactionList = transations;

		transactionList.get(0).setAccount(accountList.get(0));
		transactionList.get(0).setCategory(categoryList.get(0));
		transactionList.get(0).setCard(cards.get(0));

		return transactionList;
	}

	private List<Category> relationCategory (List<Category> categories, List<Transaction> transations) {
		List<Category> categoryList = categories;

		categoryList.get(0).setTransactionList(Arrays.asList(transations.get(0)));

		return categoryList;
	}

	private List<Account> createDataAccount () {
		List<Account> accountList = new ArrayList<>();

		accountList.add(new Account("Cuenta corriente","ES78 23424234234234234234324",200000.00,LocalDate.now(),LocalDate.now()));
		accountList.add(new Account("Cuenta corriente","ES78 34534535445324543434453",300000.00,LocalDate.now(),LocalDate.now()));
		accountList.add(new Account("Cuenta ahorro","ES78 45364565465464565465464",50000.00,LocalDate.now(),LocalDate.now()));
		return accountList;
	}

	private List<Transaction> createDataTransation () {
		List<Transaction> transationList = new ArrayList<>();

		transationList.add(new Transaction(200.35,LocalDate.now(),"" , true));
		transationList.add(new Transaction(150.35,LocalDate.now(),"",true));
		transationList.add(new Transaction(432.45,LocalDate.now(),"",false));
		transationList.add(new Transaction(55.25,LocalDate.now(),"",false));
		transationList.add(new Transaction(23.40,LocalDate.now(),"",true));
		transationList.add(new Transaction(86.20,LocalDate.now(),"",false));
		transationList.add(new Transaction(110.10,LocalDate.now(),"",true));
		transationList.add(new Transaction(10.14,LocalDate.now(),"",false));
		transationList.add(new Transaction(5.35,LocalDate.now(),"",true));
		transationList.add(new Transaction(32.60,LocalDate.now(),"",false));

		return transationList;
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

		return cardList;
	}

	private List<Category> createDataCategory () {
		List<Category> categoryList = new ArrayList<>();

		categoryList.add(new Category("Hogar"));
		categoryList.add(new Category("Gasolina"));
		categoryList.add(new Category("Supermercado"));
		categoryList.add(new Category("Colegio"));
		categoryList.add(new Category("Textil"));
		categoryList.add(new Category("Comercio"));
		categoryList.add(new Category("Restaurante"));

		return categoryList;
	}
}
