package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@Repository
public class HotelManagementRepo {

    TreeMap<String, Hotel> hotelDB = new TreeMap<>();
    HashMap<Integer, User> userDB = new HashMap<>();
    HashMap<String, Booking> bookingDB = new HashMap<>();
    HashMap<Integer, List<Booking>> personBookingDB = new HashMap<>();
    public String addHotel(Hotel hotel) {
        if(hotel.getHotelName() == null || hotelDB.containsKey(hotel.getHotelName())){
            return "FAILURE";
        }
        hotelDB.put(hotel.getHotelName(), hotel);
        return "SUCCESS";
    }

    public int addUser(User user) {
        userDB.put(user.getaadharCardNo(), user);
        return user.getaadharCardNo();
    }
    public String getHotelWithMostFacilities() {
        int maxFacilities = 0;
        String hotelWithMostFacilities = "";
        for(String hotelName : hotelDB.keySet()){
            int numberOfFacilities = hotelDB.get(hotelName).getSize();
            if(numberOfFacilities > maxFacilities){
                maxFacilities = numberOfFacilities;
                hotelWithMostFacilities = hotelName;
            }
        }

        return hotelWithMostFacilities;
    }
    public int bookARoom(Booking booking) {
        String hotelName = booking.getHotelName();
        if(hotelDB.containsKey(hotelName)) {
            if (hotelDB.get(hotelName).getAvailableRooms() >= booking.getNoOfRooms()) {
                int amountToBePaid = hotelDB.get(hotelName).getPricePerNight() * booking.getNoOfRooms();
                booking.setAmountToBePaid(amountToBePaid);
                hotelDB.get(hotelName).setAvailableRooms(hotelDB.get(hotelName).getAvailableRooms() - booking.getNoOfRooms());
                bookingDB.put(booking.getBookingId(), booking);
                if(!personBookingDB.containsKey(booking.getBookingAadharCard())){
                    personBookingDB.put(booking.getBookingAadharCard(), new ArrayList<>());
                    personBookingDB.get(booking.getBookingAadharCard()).add(booking);
                } else {
                    personBookingDB.get(booking.getBookingAadharCard()).add(booking);
                }
                return amountToBePaid;
            }
        }
        return -1;
    }

    public int getBookings(Integer aadharCard) {
        return personBookingDB.get(aadharCard).size();
    }


    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotelDB.get(hotelName);
        List<Facility> oldFacilities = hotel.getFacilities();
        for(Facility f : newFacilities){
            if(!oldFacilities.contains(f)){
                oldFacilities.add(f);
            }
        }
        return hotel;
    }
}
