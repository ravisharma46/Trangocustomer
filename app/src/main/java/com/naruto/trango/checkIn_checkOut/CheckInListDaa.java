package com.naruto.trango.checkIn_checkOut;

public class CheckInListDaa {

    private String station, dTime,aTime,station_details,full_timetable;
    private  int imgId;

    public CheckInListDaa(Integer integer, String station, String dTime, String aTime,String station_details,String full_timetable) {
        imgId=integer;
        this.station = station;
        this.dTime = dTime;
        this.aTime = aTime;
        this.station_details=station_details;
        this.full_timetable=full_timetable;

    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public int getImgId() {
        return imgId;
    }

    public String getStation_details() {
        return station_details;
    }

    public void setStation_details(String station_details) {
        this.station_details = station_details;
    }

    public String getFull_timetable() {
        return full_timetable;
    }

    public void setFull_timetable(String full_timetable) {
        this.full_timetable = full_timetable;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getdTime() {
        return dTime;
    }

    public void setdTime(String dTime) {
        this.dTime = dTime;
    }

    public String getaTime() {
        return aTime;
    }

    public void setaTime(String aTime) {
        this.aTime = aTime;
    }
}
