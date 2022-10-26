package com.example.eventservice.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StringDateParser {
    private static final String DATE_FORMAT = "HH-mm dd-MM-yyyy";

    private StringDateParser() {
    }

    /**
     * Parse string in format "HH-mm dd-MM-yyyy" in LocalDateTime object.
     *
     * @param dateStr - a string in format "HH-mm dd-MM-yyyy".
     * @return -a LocalDateTime object
     * @throws DateTimeParseException - if dateStr doesn't math format "HH-mm dd-MM-yyyy".
     */
    public static LocalDateTime parseStringToDate(String dateStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        return LocalDateTime.parse(dateStr, dateTimeFormatter);
    }
}