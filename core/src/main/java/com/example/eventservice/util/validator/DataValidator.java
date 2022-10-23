package com.example.eventservice.util.validator;

import com.example.eventservice.dto.AddressDto;
import com.example.eventservice.dto.OrganizerDto;

public interface DataValidator {
    boolean isEventTopicValid(String topic);

    boolean isEventDescriptionValid(String description);

    boolean isEventDateValid(String date);

    boolean isNumberValid(long number);

    boolean isOrganizerValid(OrganizerDto organizer);
    boolean isAddressValid(AddressDto address);
    boolean isNameValid(String name);
    boolean isRequestParamNameValid(String requestParamName);
}