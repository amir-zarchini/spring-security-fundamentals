package com.example.authorizationserver.repositories;

import com.example.authorizationserver.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

  @Query("SELECT c FROM Client c WHERE c.clientId = :clientId")
  Optional<Client> findByClientId(String clientId);

}
