package com.digitalparadise.controller.exceptions;

public class OrderException extends Exception {
    public static String NULL_FIELD = "Null field in the constructor";
    public static String NULL_Goods = "Goods cannot be null.";
    public static String NULL_Client = "Client cannot be null.";

    public OrderException(String message) {
        super(message);
    }
}
