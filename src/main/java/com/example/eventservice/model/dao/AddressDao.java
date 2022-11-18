package com.example.eventservice.model.dao;

import com.example.eventservice.model.entity.Address;

import java.util.List;

public interface AddressDao {
    List<Address> findAddressByCity(String city);
}
