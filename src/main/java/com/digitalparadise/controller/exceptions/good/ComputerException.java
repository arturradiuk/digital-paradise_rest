package com.digitalparadise.controller.exceptions.good;

public class ComputerException extends GoodException {
    public static String NEGATIVE_RAM = "RAM amount cannot be negative.";
    public static String NEGATIVE_SSD = "SSD disk capacity cannot be negative.";
    public ComputerException(String message) {
        super(message);
    }
}
