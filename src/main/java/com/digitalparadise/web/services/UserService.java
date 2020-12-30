package com.digitalparadise.web.services;

import com.digitalparadise.controller.exceptions.UserManagerException;
import com.digitalparadise.controller.exceptions.repository.RepositoryException;
import com.digitalparadise.controller.managers.UserManager;
import com.digitalparadise.model.clients.Administrator;
import com.digitalparadise.model.clients.Client;
import com.digitalparadise.model.clients.Employee;
import com.digitalparadise.model.entities.User;
import com.digitalparadise.web.filters.EntitySignatureValidatorFilterBinding;
import com.digitalparadise.web.utils.EntityIdentitySignerVerifier;

import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path(value = "/users")
public class UserService {


    @Inject
    UserManager userManager = new UserManager();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getAll() {
        return userManager.getAll();
    }

//    @GET
//    @Path("{uuid}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public User get(@PathParam("uuid") String uuid) throws UserManagerException {
//        UUID userUuid = UUID.fromString(uuid);
//        try {
//            return userManager.getUserByUUID(userUuid);
//        } catch (RepositoryException e) {
//            e.printStackTrace();
//            throw new UserManagerException(String.valueOf(Response.Status.NOT_FOUND));
//        }
//    }

//    @POST
//    @Path("/client")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public void add(Client client) throws UserManagerException {
//        try {
//            this.userManager.add(client);
//        } catch (RepositoryException e) {
//            e.printStackTrace();
//            throw new UserManagerException(String.valueOf(Response.Status.CONFLICT));
//        }
//    }
//
//    @POST
//    @Path("/employee")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public void add(Employee employee) throws UserManagerException {
//        try {
//            this.userManager.add(employee);
//        } catch (RepositoryException e) {
//            e.printStackTrace();
//            throw new UserManagerException(String.valueOf(Response.Status.CONFLICT));
//        }
//    }
//
//    @POST
//    @Path("/administrator")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public void add(Administrator administrator) throws UserManagerException {
//        try {
//            this.userManager.add(administrator);
//        } catch (RepositoryException e) {
//            e.printStackTrace();
//            throw new UserManagerException(String.valueOf(Response.Status.CONFLICT));
//        }
//    }

    @GET
    @Path("client/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findClientByUuid(@PathParam("uuid") String uuid) throws Exception {
        UUID userUuid = UUID.fromString(uuid);
        User storedUser = userManager.getUserByUUID(userUuid);
        Client storedClient = null;
        if (storedUser instanceof Client)
            storedClient = (Client) storedUser;

        if (null == storedClient) {
            throw new Exception("Not found");
        }


        return Response.ok()
                .entity(storedClient)
                .tag(EntityIdentitySignerVerifier.calculateEntitySignature(storedClient))
                .build();
    }

    @GET
    @Path("admin/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAdminByUuid(@PathParam("uuid") String uuid) throws Exception {
        UUID userUuid = UUID.fromString(uuid);
        User storedUser = userManager.getUserByUUID(userUuid);
        Administrator storedAdmin = null;
        if (storedUser instanceof Client)
            storedAdmin = (Administrator) storedUser;

        if (null == storedAdmin) {
            throw new Exception("Not found");
        }
        return Response.ok()
                .entity(storedAdmin)
                .tag(EntityIdentitySignerVerifier.calculateEntitySignature(storedAdmin))
                .build();
    }



    @PUT
    @Path("client/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @EntitySignatureValidatorFilterBinding
    public void updateClient(@PathParam("uuid") String uuid, @HeaderParam("If-Match") @NotNull @NotEmpty String tagValue, Client client) throws Exception {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(tagValue, client)) {
            throw new Exception("Integrity broken");
        }
        UUID userUuid = UUID.fromString(uuid);
        User storedUser = this.userManager.getUserByUUID(userUuid);
        Client storedClient = null;

        if (storedUser instanceof Client)
            storedClient = (Client) storedUser;

        if (null == storedClient) {
            throw new Exception("Not found");
        }

//        client // todo check fields

        this.userManager.update(userUuid, client);

    }

    @PUT
    @Path("admin/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @EntitySignatureValidatorFilterBinding
    public void updateAdmin(@PathParam("uuid") String uuid, @HeaderParam("If-Match") @NotNull @NotEmpty String tagValue, Administrator admin) throws Exception {
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(tagValue, admin)) {
            throw new Exception("Integrity broken");
        }
        UUID userUuid = UUID.fromString(uuid);
        User storedUser = this.userManager.getUserByUUID(userUuid);
        Administrator storedAdmin = null;

        if (storedUser instanceof Administrator)
            storedAdmin = (Administrator) storedUser;

        if (null == storedAdmin) {
            throw new Exception("Not found");
        }

//        client // todo check fields

        this.userManager.update(userUuid, admin);

    }

//    @PUT
//    @Path("/employee")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public void update(Employee employee) throws UserManagerException {
//
//        try {
//            this.userManager.update(employee.getUuid(), employee);
//        } catch (RepositoryException e) {
//            e.printStackTrace();
//            throw new UserManagerException(String.valueOf(Response.Status.CONFLICT));
//        }
//
//    }
//
//    @PUT
//    @Path("/administrator")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public void update(Administrator administrator) throws UserManagerException {
//        try {
//            this.userManager.update(administrator.getUuid(), administrator);
//        } catch (RepositoryException e) {
//            e.printStackTrace();
//            throw new UserManagerException(String.valueOf(Response.Status.CONFLICT));
//        }
//    }
//

//    @DELETE  // todo remove this function
//    @Path("{uuid}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public void delete(@PathParam("uuid") String uuid) throws UserManagerException {
//        UUID userUuid = UUID.fromString(uuid);
//        try {
//            this.userManager.remove(this.userManager.getUserByUUID(userUuid));
//        } catch (RepositoryException e) {
//            e.printStackTrace();
//            throw new UserManagerException(String.valueOf(Response.Status.NOT_FOUND));
//        }
//    }


}
