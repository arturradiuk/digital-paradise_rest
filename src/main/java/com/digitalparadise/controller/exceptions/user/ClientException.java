package com.digitalparadise.controller.exceptions.user;

public class ClientException extends UserException {
    public static String EMPTY_FIELD = "Empty field in the constructor";
    public static String NULL_FIELD = "Null field in the constructor";

    public ClientException(String message) {
        super(message);
    }
}
