package com.perdijimen.bethabank.repository;

import com.perdijimen.bethabank.model.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRegisterRepository extends JpaRepository<UserRegister, Long> {


    Optional<UserRegister> findByEmail(Optional<String> email);
}
