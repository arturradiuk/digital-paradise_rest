package com.digitalparadise.web.services;

import com.digitalparadise.controller.exceptions.ManagerException;

import com.digitalparadise.controller.exceptions.repository.RepositoryException;
import com.digitalparadise.controller.managers.GoodManager;
import com.digitalparadise.model.entities.Good;

import com.digitalparadise.model.goods.Laptop;
import com.digitalparadise.model.goods.PC;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path(value = "/goods")
public class GoodService {
    @Inject
    GoodManager goodManager = new GoodManager();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Good> getAll() {
        return goodManager.getAll();
    }

    @GET
    @Path("{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Good get(@PathParam("uuid") String uuid) throws ManagerException {
        UUID goodUuid = UUID.fromString(uuid);
        try {
            return goodManager.getGoodByUUID(goodUuid);
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new ManagerException(String.valueOf(Response.Status.NOT_FOUND));
        }
    }

    @POST
    @Path("/laptop")
    @Consumes({MediaType.APPLICATION_JSON})
    public void add(Laptop laptop) throws ManagerException {
        try {
            this.goodManager.add(laptop);
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new ManagerException(String.valueOf(Response.Status.CONFLICT));
        }
    }


    @POST
    @Path("/PC")
    @Consumes({MediaType.APPLICATION_JSON})
    public void add(PC pc) throws ManagerException {
        try {
            this.goodManager.add(pc);
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new ManagerException(String.valueOf(Response.Status.CONFLICT));
        }
    }

    @PUT
    @Path("/laptop")
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(Laptop laptop) throws ManagerException {
        try {
            this.goodManager.update(laptop.getUuid(), laptop);
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new ManagerException(String.valueOf(Response.Status.CONFLICT));
        }
    }

    @PUT
    @Path("/PC")
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(PC pc) throws ManagerException {
        try {
            this.goodManager.update(pc.getUuid(), pc);
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new ManagerException(String.valueOf(Response.Status.CONFLICT));
        }
    }


    @DELETE
    @Path("{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public void delete(@PathParam("uuid") String uuid) throws ManagerException {
        UUID goodUuid = UUID.fromString(uuid);
        try {
            this.goodManager.remove(this.goodManager.getGoodByUUID(goodUuid));
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new ManagerException(String.valueOf(Response.Status.NOT_FOUND));
        }
    }
}
