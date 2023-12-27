package com.example.libertybankapp.controllers;


import com.example.libertybankapp.dto.Credentials;
import com.example.libertybankapp.dto.TokenResponse;
import com.example.libertybankapp.dto.UserRequest;
import com.example.libertybankapp.security.JwtUtil;
import com.example.libertybankapp.services.UserService;
import com.example.libertybankapp.user.AppUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.smartcardio.CardException;

@RestController
@AllArgsConstructor
@RequestMapping("/authentication")
public class AuthenticationController {


    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credentials credentials){



        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getEmail(), credentials.getPassword()
                    )
            );
            AppUser appUser = (AppUser) authentication.getPrincipal();

            return ResponseEntity.ok().body(new TokenResponse(appUser, jwtUtil.generateToken(appUser)));
        }
        catch(BadCredentialsException exception) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



    @PostMapping("/newuser")
    public void addUser(@RequestBody UserRequest userRequest) throws CardException {

        userService.addNewUser(userRequest);
    }
}
