package com.example.libertybank.services;


import com.example.libertybank.configurations.Beans;
import com.example.libertybank.dto.UserRequest;
import com.example.libertybank.repositories.UserRepository;
import com.example.libertybank.user.AppUser;
import com.example.libertybank.user.AppUserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public void addNewUser(UserRequest userRequest) {

        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        AppUser appUser = new AppUser(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getCin(), userRequest.getEmail(), encodedPassword,generaterib(), 100L, AppUserRole.CUSTOMER);
        userRepository.save(appUser);
    }


    //implement rib generator

    private Beans beans;


    private String generaterib(){
        String RIB_MSB = "181336";
        String ACCOUNT_NUMBER_MSB = "21111";
        String RIB_LSB = "000033";

        Integer accountNumberLSB = beans.getAccountNumberLSB() + 1;



        beans.setAccountNumberLSB(accountNumberLSB);

        return RIB_MSB + ACCOUNT_NUMBER_MSB + accountNumberLSB + RIB_LSB;

    }

}
