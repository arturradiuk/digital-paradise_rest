package com.digitalparadise.controller.exceptions.repository;

public class OrderRepositoryException extends RepositoryException{
        public static String EXIST_ORDER = "This order already exists";
        public static String NOT_EXIST_ORDER = "This order doesn't exist";
        public static String NOT_EXIST_ORDER_WITH_SUCH_UUID = "There is no order with such uuid in the repository.";
    public OrderRepositoryException(String message) {
        super(message);
    }
}
