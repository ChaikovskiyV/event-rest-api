package com.example.eventservice.entity.entityfactory;

import com.example.eventservice.dto.BaseDto;
import com.example.eventservice.entity.BaseEntity;

/**
 * Build entity object from dto object.
 *
 * @param <T> - type an entity object, it has to extend BaseEntity
 * @param <E> - type a dto object, it has to extend BaseDto
 */
public interface EntityFactory<T extends BaseEntity, E extends BaseDto> {
    T buildEntityFromDto(E dto);
}