package com.digitalparadise.controller.exceptions.good;

public class LaptopException extends ComputerException {
    public static String NEGATIVE_SCREEN_SIZE= "Screen size cannot be negative.";
    public static String NEGATIVE_USB_AMOUNT = "USB amount cannot be negative.";
    public LaptopException(String message) {
        super(message);
    }
}
