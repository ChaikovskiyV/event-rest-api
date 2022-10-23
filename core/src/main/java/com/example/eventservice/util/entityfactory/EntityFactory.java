package com.example.eventservice.util.entityfactory;

import com.example.eventservice.dto.BaseDto;
import com.example.eventservice.entity.BaseEntity;

public interface EntityFactory<T extends BaseEntity, E extends BaseDto> {
    T buildEntityFromDto(E dto);
}