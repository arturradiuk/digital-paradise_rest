package com.digitalparadise.controller.exceptions.manager;

public class GoodManagerException extends Exception{
    public static String CANNOT_DELETE = "Cannot delete the good that is a part of order.";
    public static String NOT_ENOUGH_GOODS = "There are not enough goods in magazine";
    public GoodManagerException(String message) {
        super(message);
    }
}
