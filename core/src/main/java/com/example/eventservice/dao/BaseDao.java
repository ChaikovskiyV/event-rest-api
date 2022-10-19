package com.example.eventservice.dao;

import com.example.eventservice.entity.BaseEntity;

import java.util.List;

public interface BaseDao<T extends BaseEntity> {

    T findById(long id);

    List<T> findAll(String sortParams);

    T update(T entity);

    boolean delete(T entity);
}