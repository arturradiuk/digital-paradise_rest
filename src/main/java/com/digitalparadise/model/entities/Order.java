package com.digitalparadise.model.entities;

import com.digitalparadise.controller.exceptions.OrderException;
import com.digitalparadise.web.SignableEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.digitalparadise.model.clients.Client;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class Order implements Serializable, SignableEntity {
    private UUID uuid;
    private LocalDateTime orderDateTime;
    private List<Good> goods = new ArrayList<Good>();
    private Client client;

    public Order(LocalDateTime orderDateTime, List<Good> goods, Client client) throws OrderException {
        if (orderDateTime == null || goods == null || client == null)
            throw new OrderException(OrderException.NULL_FIELD);

        this.orderDateTime = orderDateTime;
        this.goods = goods;
        this.client = client;
    }

    public Order(@JsonbProperty("uuid") UUID uuid,
                 @JsonbProperty("orderDataTime") LocalDateTime orderDateTime,
                 @JsonbProperty("goods") List<Good> goods,
                 @JsonbProperty("client") Client client) throws OrderException {
        if (uuid == null || orderDateTime == null || goods == null || client == null)
            throw new OrderException(OrderException.NULL_FIELD);

        this.uuid = uuid;
        this.orderDateTime = orderDateTime;
        this.goods = goods;
        this.client = client;
    }

    public void setGoods(List<Good> goods) throws OrderException {
        if (goods == null) {
            throw new OrderException(OrderException.NULL_Goods);
        }
        this.goods = goods;
    }

    public void setClient(Client client) throws OrderException {
        if (client == null) {
            throw new OrderException(OrderException.NULL_Client);
        }
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return uuid.equals(order.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @JsonbTransient
    @Override
    public String getSignablePayload() {
        return this.uuid.toString();
    }
}
