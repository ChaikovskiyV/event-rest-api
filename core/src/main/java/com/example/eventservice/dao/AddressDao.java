package com.example.eventservice.dao;

import com.example.eventservice.entity.Address;

import java.util.List;

public interface AddressDao {
    List<Address> findAddressByCity(String city);
}
