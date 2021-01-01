package com.digitalparadise.model.clients;

import com.digitalparadise.controller.exceptions.user.ClientException;
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
import java.util.UUID;

@Data
@ToString(callSuper = true)

@JsonbNillable(value = true)
public class Client extends User{

    @JsonbProperty
    private String phoneNumber;
    @JsonbProperty
    private Boolean active = true;

    public Client(){

    }

//    public Client(String email, String name, Address address, String phoneNumber) throws UserException {
//        super(email, name, address);
//
//        if (phoneNumber == null)
//            throw new ClientException(ClientException.NULL_FIELD);
//        if (phoneNumber.equals(""))
//            throw new ClientException(ClientException.EMPTY_FIELD);
//
//        this.phoneNumber = phoneNumber;
//        this.active = true;
//    }

    public Client(
                  @JsonbProperty("email") String email,
                  @JsonbProperty("name") String name,
                  @JsonbProperty("address") Address address,
                  @JsonbProperty("phoneNumber") String phoneNumber,
                  @JsonbProperty("password") String password) throws UserException {
        super(email, name, address, password);

//        if (phoneNumber == null)
//            throw new ClientException(ClientException.NULL_FIELD);
//        if (phoneNumber.equals(""))
//            throw new ClientException(ClientException.EMPTY_FIELD);

        this.phoneNumber = phoneNumber;
        this.active = true;
    }

    @Override
    public void changeActivity() {
        this.active = !this.active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }
}
