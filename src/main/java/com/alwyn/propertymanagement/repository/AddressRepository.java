package com.alwyn.propertymanagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.alwyn.propertymanagement.entity.AddressEntity;

public interface AddressRepository extends CrudRepository<AddressEntity, Long>{
    
}
