package com.example.eventservice.model.dao.impl;

import com.example.eventservice.model.dao.AddressDao;
import com.example.eventservice.model.entity.Address;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.example.eventservice.model.dao.DatabaseQueries.FIND_ADDRESS_BY_CITY;

@Repository
public class AddressDaoImpl implements AddressDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Address> findAddressByCity(String city) {
        return entityManager.createQuery(FIND_ADDRESS_BY_CITY, Address.class)
                .setParameter(1, city)
                .getResultList();
    }
}