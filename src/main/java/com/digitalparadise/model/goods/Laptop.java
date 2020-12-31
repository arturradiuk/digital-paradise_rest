package com.digitalparadise.model.goods;

import com.digitalparadise.controller.exceptions.good.GoodException;
import com.digitalparadise.controller.exceptions.good.LaptopException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbNillable;
import javax.json.bind.annotation.JsonbProperty;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonbNillable(value = true)
public class Laptop extends Computer {
    private Double screenSize;
    private Boolean hasCamera;
    private Integer usbAmount;

    public Laptop() {
        super();
    }

    @JsonbCreator
    public Laptop(@JsonbProperty("basePrice") double basePrice,
                  @JsonbProperty("goodName") String goodName,
                  @JsonbProperty("ram") int ram,
                  @JsonbProperty("ssdCapacity") int ssdCapacity,
                  @JsonbProperty("screenSize") double screenSize,
                  @JsonbProperty("hasCamera") boolean hasCamera,
                  @JsonbProperty("usbAmount") int usbAmount,
                  @JsonbProperty("count") int count) throws GoodException {
        super(basePrice, goodName, ram, ssdCapacity, count);
        if (screenSize < 0) {
            throw new LaptopException(LaptopException.NEGATIVE_SCREEN_SIZE);
        }
        if (usbAmount < 0) {
            throw new LaptopException(LaptopException.NEGATIVE_USB_AMOUNT);
        }
        this.screenSize = screenSize;
        this.hasCamera = hasCamera;
        this.usbAmount = usbAmount;
    }

    public Double getPrice() {
        return getBasePrice() + screenSize * 10 + usbAmount * 100 + (hasCamera ? 200 : 0);
    }

}
