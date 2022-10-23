package com.example.eventservice.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringDateParser {
    private static final String DATE_FORMAT = "HH-mm dd-MM-yyyy";

    private StringDateParser() {
    }

    public static LocalDateTime parseStringToDate(String dateStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        return LocalDateTime.parse(dateStr, dateTimeFormatter);
    }
}