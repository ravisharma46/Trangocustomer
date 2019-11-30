package com.naruto.trango.cabs_n_more.outstation;

import java.io.Serializable;

public class VehicleOutSation implements Serializable {

    private int vehicleType;

    private String name;
    private String sampleVehicles;

    private int farePerKm;
    private String person;
    private int image;


    //Info related to trip

    private String origin;
    private String destination;

    private long departureUnix;
    private long returnUnix;

    private int distance;


    public VehicleOutSation(int vehicleType, String name, String sampleVehicles,
                            int farePerKm, String person, int image, String origin, String destination,
                            long departureUnix, long returnUnix, int distance) {


        this.vehicleType = vehicleType;
        this.name = name;
        this.sampleVehicles = sampleVehicles;
        this.farePerKm = farePerKm;
        this.person = person;
        this.image = image;
        this.origin = origin;
        this.destination = destination;
        this.departureUnix = departureUnix;
        this.returnUnix = returnUnix;
        this.distance = distance;
    }

    public int getVehicleType() {
        return vehicleType;
    }

    public String getName() {
        return name;
    }

    public String getSampleVehicles() {
        return sampleVehicles;
    }

    public int getFarePerKm() {
        return farePerKm;
    }

    public String getPerson() {
        return person;
    }

    public int getImage() {
        return image;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public long getDepartureUnix() {
        return departureUnix;
    }

    public long getReturnUnix() {
        return returnUnix;
    }

    public int getDistance() {
        return distance;
    }
}