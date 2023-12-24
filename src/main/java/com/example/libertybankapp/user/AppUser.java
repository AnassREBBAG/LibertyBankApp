package com.example.libertybankapp.user;


import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstName;
    private String lastName;
    private String cin;
    private String email;
    private String password;

    private String rib;
    private Long accountBalance;

    private AppUserRole appUserRole;

    private String identifier;




    //employee + admin constructor
    public AppUser(String firstName, String lastName, String cin, String email, String password, AppUserRole appUserRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cin = cin;
        this.email  = email;
        this.password = password;
        this.appUserRole = appUserRole;
    }

    //customer constructor
    public AppUser(String firstName, String lastName, String cin, String email, String password, String rib, Long accountBalance, AppUserRole appUserRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cin = cin;
        this.email = email;
        this.password = password;
        this.rib = rib;
        this.accountBalance = accountBalance;
        this.appUserRole = appUserRole;

        identifier = rib.substring(12,18);



    }





























    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(appUserRole.name()));
    }

    @Override
    public String getUsername() {
        return identifier;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
