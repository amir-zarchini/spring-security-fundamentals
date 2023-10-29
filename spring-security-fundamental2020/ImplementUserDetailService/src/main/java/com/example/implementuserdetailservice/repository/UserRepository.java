package com.example.implementuserdetailservice.repository;


import com.example.implementuserdetailservice.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

    Optional<User> findUserByUsername(String username);
}
