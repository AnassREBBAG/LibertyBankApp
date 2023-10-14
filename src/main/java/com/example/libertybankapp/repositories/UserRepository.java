package com.example.libertybankapp.repositories;

import com.example.libertybankapp.user.AppUser;
import com.example.libertybankapp.user.AppUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {


    Optional <AppUser>  findAppUserByAppUserRole(AppUserRole appUserRole);


    @Query("select a.accountBalance from AppUser a where a.id = ?1 ")
    Long getAccountBalance(Long id);


    Optional<AppUser> findByRib(String rib);


    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByIdentifier(String identifier);
}
