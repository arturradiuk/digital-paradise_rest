package com.digitalparadise.controller.exceptions.repository;

public class UserRepositoryException extends RepositoryException {
    public static String EXIST_USER = "This user already exists";
    public static String NOT_EXIST_USER = "This user doesn't exist";
    public static String THIS_EMAIL_IS_TAKEN = "This email is already taken";
    public static String NO_SUCH_USER = "There is no user with such uuid";
    public UserRepositoryException(String message) {
        super(message);
    }
}
