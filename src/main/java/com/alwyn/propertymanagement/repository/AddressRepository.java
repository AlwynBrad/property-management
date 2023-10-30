package com.alwyn.propertymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alwyn.propertymanagement.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long>{
    
}
