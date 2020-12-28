package com.digitalparadise.model.entities;

import com.digitalparadise.controller.exceptions.user.AddressException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Address {
    
    private String street;

    private String number;

    public Address() {
    }

    public Address(String street, String number) throws AddressException {

        if (street == null || number == null)
            throw new AddressException(AddressException.NULL_FIELD);

        if (street.equals("") || number.equals(""))
            throw new AddressException(AddressException.EMPTY_FIELD);

        this.street = street;
        this.number = number;
    }

    @Override
    public String toString() {
        return street + " " + number;
    }
}
