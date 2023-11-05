package com.alwyn.propertymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alwyn.propertymanagement.entity.PropertyEntity;

public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {

    // Define a JPA repository for the PropertyEntity class with Long as the primary key type.

    List<PropertyEntity> findAllByUserEntityId(Long userId);

    // Declare a method to find all properties associated with a specific user by their user ID.
    // The "findAllByUserEntityId" method is a query method, and it returns a List<PropertyEntity>.
}
