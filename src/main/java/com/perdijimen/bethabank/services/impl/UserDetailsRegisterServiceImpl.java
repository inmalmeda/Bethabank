package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.model.UserRegister;
import com.perdijimen.bethabank.repository.UserRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsRegisterServiceImpl implements UserDetailsService {

    @Autowired
    UserRegisterRepository userRegisterRepository;
    // Implementacion de metodos

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Retorna un nuevo usuario de spring security donde ponemos nuestro usuario, el array list
        // se utiliza para poner los tipos de roles que tendra el usuario
        // el codificador de la contraseña es dentro de {} poner noop

        UserRegister user = userRegisterRepository.findByEmail(Optional.ofNullable(email))
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " +  email));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), "{noop}" + user.getPassword() ,new ArrayList<>());

    }
}
