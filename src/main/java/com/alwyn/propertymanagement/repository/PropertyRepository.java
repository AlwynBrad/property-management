package com.alwyn.propertymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alwyn.propertymanagement.entity.PropertyEntity;

public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {

    List<PropertyEntity> findAllByUserEntityId(Long userId);
}
