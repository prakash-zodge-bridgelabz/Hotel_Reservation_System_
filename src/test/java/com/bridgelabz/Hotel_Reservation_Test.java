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
    // Use case 9, test case:
    // Ability to add special rates for reward customers as a part of Loyalty Program
    // - For Lakewood for Reward Customer Weekday & Weekend Rates per day is $80 & $80
    // - For Bridgewood $110 and $50
    // - For Ridgewood $100 and $40
    @Test
    public void test_useCase9_setSpecialRatesForRewardCustomer() throws ParseException{
        h.setSpecialRatesForRewardCustomer("Lakewood",80,80);
        h.setSpecialRatesForRewardCustomer("Bridgewood",110,50);
        h.setSpecialRatesForRewardCustomer("Ridgewood",100,40);

        String lakewoodActual = h.getSpecialRatesForRewardCustomer("Lakewood");
        String lakewoodExpected = "For Lakewood reward customer weekday rate is : $80 and reward customer weekend rate is : $80";
        Assert.assertEquals(lakewoodExpected,lakewoodActual);

        String bridgewoodActual = h.getSpecialRatesForRewardCustomer("Bridgewood");
        String bridgewoodExpected = "For Bridgewood reward customer weekday rate is : $110 and reward customer weekend rate is : $50";
        Assert.assertEquals(bridgewoodExpected,bridgewoodActual);

        String ridgewoodActual = h.getSpecialRatesForRewardCustomer("Ridgewood");
        String ridgewoodExpected = "For Ridgewood reward customer weekday rate is : $100 and reward customer weekend rate is : $40";
        Assert.assertEquals(ridgewoodExpected,ridgewoodActual);
    }
}
