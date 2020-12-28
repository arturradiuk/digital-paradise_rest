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
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor

@JsonbNillable(value = true)
public class Client extends User {

    @JsonbProperty
    private String phoneNumber;
    @JsonbProperty
    private Boolean active = true;

    public Client(String email, String name, Address address, String phoneNumber) throws UserException {
        super(email, name, address);

        if (phoneNumber == null)
            throw new ClientException(ClientException.NULL_FIELD);
        if (phoneNumber.equals(""))
            throw new ClientException(ClientException.EMPTY_FIELD);

        this.phoneNumber = phoneNumber;
        this.active = true;
    }

    @JsonbCreator
    public Client(@JsonbProperty("uuid") UUID uuid,
                  @JsonbProperty("email") String email,
                  @JsonbProperty("name") String name,
                  @JsonbProperty("address") Address address,
                  @JsonbProperty("phoneNumber") String phoneNumber) throws UserException {
        super(uuid, email, name, address);

        if (phoneNumber == null)
            throw new ClientException(ClientException.NULL_FIELD);
        if (phoneNumber.equals(""))
            throw new ClientException(ClientException.EMPTY_FIELD);

        this.phoneNumber = phoneNumber;
        this.active = true;
    }

    @Override
    public void changeActivity() {
        this.active = !this.active;
    }
}
