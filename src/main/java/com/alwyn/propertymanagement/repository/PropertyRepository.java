package com.alwyn.propertymanagement.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.alwyn.propertymanagement.entity.PropertyEntity;

public interface PropertyRepository extends CrudRepository<PropertyEntity, Long> {

    List<PropertyEntity> findAllByUserEntityId(Long userId);
}
