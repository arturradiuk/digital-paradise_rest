package com.digitalparadise.controller.exceptions.user;

public class UserException extends Exception {
    public static String EMPTY_FIELD = "Empty field in the constructor";
    public static String NULL_FIELD = "Null field in the constructor";

    public UserException(String message) {
        super(message);
    }
}
