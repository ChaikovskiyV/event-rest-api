package com.example.eventservice.dao;

import com.example.eventservice.entity.Organizer;

import java.util.List;

public interface OrganizerDao {
    List<Organizer> findOrganizerByName(String name);
}
