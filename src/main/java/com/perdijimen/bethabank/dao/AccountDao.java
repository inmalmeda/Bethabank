package com.perdijimen.bethabank.dao;

import com.perdijimen.bethabank.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountDao {

    Optional<Account> findById(Long id);

    List<Account> findAll(Long idUser,Integer limite, Integer pagina);

    List<Account> findAllByName(String name, Integer limite, Integer pagina);


}
