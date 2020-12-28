package com.digitalparadise.model.goods;

import com.digitalparadise.controller.exceptions.good.GoodException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PC extends Computer {
    public PC() {
        super();
    }

    public PC(double basePrice, String goodName, int ram, int ssdCapacity, int count) throws GoodException {
        super(basePrice, goodName, ram, ssdCapacity, count);
    }

}
