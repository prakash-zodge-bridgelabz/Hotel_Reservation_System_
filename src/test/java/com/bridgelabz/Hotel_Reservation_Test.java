package com.bridgelabz;

import org.junit.Assert;
import org.junit.Test;

// Test Case on Use Case 1 : Check count of hotels been created
public class Hotel_Reservation_Test {
    @Test
    public void addHotel(){
        Hotel Lakewood = new Hotel("Lakewood",110);
        Hotel Bridgewood = new Hotel("Bridgewood",110);
        int actual = Bridgewood.getTotalHotelsCreated();
        Assert.assertEquals(2,actual);
    }
}
