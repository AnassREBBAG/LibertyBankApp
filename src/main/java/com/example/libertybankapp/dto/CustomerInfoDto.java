package com.example.libertybankapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerInfoDto {
    private String fullName;
    private String rib;
    private Long accountBalance;
}
