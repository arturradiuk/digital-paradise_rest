package com.digitalparadise.model.entities;

import com.digitalparadise.controller.exceptions.good.GoodException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

@ToString(callSuper = true)
@Data
@NoArgsConstructor
public abstract class Good {

    private UUID uuid;

    private boolean sold = false;


    private String goodName;

    private double basePrice;

    private int count;

    public void setCount(int count) {
        this.count = count;
    }

    public Good(double basePrice, String goodName, int count) throws GoodException {
        if (goodName == null) {
            throw new GoodException(GoodException.NULL_ARGUMENT);
        }
        else if (basePrice < 0) {
            throw new GoodException(GoodException.NEGATIVE_PRICE);
        } else if (count < 0) {
            throw new GoodException(GoodException.NEGATIVE_AMOUNT);
        }
        
        this.basePrice = basePrice;
        this.goodName = goodName;
        this.count = count;
    }

    public Good(UUID uuid, double basePrice, String goodName, int count) throws GoodException {
        if (uuid == null || goodName == null) {
            throw new GoodException(GoodException.NULL_ARGUMENT);
        }
        else if (basePrice < 0) {
            throw new GoodException(GoodException.NEGATIVE_PRICE);
        } else if (count < 0) {
            throw new GoodException(GoodException.NEGATIVE_AMOUNT);
        }
        
        this.basePrice = basePrice;
        this.goodName = goodName;
        this.count = count;
    }

    public double getBasePrice() {
        return basePrice;
    }

    @Override
    public boolean equals(Object o) { // todo remember
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Good good = (Good) o;
        return uuid.equals(good.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
