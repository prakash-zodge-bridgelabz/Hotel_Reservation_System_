package com.bridgelabz;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;

public class Hotel_Reservation_Test {
    ArrayList<Hotel> allHotels;       //For storing all hotels
    Hotel Lakewood,Bridgewood,Ridgewood;
    @Before
    public void init(){
        allHotels= new ArrayList<>();       //For storing all hotels
        Lakewood = new Hotel("Lakewood",110);
        Bridgewood = new Hotel("Bridgewood",160);
        Ridgewood = new Hotel("Ridgewood",220);
        allHotels.add(Lakewood);
        allHotels.add(Bridgewood);
        allHotels.add(Ridgewood);
    }
    // Use case 1, test case: Check count of hotels been created
    @Test
    public void addHotel(){
        Lakewood = new Hotel("Lakewood",110);
        Bridgewood = new Hotel("Bridgewood",160);
        Ridgewood = new Hotel("Ridgewood",220);
        int actual = Bridgewood.getTotalHotelsCreated();
        Assert.assertEquals(2,actual);
    }
    // Use case 2 (Add weekday only), Test Case :
    // Ability to find the cheapest Hotel for a given Date Range
    // - I/P – 10Sep2020, 11Sep2020
    // - O/P – Lakewood, Total Rates: $220
    @Test
    public void test_findCheapestHotel() throws ParseException {
        Hotel h = new Hotel();
        h.setAllHotels(allHotels);
        String actual = h.findCheapestHotel("10sep2020","11sep2020");
        Assert.assertEquals("Lakewood, Total Rates: $220",actual);
    }
}
