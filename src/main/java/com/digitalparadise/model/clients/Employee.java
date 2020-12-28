package com.digitalparadise.model.clients;

import com.digitalparadise.controller.exceptions.user.EmployeeException;
import com.digitalparadise.controller.exceptions.user.UserException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.digitalparadise.model.entities.Address;
import com.digitalparadise.model.entities.User;

import java.util.UUID;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Employee extends User {

    private Float earnings;

    public Employee(String email, String name, Address address, Float earnings) throws  UserException {
        super(email, name, address);

        if (earnings == null)
            throw new EmployeeException(EmployeeException.NULL_FIELD);

        //        if (earnings.equals(""))
        //            throw new EmployeeException(EmployeeException.EMPTY_FIELD);

        this.earnings = earnings;
    }
    
    public Employee(UUID uuid, String email, String name, Address address, Float earnings) throws  UserException {
        super(uuid, email, name, address);

        if (earnings == null)
            throw new EmployeeException(EmployeeException.NULL_FIELD);

//        if (earnings.equals(""))
//            throw new EmployeeException(EmployeeException.EMPTY_FIELD);

        this.earnings = earnings;
    }
}
