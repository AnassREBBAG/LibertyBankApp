package com.example.libertybankapp.controllers;

import com.example.libertybankapp.dto.*;
import com.example.libertybankapp.services.UserService;
import com.example.libertybankapp.user.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserService userService;




    @GetMapping("/checkbalance")
    public ResponseEntity<Long> checkBalance(@AuthenticationPrincipal AppUser appUser){


        return userService.getBalance(appUser.getId());
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody AmountDto amountDto){

        return userService.withdraw(amountDto);
    }

    @GetMapping("/getCustomerInfo")
    public ResponseEntity<CustomerInfoDto> getCustomerInfo(@AuthenticationPrincipal AppUser appUser){
        return userService.getCustomerInfo(appUser.getId());
    }









}