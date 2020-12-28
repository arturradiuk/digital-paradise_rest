package com.digitalparadise.model.clients;

import com.digitalparadise.controller.exceptions.user.AdminException;
import com.digitalparadise.controller.exceptions.user.UserException;
import lombok.*;
import com.digitalparadise.model.entities.Address;
import com.digitalparadise.model.entities.User;

import java.util.UUID;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Administrator extends User {
    
    @Getter @Setter
    private Boolean isHeadAdmin;

    public Administrator(String email, String name, Address address, Boolean isHeadAdmin) throws UserException {
        super(email, name, address);

        if (isHeadAdmin == null)
            throw new AdminException(AdminException.NULL_FIELD);

        //        if (isHeadAdmin.equals(""))
        //            throw new AdminException(AdminException.EMPTY_FIELD);

        this.isHeadAdmin = isHeadAdmin;
    }
    
    public Administrator(UUID uuid, String email, String name, Address address, Boolean isHeadAdmin) throws UserException {
        super(uuid, email, name, address);

        if (isHeadAdmin == null)
            throw new AdminException(AdminException.NULL_FIELD);

//        if (isHeadAdmin.equals(""))
//            throw new AdminException(AdminException.EMPTY_FIELD);

        this.isHeadAdmin = isHeadAdmin;
    }
}
