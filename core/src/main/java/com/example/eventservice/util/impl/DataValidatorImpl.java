package com.example.eventservice.util.impl;

import com.example.eventservice.util.DataValidator;

public class DataValidatorImpl implements DataValidator {
    private static final String NAME_REGEX = "\\w{3,20}";
    private static final String TOPIC_REGEX = "\\w{5,50}";
    private static final String DESCRIPTION_REGEX = "[^><]{20,200}";
    private static final String DATE_REGEX = "\\d{2}-\\d{2}\\s\\d{2}-\\d{2}-\\d{4}";
    private static final String TELEPHONE_NUM_REGEX = "\\+\\d{12}";
    private static final String EMAIL_REGEX = "[^><].+@\\w{2,}\\.\\w{2,}";

    @Override
    public boolean isEventTopicValid(String topic) {
        return topic.matches(TOPIC_REGEX);
    }

    @Override
    public boolean isEventDescriptionValid(String description) {
        return description.matches(DESCRIPTION_REGEX);
    }

    @Override
    public boolean isEventDateValid(String date) {
        return date.matches(DATE_REGEX);
    }

    @Override
    public boolean isNameValid(String name) {
        return name.matches(NAME_REGEX);
    }

    @Override
    public boolean isTelephoneNumberValid(String telephoneNumber) {
        return telephoneNumber.matches(TELEPHONE_NUM_REGEX);
    }

    @Override
    public boolean isEmailValid(String email) {
        return email.matches(EMAIL_REGEX);
    }

    @Override
    public boolean isNumberValid(long number) {
        return number > 0;
    }
}