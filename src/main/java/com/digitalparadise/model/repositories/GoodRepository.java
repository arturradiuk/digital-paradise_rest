package com.digitalparadise.model.repositories;

import com.digitalparadise.controller.exceptions.repository.GoodRepositoryException;
import com.digitalparadise.controller.exceptions.repository.RepositoryException;
import com.digitalparadise.model.fillers.DataFiller;
import com.digitalparadise.model.fillers.StaticGoodFiller;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.digitalparadise.model.entities.Good;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;



@Data
@NoArgsConstructor
public class GoodRepository implements Repository<Good, UUID> {
    private List<Good> goods;

    @Override
    public Good getResourceByUUID(UUID uuid) throws RepositoryException {
        for (Good g : this.goods) {
            if (g.getUuid().equals(uuid)) {
                return g;
            }
        }
        throw new GoodRepositoryException(GoodRepositoryException.NOT_EXIST_GOOD_WITH_SUCH_UUID);
    }

    @Override
    public void add(Good element) throws RepositoryException {
        synchronized (this.goods) {
            element.setUuid(UUID.randomUUID());
            if (this.goods.contains(element))
                throw new GoodRepositoryException(GoodRepositoryException.EXIST_GOOD);

            this.goods.add(element);
        }
    }

    @Override
    public void remove(Good element) throws RepositoryException {
        synchronized (this.goods) {

            if (!this.goods.remove(element)) {
                throw new GoodRepositoryException(GoodRepositoryException.NOT_EXIST_GOOD);
            }

        }
    }

    @Override
    public List<Good> getAll() {
        synchronized (this.goods) {
            return new CopyOnWriteArrayList<>(goods);
        }
    }

    @Override
    public void update(UUID id, Good element) {
        synchronized (this.goods) {
            for (int i = 0; i < goods.size(); i++) {
                if (id.equals(goods.get(i).getUuid())) {
                    this.goods.set(i, element);
                }
            }
        }
    }

    @PostConstruct
    public void initGoods() {
        DataFiller dataFiller = new StaticGoodFiller();
        this.goods = dataFiller.Fill();
    }


}
