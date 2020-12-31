package com.digitalparadise.web.services;

import com.digitalparadise.controller.exceptions.UserManagerException;
import com.digitalparadise.controller.exceptions.repository.RepositoryException;
import com.digitalparadise.controller.exceptions.repository.UserRepositoryException;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
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

    @GET
    @Path("_self")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findSelf(@Context SecurityContext securityContext) {
        User storedUser;
        try {
            storedUser = userManager.findByEmail(securityContext.getUserPrincipal().getName());
            return Response.ok().entity(storedUser).build();
        } catch (UserRepositoryException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @GET
    @Path("{uuid}")
    @Produces({MediaType.APPLICATION_JSON})  // todo should we remove this method ??
    public Response get(@PathParam("uuid") String uuid) throws UserManagerException {
        UUID userUuid = null;
        try {
            userUuid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }

        User storedUser = null;
        try {

            storedUser = userManager.getUserByUUID(userUuid);
            return Response.ok().entity(storedUser).tag(EntityIdentitySignerVerifier
                    .calculateEntitySignature(storedUser)).build();

        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }


    @GET
    @Path("client/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findClientByUuid(@PathParam("uuid") String uuid) {
        UUID userUuid = null;
        try {
            userUuid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }
        User storedUser = null;
        try {

            storedUser = userManager.getUserByUUID(userUuid);
            if (storedUser instanceof Client)
                return Response.ok().entity(storedUser).tag(EntityIdentitySignerVerifier
                        .calculateEntitySignature(storedUser)).build();

        } catch (RepositoryException e) {
            e.printStackTrace();
        } finally {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("admin/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAdminByUuid(@PathParam("uuid") String uuid) {
        UUID userUuid = null;
        try {
            userUuid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }
        User storedUser = null;
        try {

            storedUser = userManager.getUserByUUID(userUuid);
            if (storedUser instanceof Administrator)
                return Response.ok().entity(storedUser)
                        .tag(EntityIdentitySignerVerifier
                        .calculateEntitySignature(storedUser))
                        .build();

        } catch (RepositoryException e) {
            e.printStackTrace();
        } finally {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("employee/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findEmployeeByUuid(@PathParam("uuid") String uuid) {
        UUID userUuid = null;
        try {
            userUuid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }
        User storedUser = null;
        try {

            storedUser = userManager.getUserByUUID(userUuid);
            if (storedUser instanceof Employee)
                return Response.ok().entity(storedUser).build();

        } catch (RepositoryException e) {
            e.printStackTrace();
        } finally {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @PUT
    @Path("client/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @EntitySignatureValidatorFilterBinding
    public Response updateClient(@PathParam("uuid") String uuid, @HeaderParam("If-Match") @NotNull @NotEmpty String tagValue, Client client) {

        if (client.getActive() == null || client.getName() == null || client.getPassword() == null || client.getEmail() == null || client.getUuid() == null || client.getPhoneNumber() == null || client.getAddress() == null) {
            return Response.status(422).build();
        }

        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(tagValue, client)) {
            return Response.status(406).build();
        }

        UUID userUuid = null;
        try {
            userUuid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }

        try {

            User storedUser = userManager.getUserByUUID(userUuid);
            if (!(storedUser instanceof Client))
                return Response.status(Response.Status.NOT_FOUND).build();

        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            this.userManager.update(userUuid, client);
            return Response.status(204).build();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @PUT
    @Path("admin/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @EntitySignatureValidatorFilterBinding
    public Response updateAdmin(@PathParam("uuid") String uuid, @HeaderParam("If-Match") @NotNull @NotEmpty String tagValue, Administrator admin) {
        if (admin.getIsHeadAdmin() == null || admin.getName() == null || admin.getPassword() == null || admin.getEmail() == null || admin.getUuid() == null || admin.getAddress() == null) {
            return Response.status(422).build();
        }

        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(tagValue, admin)) {
            return Response.status(406).build();
        }

        UUID userUuid = null;
        try {
            userUuid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }

        try {

            User storedUser = userManager.getUserByUUID(userUuid);
            if (!(storedUser instanceof Administrator))
                return Response.status(Response.Status.NOT_FOUND).build();

        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }



        try {
            this.userManager.update(userUuid, admin);
            return Response.status(204).build();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("employee/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @EntitySignatureValidatorFilterBinding
    public Response updateEmployee(@PathParam("uuid") String uuid, @HeaderParam("If-Match") @NotNull @NotEmpty String tagValue, Employee employee) throws Exception {
        if (employee.getEarnings() == null || employee.getName() == null || employee.getPassword() == null || employee.getEmail() == null || employee.getUuid() == null || employee.getAddress() == null) {
            return Response.status(422).build();
        }
//
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(tagValue, employee)) {
            return Response.status(406).build();
        }

        UUID userUuid = null;
        try {
            userUuid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }

        try {

            User storedUser = userManager.getUserByUUID(userUuid);
            if (!(storedUser instanceof Employee))
                return Response.status(Response.Status.NOT_FOUND).build();

        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }



        try {
            this.userManager.update(userUuid, employee);
            return Response.status(204).build();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @POST
    @Path("/client")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response add(Client client) {

        if (client.getActive() == null || client.getName() == null || client.getPassword() == null || client.getEmail() == null  || client.getPhoneNumber() == null || client.getAddress() == null) {
            return Response.status(422).build();
        }

        try {
            this.userManager.add(client);
            return Response.status(201).build();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(409).build();
        }

    }

    @POST
    @Path("/employee")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response add(Employee employee) throws UserManagerException {

        if (employee.getEarnings() == null || employee.getName() == null || employee.getPassword() == null || employee.getEmail() == null  || employee.getAddress() == null) {
            return Response.status(422).build();
        }

        try {
            this.userManager.add(employee);
            return Response.status(201).build();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(409).build();
        }

    }

    @POST
    @Path("/administrator")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response add(Administrator admin) throws UserManagerException {
        if (admin.getIsHeadAdmin() == null || admin.getName() == null || admin.getPassword() == null || admin.getEmail() == null  || admin.getAddress() == null) {
            return Response.status(422).build();
        }

        try {
            this.userManager.add(admin);
            return Response.status(201).build();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(409).build();
        }
    }

}
