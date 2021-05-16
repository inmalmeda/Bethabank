package com.perdijimen.bethabank.repository;

import com.perdijimen.bethabank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(Optional<String> email);

    Optional<User> findByDNI(String DNI);
}
