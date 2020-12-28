package com.digitalparadise.model.repositories;

import com.digitalparadise.controller.exceptions.repository.OrderRepositoryException;
import com.digitalparadise.controller.exceptions.repository.RepositoryException;
import com.digitalparadise.model.fillers.DataFiller;
import com.digitalparadise.model.fillers.StaticOrderFiller;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.digitalparadise.model.entities.Order;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
@NoArgsConstructor
public class OrderRepository implements Repository<Order, UUID> {// todo write methods getBy...
    private List<Order> orders;


    @Override
    public Order getResourceByUUID(UUID uuid) throws RepositoryException {
        for (Order o : orders) {
            if (o.getUuid().equals(uuid)) {
                return o;
            }
        }
        throw new OrderRepositoryException("There is no order with such uuid in the repository.");
    }

    @Override
    public void update(UUID id, Order element) {
        synchronized (this.orders) {
            for (int i = 0; i < orders.size(); i++) {
                if (id.equals(orders.get(i).getUuid())) {
                    this.orders.set(i, element);
                }
            }
        }
    }


    public OrderRepository(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public void add(Order element) throws RepositoryException {
        synchronized (this.orders) {
            element.setUuid(UUID.randomUUID());
            for (Order order : orders) {
                if (order.equals(element))
                    throw new OrderRepositoryException(OrderRepositoryException.EXIST_ORDER);
            }
            this.orders.add(element);
        }
    }

    @Override
    public void remove(Order element) throws RepositoryException {
        synchronized (this.orders) {
            if (!this.orders.remove(element)) {
                throw new OrderRepositoryException(OrderRepositoryException.NOT_EXIST_ORDER);
            }
        }
    }

    @Override
    public List<Order> getAll() {
        synchronized (this.orders) {
            return new CopyOnWriteArrayList<>(orders);
        }
    }

    @PostConstruct
    public void initOrders() {
        DataFiller dataFiller = new StaticOrderFiller();
        this.orders = dataFiller.Fill();
    }
}
