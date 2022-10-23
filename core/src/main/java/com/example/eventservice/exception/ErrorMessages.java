package com.example.eventservice.exception;

public final class ErrorMessages {
    public static final String TIME_LEFT = "Time %s is left, another time has to be chosen.";
    public static final String PLACE_TAKEN = "The place %s has already been taken for this time.";
    public static final String NOT_CORRECT_EVENT_DATA = "Event data %s is not correct";
    public static final String NOT_CORRECT_ID = "Id=%s is not correct.";
    public static final String NOT_FOUND_EVENT = "Event with id=%s is not found.";

    private ErrorMessages() {
    }
}