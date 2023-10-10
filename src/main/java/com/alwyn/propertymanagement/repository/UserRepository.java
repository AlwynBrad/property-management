package com.alwyn.propertymanagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.alwyn.propertymanagement.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    
}
    