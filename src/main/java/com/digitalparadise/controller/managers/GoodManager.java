package com.digitalparadise.controller.managers;

import com.digitalparadise.controller.exceptions.repository.RepositoryException;
import com.digitalparadise.controller.exceptions.manager.GoodManagerException;
import lombok.NoArgsConstructor;
import com.digitalparadise.model.entities.Good;
import com.digitalparadise.model.entities.Order;
import com.digitalparadise.model.goods.Laptop;
import com.digitalparadise.model.goods.PC;
import com.digitalparadise.model.repositories.Repository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
@NoArgsConstructor
public class GoodManager implements IManager<Good, UUID> {

    @Inject
    private Repository<Good, UUID> goodRepository;


    public List<Good> getGoods(List<String> goodsUuid) throws RepositoryException {
        List<Good> goods = new ArrayList<>();
        for ( String uuid : goodsUuid) {
            goods.add(this.getGoodByUUID(UUID.fromString(uuid)));
        }
        return goods;
    }

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
    public void remove(Good good) throws RepositoryException {
        this.goodRepository.remove(good);
    }

    public void remove(OrderManager orderManager, Good good) throws GoodManagerException, RepositoryException {
        for (Order o : orderManager.getAll()) {
            for (Good g : o.getGoods()) {
                if (g.getUuid().equals(good.getUuid())) {
                    throw new GoodManagerException(GoodManagerException.CANNOT_DELETE);
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
        return this.getAll().stream().filter(lapop -> lapop instanceof Laptop).collect(Collectors.toList());
    }

    public List<Good> getAllCurrentPCs() {
        return this.getAll().stream().filter(pc -> pc instanceof PC).collect(Collectors.toList());
    }

}

