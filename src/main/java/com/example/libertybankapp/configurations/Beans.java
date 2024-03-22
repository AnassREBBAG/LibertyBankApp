package com.example.libertybankapp.configurations;


import com.example.libertybankapp.dto.UserRequest;
import com.example.libertybankapp.repositories.UserRepository;
import com.example.libertybankapp.services.UserService;
import com.example.libertybankapp.user.AppUser;
import com.example.libertybankapp.user.AppUserRole;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
@Data
public class Beans implements CommandLineRunner {

    private Integer accountNumberLSB = 1000000;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {

        Optional<AppUser> optionalAdmin = userRepository.findAppUserByAppUserRole(AppUserRole.ADMIN);

        if(optionalAdmin.isEmpty()){
            String password = passwordEncoder.encode("admin");
            AppUser admin = new AppUser("admin", "admin", "FB131084", "admin@libertybank.com", password, AppUserRole.ADMIN );
            userRepository.save(admin);
        }

        Optional<AppUser> optionalEmployee = userRepository.findAppUserByAppUserRole(AppUserRole.EMPLOYEE);

        if(optionalEmployee.isEmpty()){
            String password = passwordEncoder.encode("employee");
            AppUser employee_1 = new AppUser("agent", "banquier", "A123456", "banquier@mail.com", password, AppUserRole.EMPLOYEE);
            userRepository.save(employee_1);
        }

        Optional<AppUser> optionalUser = userRepository.findByEmail("anass@mail.com");

        if(optionalUser.isEmpty()){

            UserRequest request = new UserRequest("anass", "rebbag", "GN235475","anass@mail.com","anass");
            userService.addNewUser(request);
        }
    }
}
