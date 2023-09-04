package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/hotel")
public class HotelManagementController {
    TreeMap<String, Hotel> hotelDB = new TreeMap<>();
    HashMap<Integer, User> userDB = new HashMap<>();
    HashMap<String, Booking> bookingDB = new HashMap<>();
    HashMap<Integer, Integer> personBookingDB = new HashMap<>();
//    HotelManagementService hotelManagementService = new HotelManagementService();

    @PostMapping("/add-hotel")
    public String addHotel(@RequestBody Hotel hotel){
        //You need to add a hotel to the database
        //in-case the hotelName is null or the hotel Object is null return an empty a FAILURE
        //In-case somebody is trying to add the duplicate hotelName return FAILURE
        //in all other cases return SUCCESS after successfully adding the hotel to the hotelDb.
//        return HotelManagementService.addHotel(hotel);
        if(hotel.getHotelName() == null || hotel == null){
            return "FAILURE";
        }
        if(hotelDB.containsKey(hotel.getHotelName())){
            return "FAILURE";
        }
        hotelDB.put(hotel.getHotelName(), hotel);
        return "SUCCESS";
    }

    @PostMapping("/add-user")
    public Integer addUser(@RequestBody User user){
        //You need to add a User Object to the database
        //Assume that user will always be a valid user and return the aadharCardNo of the user
//        return HotelManagementService.addUser(user);
        userDB.put(user.getaadharCardNo(), user);
        return user.getaadharCardNo();
    }


    @GetMapping("/get-hotel-with-most-facilities")
    public String getHotelWithMostFacilities(){
        //Out of all the hotels we have added so far, we need to find the hotelName with most no of facilities
        //In-case there is a tie return the lexicographically smaller hotelName
        //In-case there is not even a single hotel with at least 1 facility return "" (empty string)
//        return  HotelManagementService.getHotelWithMostFacilities();
        int maxFacilities = 0;
        String hotelWithMostFacilities = "";
        for(String hotelName : hotelDB.keySet()){
            int numberOfFacilities = hotelDB.get(hotelName).getSize(); // getSize returns the size of facilities list of a hotel
            if(numberOfFacilities > maxFacilities){
                maxFacilities = numberOfFacilities;
                hotelWithMostFacilities = hotelName;
            }
        }
        return hotelWithMostFacilities;
    }

    @PostMapping("/book-a-room")
    public int bookARoom(@RequestBody Booking booking){
        //The booking object coming from postman will have all the attributes except bookingId and amountToBePaid;
        //Have bookingId as a random UUID generated String
        //save the booking Entity and keep the bookingId as a primary key
        //Calculate the total amount paid by the person based on no. of rooms booked and price of the room per night.
        //If there aren't enough rooms available in the hotel that we are trying to book return -1
        //in other case return total amount paid
//        return HotelManagementService.bookARoom(booking);
        String hotelName = booking.getHotelName();
        if(hotelDB.containsKey(hotelName)) {
            if (hotelDB.get(hotelName).getAvailableRooms() >= booking.getNoOfRooms()) {
                int amountToBePaid = hotelDB.get(hotelName).getPricePerNight() * booking.getNoOfRooms();
                booking.setAmountToBePaid(amountToBePaid);
                hotelDB.get(hotelName).setAvailableRooms(hotelDB.get(hotelName).getAvailableRooms() - booking.getNoOfRooms());
                bookingDB.put(booking.getBookingId(), booking);
                personBookingDB.put(booking.getBookingAadharCard(), personBookingDB.getOrDefault(booking.getBookingAadharCard(), 0) + 1);
                return amountToBePaid;
            }
        }
        return -1;
    }
    
    @GetMapping("/get-bookings-by-a-person/{aadharCard}")
    public int getBookings(@PathVariable("aadharCard")Integer aadharCard) {
        //In this function return the bookings done by a person
//        return HotelManagementService.getBookings(aadharCard);
        return personBookingDB.get(aadharCard);
    }

    @PutMapping("/update-facilities")
    public Hotel updateFacilities(List<Facility> newFacilities,String hotelName){
        //We are having a new facilities that a hotel is planning to bring.
        //If the hotel is already having that facility ignore that facility otherwise add that facility in the hotelDb
        //return the final updated List of facilities and also update that in your hotelDb
        //Note that newFacilities can also have duplicate facilities possible
//        return HotelManagementService.updateFacilities(newFacilities, hotelName);
        Hotel hotel = hotelDB.get(hotelName);
        List<Facility> oldFacilities = hotel.getFacilities();
        for(Facility f : newFacilities){
            if(!oldFacilities.contains(f)){
                oldFacilities.add(f);
            }
        }
        hotel.setFacilities(oldFacilities);
        hotelDB.put(hotelName, hotel);
        return hotel;
    }

}
