package com.digitalparadise.model.entities;

import com.digitalparadise.controller.exceptions.good.GoodException;
import com.digitalparadise.web.SignableEntity;
import lombok.*;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbNillable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.ws.rs.GET;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@ToString(callSuper = true)
@Data
@NoArgsConstructor

@JsonbNillable(value = true)
public abstract class Good implements Serializable, SignableEntity {


    @JsonbTransient
    @Override
    public String getSignablePayload() {
        return uuid.toString();
    }

    @JsonbProperty
    private UUID uuid;
    @JsonbProperty
    private Boolean sold = false;
    @JsonbProperty
    private String goodName;
    @JsonbProperty
    private Double basePrice;

//    @JsonbTransient // todo remove comment here
    @JsonbProperty
    private Integer count;


    public Good(double basePrice, String goodName, int count) throws GoodException {
        if (goodName == null) {
            throw new GoodException(GoodException.NULL_ARGUMENT);
        } else if (basePrice < 0) {
            throw new GoodException(GoodException.NEGATIVE_PRICE);
        } else if (count < 0) {
            throw new GoodException(GoodException.NEGATIVE_AMOUNT);
        }

        this.basePrice = basePrice;
        this.goodName = goodName;
        this.count = count;
    }

    @JsonbCreator
    public Good(@JsonbProperty("uuid") UUID uuid,
                @JsonbProperty("basePrice") double basePrice,
                @JsonbProperty("goodName") String goodName,
                @JsonbProperty("count") int count) throws GoodException {
        if (uuid == null || goodName == null) {
            throw new GoodException(GoodException.NULL_ARGUMENT);
        } else if (basePrice < 0) {
            throw new GoodException(GoodException.NEGATIVE_PRICE);
        } else if (count < 0) {
            throw new GoodException(GoodException.NEGATIVE_AMOUNT);
        }

        this.basePrice = basePrice;
        this.goodName = goodName;
        this.count = count;
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


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


}
