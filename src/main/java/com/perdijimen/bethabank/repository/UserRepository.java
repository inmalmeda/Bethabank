package com.perdijimen.bethabank.repository;

import com.perdijimen.bethabank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);

    List<User> findByDNI(String DNI);
}
