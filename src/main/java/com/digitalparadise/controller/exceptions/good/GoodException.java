package com.digitalparadise.controller.exceptions.good;

public class GoodException extends Exception {
    public static String NULL_ARGUMENT = "Null argument value";
    public static String NEGATIVE_PRICE = "Negative price";
    public static String NEGATIVE_AMOUNT = "Negative goods amount";

    public GoodException(String message) {
        super(message);
    }
}
