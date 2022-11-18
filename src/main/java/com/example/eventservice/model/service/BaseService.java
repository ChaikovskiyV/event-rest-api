package com.example.eventservice.model.service;

import com.example.eventservice.model.entity.BaseEntity;

import java.util.List;
import java.util.Map;

public interface BaseService<T extends BaseEntity> {

    T findById(long id);

    List<T> findByParameters(Map<String, String> searchParams);

    void delete(long id);
}