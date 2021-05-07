package com.digitalparadise.model.fillers;

import com.digitalparadise.controller.exceptions.good.GoodException;
import com.digitalparadise.model.entities.Good;
import com.digitalparadise.model.goods.Laptop;
import com.digitalparadise.model.goods.PC;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class StaticGoodFiller implements DataFiller {
    @Override
    public List<Good> Fill() {
        List<Good> goods = new CopyOnWriteArrayList<>();
        try {
            Good temp = new Laptop(110, "Lenovo", 16, 256, 13, true, 2, 5);
            temp.setUuid(UUID.fromString("14a6b7cc-bd4c-3022-83d0-d2af506bfb2b"));
            goods.add(temp);

            temp = new Laptop(120, "Apple", 16, 256, 13, true, 2, 6);
            temp.setUuid(UUID.fromString("24a6b7cc-bd4c-3022-83d0-d2af506bfb2b"));
            goods.add(temp);

            temp = new Laptop(130, "HP", 16, 256, 13, true, 2, 7);
            temp.setUuid(UUID.fromString("34a6b7cc-bd4c-3022-83d0-d2af506bfb2b"));
            goods.add(temp);

            temp = new Laptop(140, "Dell", 16, 256, 13, true, 2, 8);
            temp.setUuid(UUID.fromString("44a6b7cc-bd4c-3022-83d0-d2af506bfb2b"));
            goods.add(temp);

            temp = new Laptop(150, "Acer", 16, 256, 13, true, 2, 9);
            temp.setUuid(UUID.fromString("54a6b7cc-bd4c-3022-83d0-d2af506bfb2b"));
            goods.add(temp);

            temp = new PC(150, "Acer pc", 16, 256, 3);
            temp.setUuid(UUID.fromString("64a6b7cc-bd4c-3022-83d0-d2af506bfb2b"));
            goods.add(temp);

            temp = new PC(150, "Dell pc", 16, 256, 4);
            temp.setUuid(UUID.fromString("74a6b7cc-bd4c-3022-83d0-d2af506bfb2b"));
            goods.add(temp);

            temp = new PC(150, "HP pc", 16, 256, 6);
            temp.setUuid(UUID.fromString("84a6b7cc-bd4c-3022-83d0-d2af506bfb2b"));
            goods.add(temp);

            temp = new PC(150, "Samsung pc", 16, 256, 0);
            temp.setUuid(UUID.fromString("94a6b7cc-bd4c-3022-83d0-d2af506bfb2b"));
            goods.add(temp);

        } catch (GoodException e) {
            e.printStackTrace();
        }
        return goods;
    }
}
