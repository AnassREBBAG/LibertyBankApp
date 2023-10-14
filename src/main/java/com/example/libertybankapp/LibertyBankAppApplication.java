package com.example.libertybankapp;

import com.example.libertybankapp.repositories.UserRepository;
import com.example.libertybankapp.user.AppUser;
import com.example.libertybankapp.user.AppUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibertyBankAppApplication {




    public static void main(String[] args) {

        SpringApplication.run(LibertyBankAppApplication.class, args);

    }

}
