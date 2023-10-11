package com.example.libertybank.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {

    private String firstName;
    private String lastName;
    private String cin;
    private String email;
    private String password;



}
