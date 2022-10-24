package com.example.eventservice.util.validator.impl;

import com.example.eventservice.dto.AddressDto;
import com.example.eventservice.dto.OrganizerDto;
import com.example.eventservice.util.validator.DataValidator;
import org.springframework.stereotype.Component;

@Component
public class DataValidatorImpl implements DataValidator {
    private static final String NAME_REGEX = "[^><]{3,20}";
    private static final String TOPIC_REGEX = "[^><]{5,50}";
    private static final String DESCRIPTION_REGEX = "[^><]{20,200}";
    private static final String DATE_REGEX = "(([01]\\d)|(2[0-3]))-[0-5]\\d ((0[1-9])|([1-2]\\d)|(3[01]))-((0[1-9])|(1[0-2]))-\\d{4}";
    private static final String TELEPHONE_NUM_REGEX = "\\+\\d{12}";
    private static final String EMAIL_REGEX = "[^><].+@\\w{2,}\\.\\w{2,}";
    private static final String REQUEST_PARAM_NAME_REGEX = "[\\w\\s]{3,}";

    @Override
    public boolean isEventTopicValid(String topic) {
        return topic != null && topic.matches(TOPIC_REGEX);
    }

    @Override
    public boolean isEventDescriptionValid(String description) {
        return description != null && description.matches(DESCRIPTION_REGEX);
    }

    @Override
    public boolean isEventDateValid(String date) {
        return date != null && date.matches(DATE_REGEX);
    }

    @Override
    public boolean isNumberValid(long number) {
        return number > 0;
    }

    @Override
    public boolean isOrganizerValid(OrganizerDto organizer) {
        return isNameValid(organizer.getOrganizerName()) && isEmailValid(organizer.getOrganizerEmail()) &&
                isTelephoneNumberValid(organizer.getOrganizerTelephoneNumber());
    }

    @Override
    public boolean isAddressValid(AddressDto address) {
        return isNameValid(address.getAddressCity()) && isNameValid(address.getAddressStreet()) &&
                isNumberValid(address.getAddressHouseNumber());
    }
    @Override
    public boolean isNameValid(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    @Override
    public boolean isRequestParamNameValid(String requestParamName) {
        return requestParamName != null && requestParamName.matches(REQUEST_PARAM_NAME_REGEX);
    }

    private boolean isTelephoneNumberValid(String telephoneNumber) {
        return telephoneNumber != null && telephoneNumber.matches(TELEPHONE_NUM_REGEX);
    }

    private boolean isEmailValid(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }
}