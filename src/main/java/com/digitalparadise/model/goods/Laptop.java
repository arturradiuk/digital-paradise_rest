package com.digitalparadise.model.goods;

import com.digitalparadise.controller.exceptions.good.GoodException;
import com.digitalparadise.controller.exceptions.good.LaptopException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Laptop extends Computer {
    private double screenSize;
    private boolean hasCamera;
    private int usbAmount;

    public Laptop() {
        super();
    }

    public Laptop(double basePrice, String goodName, int ram, int ssdCapacity, double screenSize, boolean hasCamera, int usbAmount, int count) throws GoodException {
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

    public double getPrice() {
        return getBasePrice() + screenSize * 10 + usbAmount * 100 + (hasCamera ? 200 : 0);
    }

}
