package com.alwyn.propertymanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alwyn.propertymanagement.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Define a JPA repository for the UserEntity class with Long as the primary key type.
    Optional<UserEntity> findByOwnerEmail(String email);

     // Declare a method to find a user entity by their email address.
    // The "findByOwnerEmail" method is a query method, and it returns an Optional<UserEntity>.
    // It allows searching for a user by their email, which is a unique identifier.
}
    