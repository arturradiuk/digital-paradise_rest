package com.digitalparadise.controller.exceptions.user;

public class AddressException extends Exception {
    public static String EMPTY_FIELD = "Empty field in the constructor";
    public static String NULL_FIELD = "Null field in the constructor";
    public AddressException(String message) {
        super(message);
    }
}
