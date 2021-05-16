package com.perdijimen.bethabank.controller;


import com.perdijimen.bethabank.config.AuthenticationRequest;
import com.perdijimen.bethabank.config.AuthenticationResponse;
import com.perdijimen.bethabank.security.JWTUtil;
import com.perdijimen.bethabank.services.impl.UserDetailsRegisterServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsRegisterServiceImpl userDetailsRegisterService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/auth")
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest request) {

        String md5Hex = DigestUtils.md5Hex(request.getPassword()).toUpperCase();
        request.setPassword(md5Hex);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserDetails userDetails = userDetailsRegisterService.loadUserByUsername(request.getEmail());

        String jwt = jwtUtil.generateToken(userDetails);

        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);

    }
}
