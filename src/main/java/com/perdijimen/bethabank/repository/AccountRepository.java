package com.perdijimen.bethabank.repository;

import com.perdijimen.bethabank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByIBAN(String IBAN);

}
