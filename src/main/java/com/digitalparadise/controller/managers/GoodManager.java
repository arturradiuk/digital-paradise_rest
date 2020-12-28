package com.digitalparadise.controller.managers;

import com.digitalparadise.controller.exceptions.ManagerException;
import com.digitalparadise.controller.exceptions.repository.RepositoryException;
import lombok.NoArgsConstructor;
import com.digitalparadise.model.entities.Good;
import com.digitalparadise.model.entities.Order;
import com.digitalparadise.model.goods.Laptop;
import com.digitalparadise.model.goods.PC;
import com.digitalparadise.model.repositories.Repository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
@NoArgsConstructor
public class GoodManager implements IManager<Good, UUID> {

    @Inject
    private Repository<Good, UUID> goodRepository; // = new GoodRepository();


    public Good getGoodByUUID(UUID uuid) throws RepositoryException {
        return this.goodRepository.getResourceByUUID(uuid);
    }


    @Override
    public void update(UUID uuid, Good good) throws RepositoryException {
        this.goodRepository.update(uuid, good);
    }

    @Override
    public void add(Good good) throws RepositoryException {
        this.goodRepository.add(good);
    }

    @Override
    public void remove(Good good) throws RepositoryException { // save deleting
        this.goodRepository.remove(good);
    }

    public void remove(OrderManager orderManager, Good good) throws ManagerException, RepositoryException {
        for (Order o : orderManager.getAll()) {
            for (Good g : o.getGoods()) {
                if (g.getUuid().equals(good.getUuid())) {
                    throw new ManagerException("Cannot delete the good that is a part of order.");
                }

            }
        }
        this.goodRepository.remove(good);
    }

    @Override
    public List<Good> getAll() {
        return this.goodRepository.getAll();
    }


    public List<Good> getAllCurrentLaptops() {
        List<Good> laptops = this.getAll().stream().filter(lapop -> lapop instanceof Laptop).collect(Collectors.toList());
        return laptops;
    }

    public List<Good> getAllCurrentPCs() {
        List<Good> pcs = this.getAll().stream().filter(pc -> pc instanceof PC).collect(Collectors.toList());
        return pcs;
    }

}

