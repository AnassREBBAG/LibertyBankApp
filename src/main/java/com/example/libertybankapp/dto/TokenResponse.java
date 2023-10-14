package com.example.libertybankapp.dto;






import com.example.libertybankapp.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class TokenResponse {

    private AppUser user;
    private String token;
}

