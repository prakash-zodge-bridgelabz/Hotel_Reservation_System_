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

// Use case 3 :
// Ability to add weekday and weekend rates for each Hotel
// - For Lakewood Weekday & Weekend Rates per day is $110 & $90
// - For Bridgewood $150 and $50
// - For Ridgewood $220 and $150

// Use case 4 :
// Ability to find the cheapest Hotel for a given Date Range based on weekday and weekend
// - I/P – 11Sep2020, 12Sep2020
// - O/P – Lakewood and Bridgewood with Total Rates $200

// Use case 5 :
// Ability to add ratings to each Hotel
// - Lakewood is 3, Bridgewood is 4 and Ridgewood is 5

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
    void setWeekdayAndWeekendRate(String hotelName,int weekdayRate, int weekendRate);
    void setRatings(String hotelName, int ratings);
    int getRatings(String hotelName);
}
class Hotel implements  Hotel_Requirements{
    String hotelName;
    int regularRatesForCustomer, weekendRate, weekdayRate;
    static ArrayList<Hotel> allHotels= new ArrayList<>();       //For storing all hotels
    public void setAllHotels(ArrayList<Hotel> allHotels){
        this.allHotels = allHotels;
    }
    ArrayList<Hotel> getAllHotels(){        //returns arraylist which contains all hotel details
        return allHotels;
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

    public void setWeekdayAndWeekendRate(String hotelName,int weekdayRate, int weekendRate){
        // Iterate over the ArrayList and set the value of the element with the name "element2" to "new value"
        for (int i = 0; i < allHotels.size(); i++) {
            if (allHotels.get(i).hotelName.equals(hotelName)) {
                allHotels.get(i).weekdayRate = weekdayRate;
                allHotels.get(i).weekendRate = weekendRate;
            }
        }
    }
    int ratings;
    public void setRatings(String hotelName, int ratings){
        // Iterate over the ArrayList and set the value of the element with the name "element2" to "new value"
        for (int i = 0; i < allHotels.size(); i++) {
            if (allHotels.get(i).hotelName.equals(hotelName)) {
                allHotels.get(i).ratings = ratings;
            }
        }
    }
    int res=0;
    public int getRatings(String hotelName){
        for (int i = 0; i < allHotels.size(); i++) {
            if (allHotels.get(i).hotelName.equals(hotelName)) {
                res= allHotels.get(i).ratings;
            }
        }
        return res;
    }
    public String getHotelName()            {   return hotelName;               }
    public int getRegularRatesForCustomer() {   return regularRatesForCustomer; }
    public int getTotalHotelsCreated()      {   return totalHotelsCreated;      }
    static long totalDays;
    String firstDay,lastDay,day;
    String getDay(String strDate)throws ParseException {
        try {
            String inputDate = strDate.replace("sep","sept");     //Throws error --> if we provide "sep" so replaced with "sept"
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy");
            Date date = dateFormat.parse(inputDate);
            SimpleDateFormat DayOfWeek = new SimpleDateFormat("EEEE");
            day = DayOfWeek.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }
    static int firstWeekendCount=0,lastWeekendCount=0,firstWeekdayCount=0,lastWeekdayCount=0;
    int price,lakewoodPrice,bridgewoodPrice,ridgewoodPrice;
    public String findCheapestHotel(String fromDate,String toDate) throws ParseException {
        //Check whether dates are weekend or weekday
        //if date is weekend --> weekend++
        //else --> Weekday++
        //Parse date into day
        firstDay = getDay(fromDate);       //call getDay and pass one date
        lastDay = getDay(toDate);
        if(firstDay == "Saturday" || firstDay == "Sunday") {
            firstWeekendCount++;
        } else{
            firstWeekdayCount++;
        }
        if(lastDay == "Saturday" || lastDay == "Sunday") {
            lastWeekendCount++;
        } else{
            lastWeekdayCount++;
        }
        //Counting days`
        totalDays = countDays(fromDate,toDate);
        //Iterate over all hotels and calculate price accordingly
        for(int i=0; i<allHotels.size();i++){
            if(allHotels.get(i).hotelName == "Lakewood") {
                lakewoodPrice = allHotels.get(i).getPrice();
            } else if (allHotels.get(i).hotelName == "Bridgewood") {
                bridgewoodPrice = allHotels.get(i).getPrice();
            } else if(allHotels.get(i).hotelName == "Ridgewood"){
                ridgewoodPrice = allHotels.get(i).getPrice();
            }
            else {
                System.out.println("Hotel not found");
            }
        }
        //Finding cheapest hotel
        if(lakewoodPrice < bridgewoodPrice && lakewoodPrice < ridgewoodPrice){
            return "Lakewood, Total Rates: $"+lakewoodPrice;
        }
        else if(bridgewoodPrice < lakewoodPrice && bridgewoodPrice < ridgewoodPrice){
            return "Bridgewood, Total Rates: $"+bridgewoodPrice;
        }
        else if(ridgewoodPrice < lakewoodPrice && ridgewoodPrice < bridgewoodPrice){
            return "Ridgewood, Total Rates: $"+ridgewoodPrice;
        }
        else if(lakewoodPrice == bridgewoodPrice){
            return "Lakewood and Bridgewood with Total Rates: $"+lakewoodPrice;
        }
        else if(bridgewoodPrice == ridgewoodPrice) {
            return "Bridgewood and Ridgewood with Total Rates: $"+bridgewoodPrice;
        }
        else{       // lakewoodPrice == ridgewoodPrice
            return "Lakewood and Ridgewood with Total Rates: $"+lakewoodPrice;
        }
    }
    int getPrice() throws ParseException {  //
        if(totalDays <= 0){
            // do nothing
        } else{        //firstWeekdayCount      firstWeekendCount      lastWeekdayCount    lastWeekendCount
            if(firstWeekdayCount >= 1){  //weekendRate   weekDayRate
                price += weekdayRate * firstWeekdayCount;
            }
            if(firstWeekendCount >= 1){
                price += weekendRate * firstWeekendCount;
            }
            if(lastWeekdayCount >= 1){
                price += weekdayRate * lastWeekdayCount;
            }
            if(lastWeekendCount >= 1){
                price += weekendRate * lastWeekendCount;
            }
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
//    public String toString() {
//        return "Hotel Name : "+hotelName+
//                "\nRegular Rates for Customer : ";
//    }
    public String toString() {
        return "Hotel Name : "+hotelName+
                "\tWeekend Rate : "+weekendRate+
                "\tWeekday Rate : "+weekdayRate+
                "\tRatings : "+ratings;
    }
}
public class Hotel_Reservation {
    public static void main(String[] args) {
        System.out.println("Welcome to Hotel Reservation System");
    }
}
