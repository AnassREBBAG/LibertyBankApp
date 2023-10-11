package com.example.libertybank.repositories;

import com.example.libertybank.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
}
