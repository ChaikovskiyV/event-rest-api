package com.example.eventservice.model.dao;

import com.example.eventservice.model.entity.Organizer;

import java.util.List;

public interface OrganizerDao {
    List<Organizer> findOrganizerByName(String name);
}
