package com.example.libertybankapp.services;

import com.example.libertybankapp.repositories.UserRepository;
import com.example.libertybankapp.user.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {

        Optional<AppUser> user = userRepository.findByIdentifier(identifier);
        return user.orElseThrow(()->new UsernameNotFoundException( "client with identifier : " + identifier + " not found"));
    }
}
