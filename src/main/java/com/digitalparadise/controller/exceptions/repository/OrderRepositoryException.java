package com.digitalparadise.controller.exceptions.repository;

public class OrderRepositoryException extends RepositoryException{
        public static String EXIST_ORDER = "This order already exists";
        public static String NOT_EXIST_ORDER = "This order doesn't exist";
    public OrderRepositoryException(String message) {
        super(message);
    }
}
