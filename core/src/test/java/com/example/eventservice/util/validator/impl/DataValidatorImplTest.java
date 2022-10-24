package com.example.eventservice.util.validator.impl;

import com.example.eventservice.dto.AddressDto;
import com.example.eventservice.dto.OrganizerDto;
import com.example.eventservice.util.validator.DataValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataValidatorImplTest {
    private DataValidator dataValidator;
    private List<String> names;
    private List<String> notCorrectNames;
    private List<String> topicList;
    private List<String> notCorrectTopicList;
    private List<String> descriptionList;
    private List<String> notCorrectDescriptionList;
    private List<String> dateStringList;
    private List<String> notCorrectDateStringList;
    private List<String> paramNames;
    private List<String> notCorrectParamNames;
    private int[] numbers;
    private int[] notCorrectNumbers;
    private List<OrganizerDto> organizers;
    private List<OrganizerDto> notCorrectOrganizers;
    private List<AddressDto> addressList;
    private List<AddressDto> notCorrectAddressList;

    @BeforeAll
    void setUp() {
        dataValidator = new DataValidatorImpl();
        names = List.of("you", "name", "First name", "2cellos", "none-stop");
        notCorrectNames = List.of("I", "me", "<new name>", "this name is longer than 20 symbols");
        topicList = List.of("topic", "very interesting topic", "2 ways to access");
        notCorrectTopicList = List.of("top", "very <interesting> topic", "this topic is too long, it is even longer than 50 symbols");
        descriptionList = List.of("this is very interesting and useful event", "description has to contain at least 20 symbols");
        notCorrectDescriptionList = List.of("this text is short", "description has to contain <at least 20> symbols",
                "The length of this description is more than 200 symbols" +
                        "The length of this description is more than 200 symbols" +
                        "The length of this description is more than 200 symbols" +
                        "The length of this description is more than 200 symbols");
        dateStringList = List.of("14-20 15-12-2021", "00-05 01-01-1995", "22-00 12-12-2000", "01-10 31-12-1000");
        notCorrectDateStringList = List.of("24-20 15-12-2021", "00-05 01-21-1995", "22-60 12-12-2000", "01-10 31-12-12", "00-00 5 May 2000");
        paramNames = List.of("cat", "fist param", "eventData desc");
        notCorrectParamNames = List.of("pa", "<param>", "not-correct");
        numbers = new int[]{1, 15, 1983};
        notCorrectNumbers = new int[]{0, -15, -1};
        organizers = List.of(new OrganizerDto("first organizer", "org1@gmail.com", "+375299999999"), new OrganizerDto("second organizer", "org2@gmail.com", "+375333333333"), new OrganizerDto("third organizer", "org3@gmail.com", "+375444444444"));
        notCorrectOrganizers = List.of(new OrganizerDto("first organizer", "org1@gmail.com", "telephone_num"), new OrganizerDto("second organizer", "<org2@gmail.com>", "+375333333333"), new OrganizerDto("third organizer", "org3@gmail.com", "+3754444444440"));
        addressList = List.of(new AddressDto("city", "first street", 22), new AddressDto("city", "second street", 1015));
        notCorrectAddressList = List.of(new AddressDto("city", "first street", 0), new AddressDto("city", "second street", -5));
    }

    @Test
    void isEventTopicValidMethodTestWhenTopicCorrect() {
        topicList.forEach(topic ->
                assertTrue(dataValidator.isEventTopicValid(topic)));
    }

    @Test
    void isEventTopicValidMethodTestWhenTopicNotCorrect() {
        notCorrectTopicList.forEach(topic ->
                assertFalse(dataValidator.isEventTopicValid(topic)));
    }

    @Test
    void isEventDescriptionValidMethodTestWhenDescriptionCorrect() {
        descriptionList.forEach(description ->
                assertTrue(dataValidator.isEventDescriptionValid(description)));
    }

    @Test
    void isEventDescriptionValidMethodTestWhenDescriptionNotCorrect() {
        notCorrectDescriptionList.forEach(description ->
                assertFalse(dataValidator.isEventDescriptionValid(description)));
    }

    @Test
    void isEventDateValidMethodTestWhenDateCorrect() {
        dateStringList.forEach(date ->
                assertTrue(dataValidator.isEventDateValid(date)));
    }

    @Test
    void isEventDateValidMethodTestWhenDateNotCorrect() {
        notCorrectDateStringList.forEach(date ->
                assertFalse(dataValidator.isEventDateValid(date)));
    }

    @Test
    void isNumberValidMethodTestWhenNumberCorrect() {
        for (int number : numbers) {
            assertTrue(dataValidator.isNumberValid(number));
        }
    }

    @Test
    void isNumberValidMethodTestWhenNumberNotCorrect() {
        for (int number : notCorrectNumbers) {
            assertFalse(dataValidator.isNumberValid(number));
        }
    }

    @Test
    void isOrganizerValidMethodTestWhenOrganizerCorrect() {
        organizers.forEach(organizer ->
                assertTrue(dataValidator.isOrganizerValid(organizer)));
    }

    @Test
    void isOrganizerValidMethodTestWhenOrganizerNotCorrect() {
        notCorrectOrganizers.forEach(organizer ->
                assertFalse(dataValidator.isOrganizerValid(organizer)));
    }

    @Test
    void isAddressValidMethodTestWhenAddressCorrect() {
        addressList.forEach(address ->
                assertTrue(dataValidator.isAddressValid(address)));
    }

    @Test
    void isAddressValidMethodTestWhenAddressNotCorrect() {
        notCorrectAddressList.forEach(address ->
                assertFalse(dataValidator.isAddressValid(address)));
    }

    @Test
    void isNameValidMethodTestWhenNameCorrect() {
        names.forEach(name ->
                assertTrue(dataValidator.isNameValid(name)));
    }

    @Test
    void isNameValidMethodTestWhenNameNotCorrect() {
        notCorrectNames.forEach(name ->
                assertFalse(dataValidator.isNameValid(name)));
    }

    @Test
    void isRequestParamNameValidMethodTestWhenCorrect() {
        paramNames.forEach(paramName ->
                assertTrue(dataValidator.isRequestParamNameValid(paramName)));
    }

    @Test
    void isRequestParamNameValidMethodTestWhenNotCorrect() {
        notCorrectParamNames.forEach(paramName ->
                assertFalse(dataValidator.isRequestParamNameValid(paramName)));
    }
}