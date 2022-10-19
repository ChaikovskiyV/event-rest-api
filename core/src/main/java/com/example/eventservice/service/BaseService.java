package com.example.eventservice.service;

import com.example.eventservice.entity.BaseEntity;

import java.util.List;
import java.util.Map;

public interface BaseService<T extends BaseEntity> {

    T findById(long id);

    List<T> findByParameters(Map<String, String> searchParams);

    boolean delete(long id);
}