package com.example.eventservice.model.dao;

import com.example.eventservice.model.entity.BaseEntity;

import java.util.List;

public interface BaseDao<T extends BaseEntity> {

    T findById(long id);

    List<T> findAll(String sortParams);

    T update(T entity);

    void delete(T entity);
}