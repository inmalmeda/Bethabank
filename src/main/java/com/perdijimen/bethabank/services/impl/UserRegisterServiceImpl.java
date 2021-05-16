package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.model.UserRegister;
import com.perdijimen.bethabank.repository.UserRegisterRepository;
import com.perdijimen.bethabank.services.UserRegisterService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Repository
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    UserRegisterRepository userRegisterRepository;

    private final Logger log = LoggerFactory.getLogger(UserRegisterServiceImpl.class);

    public UserRegisterServiceImpl(UserRegisterRepository userRegisterRepository) {
        this.userRegisterRepository = userRegisterRepository;
    }

    @Override
    public UserRegister createUser(UserRegister user) {
        log.info("createUSer");
        if (ObjectUtils.isEmpty(user))
            return null;


        Optional<String> email = Optional.ofNullable(user.getEmail());
        Optional<UserRegister> emailDB = userRegisterRepository.findByEmail(email);
        Optional<Object> db = emailDB.map(a -> {
            String ala =  a.getEmail();
            return ala;
        });

        String md5Hex = DigestUtils.md5Hex(user.getPassword()).toUpperCase();
        user.setPassword(md5Hex);
        System.out.println("~~~~~~~~~~$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  DB " + db  ) ;
        System.out.println("~~~~~~~~~~$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ USER "  + email) ;

        if (email.equals(db))  {
            return null;
        }
        return userRegisterRepository.save(user);
    }
}
