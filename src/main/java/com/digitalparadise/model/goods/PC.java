package com.digitalparadise.model.goods;

import com.digitalparadise.controller.exceptions.good.GoodException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbNillable;
import javax.json.bind.annotation.JsonbProperty;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonbNillable(value = true)
public class PC extends Computer {
    public PC() {
        super();
    }

    @JsonbCreator
    public PC(@JsonbProperty("basePrice") double basePrice,
              @JsonbProperty("goodName") String goodName,
              @JsonbProperty("ram") int ram,
              @JsonbProperty("ssdCapacity") int ssdCapacity,
              @JsonbProperty("count") int count) throws GoodException {
        super(basePrice, goodName, ram, ssdCapacity, count);
    }

}
