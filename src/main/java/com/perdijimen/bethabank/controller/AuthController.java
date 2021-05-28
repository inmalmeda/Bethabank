package com.perdijimen.bethabank.controller;


import com.perdijimen.bethabank.config.AuthenticationRequest;
import com.perdijimen.bethabank.config.AuthenticationResponse;
import com.perdijimen.bethabank.model.User;
import com.perdijimen.bethabank.security.JWTUtil;
import com.perdijimen.bethabank.services.UserService;
import com.perdijimen.bethabank.services.impl.UserDetailsRegisterServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping()
@CrossOrigin(origins = "https://ingenia-bank-perdi8.vercel.app", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
//@CrossOrigin(origins = "http://localhost:3000", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsRegisterServiceImpl userDetailsRegisterService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest request) {

        String md5Hex = DigestUtils.md5Hex(request.getPassword()).toUpperCase();
        request.setPassword(md5Hex);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserDetails userDetails = userDetailsRegisterService.loadUserByUsername(request.getEmail());

        User userLogged = userService.findByEmail(request.getEmail()).get();

        String jwt = jwtUtil.generateToken(userDetails);

        AuthenticationResponse response = new AuthenticationResponse(jwt, userLogged.getName(),
                                                                        userLogged.getLastname(), userLogged.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
