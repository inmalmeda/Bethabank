package com.perdijimen.bethabank.controller;


import com.perdijimen.bethabank.model.UserRegister;
import com.perdijimen.bethabank.repository.UserRegisterRepository;
import com.perdijimen.bethabank.services.UserRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping()
public class UserRegisterController {

    private final Logger log = LoggerFactory.getLogger(UserRegisterController.class);

    private final UserRegisterService userRegisterService;
    public final UserRegisterRepository userRegisterRepository;

    public UserRegisterController(UserRegisterService userRegisterService, UserRegisterRepository userRegisterRepository) {
        this.userRegisterService = userRegisterService;

        this.userRegisterRepository = userRegisterRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegister> createExpert(@RequestBody UserRegister user) throws URISyntaxException {
        log.debug("REST request to create an User: {} ", user);
        if (user.getId() != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        UserRegister userDB = userRegisterService.createUser(user);
        return ResponseEntity
                .created(new URI("/register" + userDB.getId()))
                .body(userDB);
    }
}
