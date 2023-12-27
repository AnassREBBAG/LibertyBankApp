package com.example.libertybankapp.services;

import com.example.libertybankapp.configurations.Beans;
import com.example.libertybankapp.dto.AmountDto;
import com.example.libertybankapp.dto.CustomerInfoDto;
import com.example.libertybankapp.dto.UserRequest;
import com.example.libertybankapp.repositories.UserRepository;
import com.example.libertybankapp.user.AppUser;
import com.example.libertybankapp.user.AppUserRole;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.smartcardio.CardException;
import java.util.*;


@Service
@AllArgsConstructor
public class UserService {

    private Beans beans;

    private CardService cardService;
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

    public void addNewUser(UserRequest userRequest) throws CardException {

        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        AppUser appUser = new AppUser(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getCin(), userRequest.getEmail(), encodedPassword, generateRib(), 100L, AppUserRole.CUSTOMER);

        userRepository.save(appUser);


        cardService.initCard(appUser.getFirstName() + " " + appUser.getLastName(), appUser.getId());

    }

    public ResponseEntity<Long> getBalance(Long id) {

        return ResponseEntity.ok().body(userRepository.getAccountBalance(id));
    }

    public ResponseEntity<String> withdraw(AmountDto amountDto) {

        Optional<AppUser> appUser = userRepository.findById(amountDto.getId());
        if(appUser.isEmpty())
            return ResponseEntity.badRequest().body("user not found");
        AppUser appUser_ = appUser.get();
        if(appUser_.getAccountBalance() < amountDto.getAmount())
            return ResponseEntity.ok().body("insufficient balance");
        appUser_.setAccountBalance(appUser_.getAccountBalance() - amountDto.getAmount());

        userRepository.save(appUser_);
        return ResponseEntity.ok().body("operation completed successfully");
    }

    public ResponseEntity<CustomerInfoDto> getCustomerInfo(Long id) {

        CustomerInfoDto customerInfoDto = new CustomerInfoDto();
        AppUser appUser = userRepository.findById(id).get();

        customerInfoDto.setFullName(appUser.getFirstName() + ' ' + appUser.getLastName());
        customerInfoDto.setRib(appUser.getRib());
        customerInfoDto.setAccountBalance(appUser.getAccountBalance());
        return ResponseEntity.ok().body(customerInfoDto);
    }


}