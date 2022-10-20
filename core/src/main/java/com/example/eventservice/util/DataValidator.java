package com.example.eventservice.util;

public interface DataValidator {
    boolean isEventTopicValid(String topic);

    boolean isEventDescriptionValid(String description);

    boolean isEventDateValid(String date);

    boolean isNameValid(String name);

    boolean isTelephoneNumberValid(String telephoneNumber);

    boolean isEmailValid(String email);

    boolean isNumberValid(long number);
}