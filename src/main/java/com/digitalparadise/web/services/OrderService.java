package com.digitalparadise.web.services;

import com.digitalparadise.controller.exceptions.OrderException;
import com.digitalparadise.controller.exceptions.repository.RepositoryException;
import com.digitalparadise.controller.exceptions.repository.UserRepositoryException;
import com.digitalparadise.controller.exceptions.manager.GoodManagerException;
import com.digitalparadise.controller.managers.GoodManager;
import com.digitalparadise.controller.managers.OrderManager;
import com.digitalparadise.controller.managers.UserManager;
import com.digitalparadise.model.clients.Client;
import com.digitalparadise.model.entities.Good;
import com.digitalparadise.model.entities.Order;
import com.digitalparadise.model.entities.User;
import com.digitalparadise.web.filters.EntitySignatureValidatorFilterBinding;
import com.digitalparadise.web.utils.EntityIdentitySignerVerifier;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Path(value = "/orders")
public class OrderService {
    @Inject
    OrderManager orderManager;

    @Inject
    UserManager userManager;

    @Inject
    GoodManager goodManager;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Order> getAll() {
        return orderManager.getAll();
    }

    @GET
    @Path("_self")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findSelf(@Context SecurityContext securityContext) {
        String userEmail = securityContext.getUserPrincipal().getName();
        List<Order> storedUserOrders = orderManager.getAllOrdersForTheEmail(userEmail);
        return Response.ok().entity(storedUserOrders).build();
    }

    @GET
    @Path("{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response get(@PathParam("uuid") String uuid){
        UUID orderUuid = null;
        try {
            orderUuid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }
        Order storedOrder = null;
        try {
            storedOrder = this.orderManager.getOrderByUUID(orderUuid);
            return Response.ok().entity(storedOrder).tag(EntityIdentitySignerVerifier.calculateEntitySignature(storedOrder)).build();

        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/order")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response add(@Context SecurityContext securityContext, String goodsStr) {

        JSONObject jsonObject = null;
        try {
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(goodsStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = (JSONArray) jsonObject.get("goodsStr");
        List<String> goodsUuidStrList = new ArrayList<>();

        for (Object o : jsonArray) {
            goodsUuidStrList.add((String) o);
        }

        Client client = null;
        try {
            User storedUser = userManager.findByEmail(securityContext.getUserPrincipal().getName());
            if (!(storedUser instanceof Client)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            client = (Client) storedUser;
        } catch (UserRepositoryException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (goodsUuidStrList.size() == 0)
            return Response.status(422).build();

        List<Good> goods = null;
        try {
            goods = this.goodManager.getGoods(goodsUuidStrList);
        } catch (RepositoryException | IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (goods.size() == 0) {
            return Response.status(422).build();
        }

        try {
            this.orderManager.createOrder(this.goodManager, goods, client);
            return Response.ok().build();
        } catch (OrderException | RepositoryException | GoodManagerException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }
    }


    @PUT
    @Path("order/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @EntitySignatureValidatorFilterBinding
    public Response update(@PathParam("uuid") String uuid, @HeaderParam("If-Match") @NotNull String tagValue, Order order) {
        if (order.getOrderDateTime() == null || order.getClient() == null || order.getGoods() == null) {
            return Response.status(422).build();
        }
        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(tagValue, order)) {
            return Response.status(406).build();
        }

        UUID orderUuid = null;
        try {
            orderUuid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }

        try {
            Order storedOrder = orderManager.getOrderByUUID(orderUuid);
            if (!(storedOrder instanceof Order))
                return Response.status(Response.Status.NOT_FOUND).build();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            this.orderManager.update(orderUuid, order);
            return Response.status(204).build();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }


    }


    @DELETE
    @Path("{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("uuid") String uuid) {
        UUID orderUuid = null;
        try {
            orderUuid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(422).build();
        }

        try {
            this.orderManager.remove(this.orderManager.getOrderByUUID(orderUuid));
            return Response.status(201).build();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
}
