package com.example.eventservice.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StringDateParserTest {
    private String dateString;

    @BeforeAll
    void setUp() {
        dateString = "18-12 15-01-2098";
    }

    @Test
    void parseStringToDateMethodTest() {
        LocalDateTime expected = LocalDateTime.of(2098, 1, 15, 18, 12);
        LocalDateTime result = StringDateParser.parseStringToDate(dateString);

        assertEquals(expected, result);
    }
}