package com.digitalparadise.web.services;

import com.digitalparadise.controller.exceptions.ManagerException;

import com.digitalparadise.controller.exceptions.repository.RepositoryException;
import com.digitalparadise.controller.managers.GoodManager;
import com.digitalparadise.model.clients.Client;
import com.digitalparadise.model.entities.Good;

import com.digitalparadise.model.entities.User;
import com.digitalparadise.model.goods.Laptop;
import com.digitalparadise.model.goods.PC;
import com.digitalparadise.web.filters.EntitySignatureValidatorFilterBinding;
import com.digitalparadise.web.utils.EntityIdentitySignerVerifier;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
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
    public Response get(@PathParam("uuid") String uuid) throws ManagerException {
        UUID goodUuid = null;
        try {
            goodUuid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }
        Good storedGood = null;
        try {
            storedGood = this.goodManager.getGoodByUUID(goodUuid);
            return Response.ok().entity(storedGood).tag(EntityIdentitySignerVerifier.calculateEntitySignature(storedGood)).build();

        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/laptop") // todo should we create the similar function for get method with /laptop/{uuid} path ???
    @Consumes({MediaType.APPLICATION_JSON})
    public Response add(Laptop laptop) throws ManagerException {
        if (laptop.getSold() == null || laptop.getGoodName() == null || laptop.getBasePrice() == null || laptop.getCount() == null ||
                laptop.getScreenSize() == null || laptop.getUsbAmount() == null || laptop.getHasCamera() == null) {
            return Response.status(422).build();
        }
        try {
            this.goodManager.add(laptop);
            return Response.status(201).build();
        } catch (RepositoryException e) {
            return Response.status(409).build();
        }

    }


    @POST
    @Path("/PC")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response add(PC pc) throws ManagerException {
        if (pc.getSold() == null || pc.getGoodName() == null || pc.getBasePrice() == null || pc.getCount() == null) {
            return Response.status(422).build();
        }
        try {
            this.goodManager.add(pc);
            return Response.status(201).build();
        } catch (RepositoryException e) {
            return Response.status(409).build();
        }
    }

    @PUT
    @Path("laptop/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @EntitySignatureValidatorFilterBinding
    public Response update(@PathParam("uuid") String uuid, @HeaderParam("If-Match") @NotNull String tagValue, Laptop laptop) {
        if (laptop.getSold() == null || laptop.getGoodName() == null || laptop.getBasePrice() == null || laptop.getCount() == null ||
                laptop.getScreenSize() == null || laptop.getUsbAmount() == null || laptop.getHasCamera() == null) {
            return Response.status(422).build();
        }
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(tagValue, laptop)) {
            return Response.status(406).build();
        }

        UUID goodUuid = null;
        try {
            goodUuid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }

        try {
            Good storedGood = goodManager.getGoodByUUID(goodUuid);
            if (!(storedGood instanceof Laptop))
                return Response.status(Response.Status.NOT_FOUND).build();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            this.goodManager.update(goodUuid, laptop);
            return Response.status(204).build();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }


    }

    @PUT
    @Path("PC/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @EntitySignatureValidatorFilterBinding
    public Response update(@PathParam("uuid") String uuid, @HeaderParam("If-Match") @NotNull String tagValue, PC pc) throws ManagerException {
        if (pc.getSold() == null || pc.getGoodName() == null || pc.getBasePrice() == null || pc.getCount() == null) {
            return Response.status(422).build();
        }
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(tagValue, pc)) {
            return Response.status(406).build();
        }

        UUID goodUuid = null;
        try {
            goodUuid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }

        try {
            Good storedGood = goodManager.getGoodByUUID(goodUuid);
            if (!(storedGood instanceof PC))
                return Response.status(Response.Status.NOT_FOUND).build();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            this.goodManager.update(goodUuid, pc);
            return Response.status(204).build();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }

    }


    @DELETE
    @Path("{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("uuid") String uuid) throws ManagerException {
        UUID goodUuid = null;
        try {
            goodUuid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }

        try {
            this.goodManager.remove(this.goodManager.getGoodByUUID(goodUuid));
            return Response.status(201).build();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
}
