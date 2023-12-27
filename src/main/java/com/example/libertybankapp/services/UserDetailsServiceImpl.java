package com.example.libertybankapp.services;

import com.example.libertybankapp.repositories.UserRepository;
import com.example.libertybankapp.user.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        Optional<AppUser> user = userRepository.findByEmail(email);
        return user.orElseThrow(()->new UsernameNotFoundException( "client with email : " + email + " not found"));
    }





}
