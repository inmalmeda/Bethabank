package com.perdijimen.bethabank;

import com.perdijimen.bethabank.model.User;
import com.perdijimen.bethabank.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class BethabankApplication implements CommandLineRunner {

	final UserRepository userRepository;

	public BethabankApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BethabankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


		User user1 = new User("Inma","Jimenez",LocalDate.now(),"", "inma@email.com", "", "", "","Malaga","Spain",LocalDate.now(), LocalDate.now());

		userRepository.save(user1);
	}
}
