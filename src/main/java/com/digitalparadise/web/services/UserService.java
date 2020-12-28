package com.digitalparadise.web.services;

import com.digitalparadise.controller.exceptions.UserManagerException;
import com.digitalparadise.controller.exceptions.repository.RepositoryException;
import com.digitalparadise.controller.managers.UserManager;
import com.digitalparadise.model.clients.Administrator;
import com.digitalparadise.model.clients.Client;
import com.digitalparadise.model.clients.Employee;
import com.digitalparadise.model.entities.User;

import javax.inject.Inject;
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

    @GET
    @Path("{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public User get(@PathParam("uuid") String uuid) throws UserManagerException {
        UUID userUuid = UUID.fromString(uuid);
        try {
            return userManager.getUserByUUID(userUuid);
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new UserManagerException(String.valueOf(Response.Status.NOT_FOUND));
        }
    }

    @POST
    @Path("/client")
    @Consumes({MediaType.APPLICATION_JSON})
    public void add(Client client) throws UserManagerException {
        try {
            this.userManager.add(client);
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new UserManagerException(String.valueOf(Response.Status.CONFLICT));
        }
    }

    @POST
    @Path("/employee")
    @Consumes({MediaType.APPLICATION_JSON})
    public void add(Employee employee) throws UserManagerException {
        try {
            this.userManager.add(employee);
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new UserManagerException(String.valueOf(Response.Status.CONFLICT));
        }
    }

    @POST
    @Path("/administrator")
    @Consumes({MediaType.APPLICATION_JSON})
    public void add(Administrator administrator) throws UserManagerException {
        try {
            this.userManager.add(administrator);
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new UserManagerException(String.valueOf(Response.Status.CONFLICT));
        }
    }

    @PUT
    @Path("/client")
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(Client client) throws UserManagerException {
        try {
            this.userManager.update(client.getUuid(), client);
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new UserManagerException(String.valueOf(Response.Status.CONFLICT));
        }
    }

    @PUT
    @Path("/employee")
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(Employee employee) throws UserManagerException {
        try {
            this.userManager.update(employee.getUuid(), employee);
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new UserManagerException(String.valueOf(Response.Status.CONFLICT));
        }
    }

    @PUT
    @Path("/administrator")
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(Administrator administrator) throws UserManagerException {
        try {
            this.userManager.update(administrator.getUuid(), administrator);
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new UserManagerException(String.valueOf(Response.Status.CONFLICT));
        }
    }


    @DELETE  // todo remove this function
    @Path("{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public void delete(@PathParam("uuid") String uuid) throws UserManagerException {
        UUID userUuid = UUID.fromString(uuid);
        try {
            this.userManager.remove(this.userManager.getUserByUUID(userUuid));
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new UserManagerException(String.valueOf(Response.Status.NOT_FOUND));
        }
    }


}
