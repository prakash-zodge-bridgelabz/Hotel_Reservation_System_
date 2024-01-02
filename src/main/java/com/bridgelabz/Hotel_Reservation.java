package com.bridgelabz;


// HOTEL RESERVATION SYSTEM
// Rates --> Weekday
// Rates --> Weekend (Saturday, Sunday)
//                                                   3 HOTEL's
// Concepts                     | Lakewood      |   Bridgelwood     |   Ridgewood
// Ratings                      |   3           |       4           |       5
// RegularCustomerWeekdayRate $ |   110         |       160         |       220
// RewardCustomerWeekdayRate  $ |   80          |       110         |       100
// RegularCustomerWeekendRate $ |   90          |       60          |       150
// RewardCustomerWeekendRate  $ |   80          |       50          |       40

// Use case 1 :
// Ability to add Hotel in a Hotel Reservation System with Name and rates for Regular Customer…
interface Hotel_Requirements{
    String getHotelName();
    int getRegularRatesForCustomer();
    int getTotalHotelsCreated();
}
class Hotel implements  Hotel_Requirements{
    String hotelName;
    int regularRatesForCustomer;
    static int totalHotelsCreated;      //Static count will be same for every hotels been created
    Hotel(String hotelName,int regularRatesForCustomer){
        this.hotelName = hotelName;
        this.regularRatesForCustomer = regularRatesForCustomer;
        totalHotelsCreated++;
    }
    public String getHotelName()            {   return hotelName;               }
    public int getRegularRatesForCustomer() {   return regularRatesForCustomer; }
    public int getTotalHotelsCreated()      {   return totalHotelsCreated;      }
    
    @Override
    public String toString() {
        return "Hotel Name : "+hotelName+
                "\nRegular Rates for Customer : ";
    }
}
public class Hotel_Reservation {
    public static void main(String[] args) {
        System.out.println("Welcome to Hotel Reservation System");
    }
}
