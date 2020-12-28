package com.digitalparadise.model.clients;

import com.digitalparadise.controller.exceptions.user.EmployeeException;
import com.digitalparadise.controller.exceptions.user.UserException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.digitalparadise.model.entities.Address;
import com.digitalparadise.model.entities.User;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbNillable;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Entity;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor

@JsonbNillable(value = true)
public class Employee extends User {

    @JsonbProperty
    private Float earnings;


    public Employee(String email,
                     String name,
                    Address address,
                    Float earnings) throws  UserException {
        super(email, name, address);

        if (earnings == null)
            throw new EmployeeException(EmployeeException.NULL_FIELD);

        //        if (earnings.equals(""))
        //            throw new EmployeeException(EmployeeException.EMPTY_FIELD);

        this.earnings = earnings;
    }
    @JsonbCreator
    public Employee(@JsonbProperty("uuid")  UUID uuid,
                    @JsonbProperty("email") String email,
                    @JsonbProperty("name") String name,
                    @JsonbProperty("address") Address address,
                    @JsonbProperty("earnings") Float earnings) throws  UserException {
        super(uuid, email, name, address);

        if (earnings == null)
            throw new EmployeeException(EmployeeException.NULL_FIELD);

//        if (earnings.equals(""))
//            throw new EmployeeException(EmployeeException.EMPTY_FIELD);

        this.earnings = earnings;
    }
}
