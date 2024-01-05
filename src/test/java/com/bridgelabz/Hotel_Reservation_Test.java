package com.bridgelabz;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;

public class Hotel_Reservation_Test {
    ArrayList<Hotel> allHotels;       //For storing all hotels
    Hotel Lakewood,Bridgewood,Ridgewood,h;
    @Before
    public void init(){
        allHotels= new ArrayList<>();       //For storing all hotels
        Lakewood = new Hotel("Lakewood",80,80,3);
        Bridgewood = new Hotel("Bridgewood",110,50,4);
        Ridgewood = new Hotel("Ridgewood",100,40,5);
        allHotels.add(Lakewood);
        allHotels.add(Bridgewood);
        allHotels.add(Ridgewood);
        h = new Hotel();
        h.setAllHotels(allHotels);
        h.setSpecialRatesForRegularCustomer("Lakewood",110,90);
        h.setSpecialRatesForRegularCustomer("Bridgewood",160,60);
        h.setSpecialRatesForRegularCustomer("Ridgewood",120,150);
    }
    // Use case 12, test case :
    // Ability to find the cheapest best rated Hotel for a given Date range for a Regular Customer
    // using Java Streams
    // - Use Regex Validation, Exceptions and Java 8 Date Feature
    // - I/P – 11Sep2020, 12Sep2020
    // - O/P – Bridgewood, Rating: 4 and Total Rates: $200
    @Test
    public void test_useCase10_findCheapestBestRatedHotelForRegularCustomer() throws ParseException, HotelNotFoundException {
        String actual = h.findCheapestBestRatedHotelForRegularCustomer("11sep2020","12sep2020");
        String expected = "Bridgewood, Rating: 4 and Total Rates: $200";
        Assert.assertEquals(expected,actual);
    }
}
