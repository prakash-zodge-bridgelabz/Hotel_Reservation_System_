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
        Lakewood = new Hotel("Lakewood",110,90,3);
        Bridgewood = new Hotel("Bridgewood",150,50,4);
        Ridgewood = new Hotel("Ridgewood",220,150,5);
        allHotels.add(Lakewood);
        allHotels.add(Bridgewood);
        allHotels.add(Ridgewood);
        h = new Hotel();
        h.setAllHotels(allHotels);
    }
    // Use case 6, test case :
    // Ability to find the cheapest best rated Hotel for a given Date Range
    // - I/P – 11Sep2020, 12Sep2020
    // - O/P – Bridgewood, Rating: 4 and Total Rates: $200
    @Test
    public void test_useCase6_findCheapestHotel() throws ParseException{
        String actual = h.findCheapestHotel("11sep2020","12sep2020");   //1 weekday and 1 weekend
        String expected = "Bridgewood, Rating: 4 and Total Rates: $200";
        Assert.assertEquals(expected,actual);
    }
}
