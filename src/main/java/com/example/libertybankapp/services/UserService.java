package com.example.libertybankapp.services;

import com.example.libertybankapp.configurations.Beans;
import com.example.libertybankapp.dto.UserRequest;
import com.example.libertybankapp.repositories.UserRepository;
import com.example.libertybankapp.user.AppUser;
import com.example.libertybankapp.user.AppUserRole;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class UserService {

    private Beans beans;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;


    private String generateRib(){
        String RIB_MSB = "181336";
        String ACCOUNT_NUMBER_MSB = "21111";
        String RIB_LSB = "000033";

        Integer accountNumberLsb = beans.getAccountNumberLSB() + 1;
        beans.setAccountNumberLSB(accountNumberLsb);
        return RIB_MSB + ACCOUNT_NUMBER_MSB + accountNumberLsb + RIB_LSB;
    }

    public void addNewUser(UserRequest userRequest) {

        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        AppUser appUser = new AppUser(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getCin(), userRequest.getEmail(), encodedPassword, generateRib(), 100L, AppUserRole.CUSTOMER);
        userRepository.save(appUser);
    }

    public ResponseEntity<Long> getBalance(Long id) {

        return ResponseEntity.ok().body(userRepository.getAccountBalance(id));
    }

    public ResponseEntity<String> withdraw(Map<String, Object> request) {

        Optional<AppUser> appUser = userRepository.findById(Long.parseLong(request.get("id").toString()) );
        if(appUser.isEmpty())
            return ResponseEntity.badRequest().body("user not found");
        AppUser appUser_ = appUser.get();
        if(appUser_.getAccountBalance() < Long.parseLong(request.get("amount").toString()))
            return ResponseEntity.ok().body("insufficient balance");
        appUser_.setAccountBalance(appUser_.getAccountBalance() - Long.parseLong( request.get("amount").toString()));

        userRepository.save(appUser_);
        return ResponseEntity.ok().body("operation completed successfully");
    }

    public ResponseEntity<Map<String, String>> getCustomerInfo(Long id) {
        Map<String, String> customerInfo = new HashMap<>();
        AppUser appUser = userRepository.findById(id).get();
        customerInfo.put("fullName", appUser.getFirstName() + ' ' + appUser.getLastName());
        customerInfo.put("rib", appUser.getRib());
        customerInfo.put("accountBalance", appUser.getAccountBalance().toString());
        return ResponseEntity.ok().body(customerInfo);
    }

    public ResponseEntity<String> putAmount(Map<String, Long> request) {

        Optional<AppUser> appUser = userRepository.findById(request.get("id"));

        if(appUser.isEmpty()){
            return ResponseEntity.badRequest().body("User not found");
        }

        AppUser appUser1 = appUser.get();

        appUser1.setAccountBalance(appUser1.getAccountBalance() + request.get("amount") );

        userRepository.save(appUser1);

        return ResponseEntity.ok().body("operation completed successfully");

    }
}