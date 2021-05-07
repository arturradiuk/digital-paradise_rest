package com.digitalparadise.controller.exceptions.manager;

public class UserManagerException extends Exception{
    public static String CANNOT_DELETE_USER = "Cannot delete the user that is a part of the order";
    public UserManagerException(String message){super(message);}
}
