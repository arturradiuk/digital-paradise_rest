package com.digitalparadise.controller.exceptions.user;

public class EmployeeException extends UserException {
    public static String EMPTY_FIELD = "Empty field in the constructor";
    public static String NULL_FIELD = "Null field in the constructor";

    public EmployeeException(String message) {
        super(message);
    }
}
