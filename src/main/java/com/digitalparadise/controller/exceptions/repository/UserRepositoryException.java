package com.digitalparadise.controller.exceptions.repository;

public class UserRepositoryException extends RepositoryException {
    public static String EXIST_USER = "This user already exists";
    public static String NOT_EXIST_USER = "This user doesn't exist";
    public UserRepositoryException(String message) {
        super(message);
    }
}
