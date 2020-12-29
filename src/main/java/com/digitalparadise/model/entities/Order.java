package com.digitalparadise.model.entities;

import com.digitalparadise.controller.exceptions.OrderException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.digitalparadise.model.clients.Client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class Order {
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

    public Order(UUID uuid, LocalDateTime orderDateTime, List<Good> goods, Client client) throws OrderException {
        if (uuid == null || orderDateTime == null || goods == null || client == null)
            throw new OrderException(OrderException.NULL_FIELD);

        this.uuid = uuid;
        this.orderDateTime = orderDateTime;
        this.goods = goods;
        this.client = client;
    }

    public void setGoods(List<Good> goods) throws OrderException { // todo try to remove this ...
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
    public boolean equals(Object o) { // todo remember
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return uuid.equals(order.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
