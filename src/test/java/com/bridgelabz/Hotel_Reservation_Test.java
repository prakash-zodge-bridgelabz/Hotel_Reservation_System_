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
        Lakewood = new Hotel("Lakewood",110);
        Bridgewood = new Hotel("Bridgewood",160);
        Ridgewood = new Hotel("Ridgewood",220);
        allHotels.add(Lakewood);
        allHotels.add(Bridgewood);
        allHotels.add(Ridgewood);
        h = new Hotel();
        h.setAllHotels(allHotels);
        h.setWeekdayAndWeekendRate("Lakewood",110,90);
        h.setWeekdayAndWeekendRate("Bridgewood",150,50);
        h.setWeekdayAndWeekendRate("Ridgewood",220,150);
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
        String actual = h.findCheapestHotel("10sep2020","11sep2020");
        Assert.assertEquals("Lakewood, Total Rates: $220",actual);
    }
    // Use case 3, test case :
    // Ability to add weekday and weekend rates for each Hotel
    // - For Lakewood Weekday & Weekend Rates per day is $110 & $90
    // - For Bridgewood $150 and $50
    // - For Ridgewood $220 and $150
    @Test
    public void test_useCase3_findCheapestHotel() throws ParseException {
        h.setWeekdayAndWeekendRate("Lakewood",110,90);
        h.setWeekdayAndWeekendRate("Bridgewood",150,50);
        h.setWeekdayAndWeekendRate("Ridgewood",220,150);
        // find cheapest hotels based on weekend and weekday
        Assert.assertEquals("Lakewood, Total Rates: $220",h.findCheapestHotel("10sep2020","11sep2020"));
    }
    // Use case 4, test case :
    // Ability to find the cheapest Hotel for a given Date Range based on weekday and weekend
    // - I/P – 11Sep2020, 12Sep2020
    // - O/P – Lakewood and Bridgewood with Total Rates $200
    @Test
    public void test_useCase4_findCheapestHotel() throws ParseException{
        String expected = "Lakewood and Bridgewood with Total Rates $200";
        Assert.assertEquals(expected,h.findCheapestHotel("11sep2020","12sep2020"));
    }
}
