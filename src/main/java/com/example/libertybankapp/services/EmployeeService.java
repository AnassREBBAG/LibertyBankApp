package com.example.libertybankapp.services;


import com.example.libertybankapp.dto.AmountDto;
import com.example.libertybankapp.dto.PutAmountDto;
import com.example.libertybankapp.repositories.UserRepository;
import com.example.libertybankapp.user.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {

    private UserRepository userRepository;

    public ResponseEntity<String> putAmount( PutAmountDto putAmountDto) {

        Optional<AppUser> appUserOptional = userRepository.findByCin(putAmountDto.getCin());

        if(appUserOptional.isEmpty()){
            return ResponseEntity.badRequest().body("User not found");
        }

        AppUser appUser = appUserOptional.get();

        appUser.setAccountBalance(appUser.getAccountBalance() + putAmountDto.getAmount() );

        userRepository.save(appUser);

        return ResponseEntity.ok().body("operation completed successfully");

    }



}
