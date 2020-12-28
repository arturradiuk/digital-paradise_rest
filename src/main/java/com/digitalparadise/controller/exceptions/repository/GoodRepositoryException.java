package com.digitalparadise.controller.exceptions.repository;

public class GoodRepositoryException extends RepositoryException {
    public static String EXIST_GOOD = "This good already exists";
    public static String NOT_EXIST_GOOD = "This good doesn't exist";
    public GoodRepositoryException(String message) {
        super(message);
    }
}
