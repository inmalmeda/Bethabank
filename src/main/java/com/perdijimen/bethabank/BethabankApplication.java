package com.perdijimen.bethabank;

import com.perdijimen.bethabank.model.*;
import com.perdijimen.bethabank.repository.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BethabankApplication implements CommandLineRunner {

	final UserRepository userRepository;
	final CardRepository cardRepository;
	final CategoryRepository categoryRepository;
	final TransactionRepository transactionRepository;
	final AccountRepository accountRepository;

	public BethabankApplication(UserRepository userRepository, CardRepository cardRepository, CategoryRepository categoryRepository, TransactionRepository transactionRepository, AccountRepository accountRepository) {
		this.userRepository = userRepository;
		this.cardRepository = cardRepository;
		this.categoryRepository = categoryRepository;
		this.transactionRepository = transactionRepository;
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
			transactionRepository.save(transaction);
		}

	}


	private List<Account> relationAccount (List<User> userList, List<Card> cardList, List<Transaction> transactionList, List<Account> accounts) {
		List<Account> accountList = accounts;

		accountList.get(0).setCardList(Arrays.asList(cardList.get(0), cardList.get(1)));
		accountList.get(2).setCardList(Arrays.asList(cardList.get(2)));
		accountList.get(0).setTransactionList(Arrays.asList(transactionList.get(0), transactionList.get(1), transactionList.get(2)));
		accountList.get(0).setUserList(Arrays.asList(userList.get(0)));
		accountList.get(1).setUserList(Arrays.asList(userList.get(0), userList.get(1)));
		accountList.get(2).setUserList(Arrays.asList(userList.get(1)));
		accountList.get(0).setTitularUser(userList.get(0));
		accountList.get(1).setTitularUser(userList.get(0));
		accountList.get(2).setTitularUser(userList.get(1));

		return accountList;
	}

	private List<Card> relationCard (List<User> userList, List<Card> cards, List<Transaction> transactionList, List<Account> accountList) {
		List<Card> cardList = cards;

		cardList.get(0).setAccount(accountList.get(0));
		cardList.get(1).setAccount(accountList.get(0));
		cardList.get(2).setAccount(accountList.get(2));
		cardList.get(0).setUser(userList.get(0));
		cardList.get(1).setUser(userList.get(0));
		cardList.get(2).setUser(userList.get(1));
		cardList.get(0).setTransactionList(Arrays.asList(transactionList.get(2)));

		return cardList;
	}

	private List<User> relationUser (List<User> users, List<Card> cards, List<Account> accountList) {
		List<User> userList = users;

		userList.get(0).setCardList(Arrays.asList(cards.get(0), cards.get(1)));
		userList.get(1).setCardList(Arrays.asList(cards.get(2)));
		userList.get(0).setOwnerAccountList(Arrays.asList(accountList.get(0),accountList.get(1)));
		userList.get(1).setOwnerAccountList(Arrays.asList(accountList.get(1),accountList.get(2)));
		userList.get(0).setTitularAccountList(Arrays.asList(accountList.get(0),accountList.get(1)));
		userList.get(1).setTitularAccountList(Arrays.asList(accountList.get(2)));

		return userList;
	}

	private List<Transaction> relationTransation (List<Category> categoryList, List<Transaction> transations,  List<Card> cards, List<Account> accountList) {
		List<Transaction> transactionList = transations;

		transactionList.get(0).setAccount(accountList.get(0));
		transactionList.get(0).setCategory(categoryList.get(0));
		transactionList.get(1).setAccount(accountList.get(0));
		transactionList.get(1).setCategory(categoryList.get(1));
		transactionList.get(2).setAccount(accountList.get(0));
		transactionList.get(2).setCategory(categoryList.get(0));
		transactionList.get(2).setCard(cards.get(0));

		return transactionList;
	}

	private List<Category> relationCategory (List<Category> categories, List<Transaction> transations) {
		List<Category> categoryList = categories;

		categoryList.get(0).setTransactionList(Arrays.asList(transations.get(0),transations.get(2)));
		categoryList.get(1).setTransactionList(Arrays.asList(transations.get(1)));

		return categoryList;
	}

	private List<Account> createDataAccount () {
		List<Account> accountList = new ArrayList<>();

		accountList.add(new Account("Cuenta corriente","ES78 23424234234234234234324",200000.00,LocalDate.of(2009, 10, 5),LocalDate.now()));
		accountList.add(new Account("Cuenta corriente","ES78 34534535445324543434453",300000.00,LocalDate.of(2012, 9, 20),LocalDate.now()));
		accountList.add(new Account("Cuenta ahorro","ES78 45364565465464565465464",50000.00,LocalDate.of(2018, 5, 3),LocalDate.now()));
		return accountList;
	}

	private List<Transaction> createDataTransation () {
		List<Transaction> transationList = new ArrayList<>();

		transationList.add(new Transaction(200.35,LocalDate.of(2021, 5, 1),
				LocalTime.of(11, 00, 59), "" , true, 200050.00, "Ingreso de la mutua"));
		transationList.add(new Transaction(150.35,LocalDate.of(2020, 3, 10),
				LocalTime.of(21, 05, 39),"",false, 199849.65, "Pago alquiler"));
		transationList.add(new Transaction(432.45,LocalDate.of(2021, 5, 12),
				LocalTime.of(9, 15, 19),"",false, 199617.55 , "Pago letra vehiculo"));

		/*
		transationList.add(new Transaction(55.25,LocalDate.now(),"",false));
		transationList.add(new Transaction(23.40,LocalDate.now(),"",true));
		transationList.add(new Transaction(86.20,LocalDate.now(),"",false));
		transationList.add(new Transaction(110.10,LocalDate.now(),"",true));
		transationList.add(new Transaction(10.14,LocalDate.now(),"",false));
		transationList.add(new Transaction(5.35,LocalDate.now(),"",true));
		transationList.add(new Transaction(32.60,LocalDate.now(),"",false));
*/
		return transationList;
	}

	private List<User> createDataUser () {
		List<User> userList = new ArrayList<>();

		userList.add(new User("Inma","Jimenez",LocalDate.now(),"", "inma@email.com", "", "", "","Malaga","Spain",LocalDate.now(), LocalDate.now()));
		userList.add(new User("Miguel","Perdiguero",LocalDate.now(),"", "miguel@email.com", "", "", "","Malaga","Spain",LocalDate.now(), LocalDate.now()));
		userList.add(new User("Patri","Almeda",LocalDate.now(),"", "patri@email.com", "", "", "","Malaga","Spain",LocalDate.now(), LocalDate.now()));
		return userList;
	}

	private List<Card> createDataCard () {
		List<Card> cardList = new ArrayList<>();

		String passwordCard1 = DigestUtils.md5Hex("2233");
		String passwordCard2 = DigestUtils.md5Hex("4455");
		String passwordCard3 = DigestUtils.md5Hex("4400");

		String cvv1 = DigestUtils.md5Hex("333");
		String cvv2 = DigestUtils.md5Hex("232");
		String cvv3 = DigestUtils.md5Hex("282");

		cardList.add( new Card("34534534353453",cvv1,"credit",LocalDate.now(),LocalDate.now(),LocalDate.now(),passwordCard1));
		cardList.add( new Card("43535435345435",cvv2,"debit",LocalDate.now(),LocalDate.now(),LocalDate.now(),passwordCard2));
		cardList.add( new Card("43535435345400",cvv3,"credit",LocalDate.now(),LocalDate.now(),LocalDate.now(),passwordCard3));


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
		categoryList.add(new Category("Otros"));

		return categoryList;
	}
}
