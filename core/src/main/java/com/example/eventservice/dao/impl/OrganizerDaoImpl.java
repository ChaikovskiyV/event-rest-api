package com.example.eventservice.dao.impl;

import com.example.eventservice.dao.OrganizerDao;
import com.example.eventservice.entity.Organizer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import static com.example.eventservice.dao.DatabaseQueries.*;

@Repository
public class OrganizerDaoImpl implements OrganizerDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Organizer> findOrganizerByName(String name) {
        return entityManager.createQuery(FIND_ORGANIZER_BY_NAME, Organizer.class)
                .setParameter(1, name)
                .getResultList();
    }
}