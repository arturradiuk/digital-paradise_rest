package com.digitalparadise.model.clients;

import com.digitalparadise.controller.exceptions.user.AdminException;
import com.digitalparadise.controller.exceptions.user.UserException;
import lombok.*;
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
public class Administrator extends User {
    
    @Getter @Setter
    @JsonbProperty
    private Boolean isHeadAdmin;

    public Administrator(String email, String name, Address address, Boolean isHeadAdmin) throws UserException {
        super(email, name, address);

        if (isHeadAdmin == null)
            throw new AdminException(AdminException.NULL_FIELD);

        //        if (isHeadAdmin.equals(""))
        //            throw new AdminException(AdminException.EMPTY_FIELD);

        this.isHeadAdmin = isHeadAdmin;
    }

    @JsonbCreator
    public Administrator(@JsonbProperty("uuid") UUID uuid,
                         @JsonbProperty("email") String email,
                         @JsonbProperty("name") String name,
                         @JsonbProperty("address") Address address,
                         @JsonbProperty("isHeadAdmin") Boolean isHeadAdmin) throws UserException {
        super(uuid, email, name, address);

        if (isHeadAdmin == null)
            throw new AdminException(AdminException.NULL_FIELD);

//        if (isHeadAdmin.equals(""))
//            throw new AdminException(AdminException.EMPTY_FIELD);

        this.isHeadAdmin = isHeadAdmin;
    }
}
