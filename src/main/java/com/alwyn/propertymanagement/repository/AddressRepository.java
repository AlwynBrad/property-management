package com.alwyn.propertymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alwyn.propertymanagement.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long>{

    // Define a JPA repository for the AddressEntity class with Long as the primary key type.

     // This repository provides basic CRUD operations for the AddressEntity.
    
}
