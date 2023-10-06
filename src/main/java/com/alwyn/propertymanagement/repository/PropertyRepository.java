package com.alwyn.propertymanagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.alwyn.propertymanagement.entity.PropertyEntity;

public interface PropertyRepository extends CrudRepository<PropertyEntity, Long> {
    
}
