package com.example.libertybankapp.controllers;


import com.example.libertybankapp.dto.AmountDto;
import com.example.libertybankapp.dto.PutAmountDto;
import com.example.libertybankapp.services.EmployeeService;
import com.example.libertybankapp.user.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    @PostMapping("/putamount")
    public ResponseEntity<String> putAmount(@RequestBody PutAmountDto putAmountDto){
        return employeeService.putAmount(putAmountDto);
    }

}
