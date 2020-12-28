package com.digitalparadise.model.entities;

import com.digitalparadise.controller.exceptions.user.UserException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbNillable;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@ToString(callSuper = true)


@JsonbNillable(value = true)
public abstract class User {

    @JsonbProperty
    private UUID uuid;

    @JsonbProperty
    private String email;

    @JsonbProperty
    private String name;

    @JsonbProperty
    private Address address;

    public void changeActivity() {

    }


    public User( String email,
                 String name, Address address) throws UserException {
        if (email == null || address == null)
            throw new UserException(UserException.NULL_FIELD);

        if (email.equals("") || name.equals(""))
            throw new UserException(UserException.EMPTY_FIELD);
        
        this.email = email;
        this.name = name;
        this.address = address;
    }


    @JsonbCreator
    public User(@JsonbProperty("uuid") UUID uuid,
                @JsonbProperty("email")String email,
                @JsonbProperty("name")String name,
                @JsonbProperty("address")Address address) throws UserException {

        if (uuid == null || email == null || address == null)
            throw new UserException(UserException.NULL_FIELD);

        if (email.equals("") || name.equals(""))
            throw new UserException(UserException.EMPTY_FIELD);

        this.uuid = uuid;
        this.email = email;
        this.name = name;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) { // todo remember
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return uuid.equals(user.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    public boolean isEmailEquals(String email) {
        return this.email.equals(email);
    }
}
