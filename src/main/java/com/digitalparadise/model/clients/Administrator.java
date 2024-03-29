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
@NoArgsConstructor

@JsonbNillable(value = true)
public class Administrator extends User {
    
    @Getter @Setter
    @JsonbProperty
    private Boolean isHeadAdmin;

//    public Administrator(String email, String name, Address address, Boolean isHeadAdmin) throws UserException {
//        super(email, name, address);
//
//        if (isHeadAdmin == null)
//            throw new AdminException(AdminException.NULL_FIELD);
//
//        //        if (isHeadAdmin.equals(""))
//        //            throw new AdminException(AdminException.EMPTY_FIELD);
//
//        this.isHeadAdmin = isHeadAdmin;
//    }

//    @JsonbCreator
        public Administrator(
                         @JsonbProperty("email") String email,
                         @JsonbProperty("name") String name,
                         @JsonbProperty("address") Address address,
                         @JsonbProperty("isHeadAdmin") Boolean isHeadAdmin,
                             @JsonbProperty("password") String password) throws UserException {
        super(email, name, address,password);

        if (isHeadAdmin == null)
            throw new AdminException(AdminException.NULL_FIELD);

//        if (isHeadAdmin.equals(""))
//            throw new AdminException(AdminException.EMPTY_FIELD);

        this.isHeadAdmin = isHeadAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }
}
