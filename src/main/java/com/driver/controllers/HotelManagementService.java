package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelManagementService {

    static HotelManagementRepo hotelManagementRepo = new HotelManagementRepo();
    public static String addHotel(Hotel hotel) {
        return hotelManagementRepo.addHotel(hotel);
    }

    public static int addUser(User user) {
        return hotelManagementRepo.addUser(user);
    }
    public static String getHotelWithMostFacilities() {
        return hotelManagementRepo.getHotelWithMostFacilities();
    }

    public static int bookARoom(Booking booking) {
        return hotelManagementRepo.bookARoom(booking);
    }

    public static int getBookings(Integer aadharCard) {
        return hotelManagementRepo.getBookings(aadharCard);
    }

    public static Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return hotelManagementRepo.updateFacilities(newFacilities, hotelName);
    }
}
