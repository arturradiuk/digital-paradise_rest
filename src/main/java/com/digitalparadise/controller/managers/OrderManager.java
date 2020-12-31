package com.digitalparadise.controller.managers;


import com.digitalparadise.controller.exceptions.ManagerException;
import com.digitalparadise.controller.exceptions.OrderException;
import com.digitalparadise.controller.exceptions.repository.RepositoryException;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.digitalparadise.model.clients.Client;
import com.digitalparadise.model.entities.Good;
import com.digitalparadise.model.entities.Order;
import com.digitalparadise.model.entities.User;
import com.digitalparadise.model.repositories.Repository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Named
@ApplicationScoped
@NoArgsConstructor
@Data
public class OrderManager implements IManager<Order, UUID> {

    @Inject
    private Repository<Order, UUID> orderRepository;

    @Override
    public void add(Order element) throws RepositoryException { // todo here must be exception
        this.orderRepository.add(element);
    }

    @Override
    public void update(UUID id, Order element) throws RepositoryException {
        this.orderRepository.update(id, element);
    }

    @Override
    public void remove(Order order) throws RepositoryException {
        this.orderRepository.remove(order);
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = this.orderRepository.getAll();
        return orders;
    }

    public Order createOrder(GoodManager goodManager, List<Good> goods, Client client) throws OrderException, RepositoryException, ManagerException {
        boolean exists = false;

        Map<Good, Integer> tempMap = new HashMap<Good, Integer>();

        for (int i = 0; i < goods.size(); i++) {
            if (tempMap.containsKey(goods.get(i))) {

                int tempInt = tempMap.get(goods.get(i));
                tempInt++;
                tempMap.remove(goods.get(i));
                tempMap.put(goods.get(i), tempInt);

            } else {
                tempMap.put(goods.get(i), 1);
            }
        }
        for (Good g :
                goodManager.getAll()) {
            if(tempMap.containsKey(g)){
                if( (g.getCount() - tempMap.get(g)) < 0){
                    throw new ManagerException("There are not enough goods in magazine"); // todo add static string in ManagerException
                }
                else{
                    g.setCount(g.getCount()-tempMap.get(g));
                    if (g.getCount() == 0)
                        g.setSold(true);
                }
            }
        }


        Order order = new Order(LocalDateTime.now(), goods, client);
        this.orderRepository.add(order);

        return order;

    }

    public void returnOrder(GoodManager goodManager, Order order) throws RepositoryException {
        this.remove(order);
        for (Good g : goodManager.getAll()) {

            for (Good og : order.getGoods()) {
                if (g.getUuid().equals(og.getUuid())) {
                    g.setCount(g.getCount() + 1);
                }
            }
        }
    }

    public Order getOrderByUUID(UUID uuid) throws RepositoryException {
        return this.orderRepository.getResourceByUUID(uuid);
    }

    public List<Order> getAllOrdersForTheEmail(String userEmail) {

        List<Order> orders = new CopyOnWriteArrayList<>();
        for (Order order : this.orderRepository.getAll()) {
            if (order.getClient().getEmail().equals(userEmail)) {
                orders.add(order);
            }
        }
        return orders;
    }


}
