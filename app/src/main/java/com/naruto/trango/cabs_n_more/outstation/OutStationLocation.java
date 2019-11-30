package com.naruto.trango.cabs_n_more.outstation;

import java.io.Serializable;

public class OutStationLocation implements Serializable {

    private int id;
    private String pickupLocation;
    private String dropLocation, dropState;
    private int distance;

    private int priceMicro, priceMini, priceSedan, priceSuv, priceTraveller, priceLuxury;

    public OutStationLocation(int id, String dropLocation, String dropState,
                              int distance, int priceMicro, int priceMini, int priceSedan, int priceSuv,
                              int priceTraveller, int priceLuxury) {
        this.id = id;

        this.pickupLocation = "";

        this.dropLocation = dropLocation;
        this.dropState = dropState;
        this.distance = distance;
        this.priceMicro = priceMicro;
        this.priceMini = priceMini;
        this.priceSedan = priceSedan;
        this.priceSuv = priceSuv;
        this.priceTraveller = priceTraveller;
        this.priceLuxury = priceLuxury;
    }

    public int getId() {
        return id;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }



    public String getDropLocation() {
        return dropLocation;
    }

    public String getDropState() {
        return dropState;
    }

    public int getDistance() {
        return distance;
    }

    public int getPriceMicro() {
        return priceMicro;
    }

    public int getPriceMini() {
        return priceMini;
    }

    public int getPriceSedan() {
        return priceSedan;
    }

    public int getPriceSuv() {
        return priceSuv;
    }

    public int getPriceTraveller() {
        return priceTraveller;
    }

    public int getPriceLuxury() {
        return priceLuxury;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
}