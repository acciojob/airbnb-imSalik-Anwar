package com.driver.model;

import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private String hotelName; //This will be the primary key and will be unique for each hotel in hotelDb
    private int availableRooms;

    private List<Facility> facilities;

    private int pricePerNight;
    private int size;

    public Hotel(String hotelName, int availableRooms, List<Facility> facilities, int pricePerNight) {
        this.hotelName = hotelName;
        this.availableRooms = availableRooms;
        this.facilities = facilities;
        this.pricePerNight = pricePerNight;
        this.size = facilities.size();
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    public List<Facility> getFacilities() {
        List<Facility> list = new ArrayList<>(facilities);
        return list;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
