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

// Use case 2 : (Add weekday only)
// Ability to find the cheapest Hotel for a given Date Range
// - I/P – 10Sep2020, 11Sep2020
// - O/P – Lakewood, Total Rates: $220

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


interface Hotel_Requirements{
    String getHotelName();
    int getRegularRatesForCustomer();
    int getTotalHotelsCreated();
    String findCheapestHotel(String fromDate,String toDate) throws ParseException;
    void setAllHotels(ArrayList<Hotel> allHotels);
}
class Hotel implements  Hotel_Requirements{
    String hotelName;
    int regularRatesForCustomer;
    static ArrayList<Hotel> allHotels= new ArrayList<>();       //For storing all hotels
    public void setAllHotels(ArrayList<Hotel> allHotels){
        this.allHotels = allHotels;
    }
    Hotel(){
        //For calling setAllHotels method
    }
    static int totalHotelsCreated;      //Static count will be same for every hotels been created
    Hotel(String hotelName,int regularRatesForCustomer){
        this.hotelName = hotelName;
        this.regularRatesForCustomer = regularRatesForCustomer;
        totalHotelsCreated++;
    }
    public String getHotelName()            {   return hotelName;               }
    public int getRegularRatesForCustomer() {   return regularRatesForCustomer; }
    public int getTotalHotelsCreated()      {   return totalHotelsCreated;      }
    static long totalDays;
    public String findCheapestHotel(String fromDate,String toDate) throws ParseException {
        totalDays = countDays(fromDate,toDate);
        // Use case 2 : Weekday's only -> Assuming firstDay and lastDay are weekday's
        //Iterate over all hotels and calculate price accordingly
        if (allHotels.size() > 0) {
            int best = 0;
            int i  = 1;
            while (i < allHotels.size()) {

                double cost = allHotels.get(i).getPrice();
                if (cost < allHotels.get(best).getPrice()) {        // Finding the cheapest hotel
                    best = i;       // Storing index of arraylist which is cheapest
                }
                i++;
            }
            return allHotels.get(best).hotelName+", Total Rates: $"+allHotels.get(best).getPrice();
        } else {
            return "Hotel list is empty";
        }
    }
    int getPrice() throws ParseException {
        // Counting total days
        int price=0;
        if(totalDays <= 0){
            // do nothing
        }
        else{
            price += regularRatesForCustomer * totalDays;
        }
        return price;
    }
    long countDays(String firstDay,String lastDay){
        String strDate1 = firstDay.replace("sep","09");
        String strDate2 = lastDay.replace("sep","09");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy", Locale.ENGLISH);
        LocalDateTime local_date1 = LocalDate.parse(strDate1, dtf).atStartOfDay();
        LocalDateTime local_date2 = LocalDate.parse(strDate2, dtf).atStartOfDay();
        long daysBetween = Duration.between(local_date1, local_date2).toDays();
        // .betweeen method returns long
        return daysBetween+1;
    }
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
