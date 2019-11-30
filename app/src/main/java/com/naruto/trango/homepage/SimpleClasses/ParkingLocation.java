package com.naruto.trango.homepage.SimpleClasses;

import java.io.Serializable;

public class ParkingLocation implements Serializable {


    private int parkingId;
    private int ownerId;

    String imagePath;

    private String location ,desc,landmark,openingHrs;
    private String latitude, longitude;
    private int parkingType;

    private int bikeCapacity,CarCapacity;
    private int fareForBike,fareForCar;

    float distanceFromMyLocation ;


    private int initialCarVacancy;
    private int initialBikeVacancy;



    public float getDistanceFromMyLocation() {
        return distanceFromMyLocation;
    }

    public void setDistanceFromMyLocation(float distanceFromMyLocation) {
        this.distanceFromMyLocation = distanceFromMyLocation;
    }

    public ParkingLocation(int parkingId, int ownerId, String location, String desc, String landmark,
                           String latitude, String longitude) {

        this.parkingId      = parkingId;
        this.ownerId        = ownerId;
        this.imagePath      = imagePath;

        this.location       = location;
        this.desc           = desc;
        this.landmark       = landmark;
        this.openingHrs     = openingHrs;
        //this.latLng         = latLng;
        this.latitude       = latitude;
        this.longitude      = longitude;
        this.parkingType    = parkingType ;
        this.bikeCapacity   = bikeCapacity;

        this.fareForBike        = fareForBike;
        this.fareForCar         = fareForCar;

        distanceFromMyLocation = 8;




    }

    public int getParkingId() {
        return parkingId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getOpeningHrs() {
        return openingHrs;
    }

    public void setOpeningHrs(String openingHrs) {
        this.openingHrs = openingHrs;
    }

//    public LatLng getLatLng() {
//        return latLng;
//    }
//
//    public void setLatLng(LatLng latLng) {
//        this.latLng = latLng;
//    }


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getParkingType() {
        return parkingType;
    }

    public void setParkingType(int parkingType) {
        this.parkingType = parkingType;
    }

    public int getBikeCapacity() {
        return bikeCapacity;
    }

    public void setBikeCapacity(int bikeCapacity) {
        this.bikeCapacity = bikeCapacity;
    }

    public int getCarCapacity() {
        return CarCapacity;
    }

    public void setCarCapacity(int carCapacity) {
        CarCapacity = carCapacity;
    }

    public int getFareForBike() {
        return fareForBike;
    }

    public void setFareForBike(int fareForBike) {
        this.fareForBike = fareForBike;
    }

    public int getFareForCar() {
        return fareForCar;
    }

    public void setFareForCar(int fareForCar) {
        this.fareForCar = fareForCar;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getInitialCarVacancy() {
        return initialCarVacancy;
    }

    public void setInitialCarVacancy(int initialCarVacancy) {
        this.initialCarVacancy = initialCarVacancy;
    }

    public int getInitialBikeVacancy() {
        return initialBikeVacancy;
    }

    public void setInitialBikeVacancy(int initialBikeVacancy) {
        this.initialBikeVacancy = initialBikeVacancy;
    }
}
