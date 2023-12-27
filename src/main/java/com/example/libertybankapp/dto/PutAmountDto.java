package com.example.libertybankapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PutAmountDto {

    private String cin;
    private Long amount;
}
