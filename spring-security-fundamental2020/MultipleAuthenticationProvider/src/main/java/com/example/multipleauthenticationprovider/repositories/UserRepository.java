package com.example.multipleauthenticationprovider.repositories;

import com.example.multipleauthenticationprovider.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
}
