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
import java.util.Objects;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@JsonbNillable(value = true)
public class Employee extends User {

    @JsonbProperty
    private Float earnings;

    public Employee(
                    @JsonbProperty("email") String email,
                    @JsonbProperty("name") String name,
                    @JsonbProperty("address") Address address,
                    @JsonbProperty("earnings") Float earnings,
                    @JsonbProperty("password") String password) throws UserException {
        super( email, name, address, password);

        if (earnings == null)
            throw new EmployeeException(EmployeeException.NULL_FIELD);

        this.earnings = earnings;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }


}
