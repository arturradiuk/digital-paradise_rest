package com.digitalparadise.model.entities;

import com.digitalparadise.controller.exceptions.user.UserException;
import com.digitalparadise.web.SignableEntity;
import com.digitalparadise.web.jsonb.adapters.SerializeStringToEmptyAdapter;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.json.bind.annotation.*;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@ToString(callSuper = true)


@JsonbNillable(value = true)
public abstract class User implements Serializable, SignableEntity {


    @JsonbTransient
    @Override
    public String getSignablePayload() { //todo change returning type to the UUID
        return this.uuid.toString();
    }

    @JsonbProperty
    private String password;

    @JsonbTypeAdapter(SerializeStringToEmptyAdapter.class)
    public String getPassword() {
        return password;
    }

    @JsonbProperty
    private String email;


    @JsonbProperty
    private UUID uuid;


    @JsonbProperty
    private String name;

    @JsonbProperty
    private Address address;

    public void changeActivity() {
    } // todo remove it


//    public User(String email,
//                String name, Address address) throws UserException {
//        if (email == null || address == null)
//            throw new UserException(UserException.NULL_FIELD);
//
//        if (email.equals("") || name.equals(""))
//            throw new UserException(UserException.EMPTY_FIELD);
//
//        this.email = email;
//        this.name = name;
//        this.address = address;
//    }


    @JsonbCreator
    public User(
                @JsonbProperty("email") String email,
                @JsonbProperty("name") String name,
                @JsonbProperty("address") Address address,
                @JsonbProperty("password") String password) throws UserException {

//        if (uuid == null || email == null || address == null)
//        if (email == null || address == null)
//            throw new UserException(UserException.NULL_FIELD);
//
//        if (email.equals("") || name.equals(""))
//            throw new UserException(UserException.EMPTY_FIELD);

        this.uuid = uuid;
        this.email = email;
        this.name = name;
        this.address = address;
        this.password = password;
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
