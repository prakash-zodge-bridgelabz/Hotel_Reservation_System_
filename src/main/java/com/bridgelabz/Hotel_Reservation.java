package com.bridgelabz;


// HOTEL RESERVATION SYSTEM
// Rates --> Weekday
// Rates --> Weekend (Saturday, Sunday)
//                                                       3 HOTEL's
// Concepts                         | Lakewood      |   Bridgelwood     |   Ridgewood
// Ratings                          |   3           |       4           |       5
// rewardCustomerWeekdayRate      $ |   80          |       110         |       100
// rewardCustomerWeekendRate      $ |   80          |       50          |       40
// regularCustomerWeekdayRate     $ |   110         |       160         |       120
// regularCustomerWeekendRate     $ |   90          |       60          |       150

// Use case 12:
// Ability to find the cheapest best rated Hotel for a given Date range for a Regular Customer
// using Java Streams
// - Use Regex Validation, Exceptions and Java 8 Date Feature
// - I/P – 11Sep2020, 12Sep2020
// - O/P – Bridgewood, Rating: 4 and Total Rates: $200

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

interface Hotel_Requirements{
    String getHotelName();
    String findCheapestBestRatedHotelForRewardCustomer(String fromDate,String toDate) throws ParseException, HotelNotFoundException;
    String findBestRatedHotel(String fromDate,String toDate) throws ParseException;
    void setAllHotels(ArrayList<Hotel> allHotels);
    int getRatings(String hotelName);
    void setSpecialRatesForRewardCustomer(String hotelName,int rewardCustomerWeekdayRate,int rewardCustomerWeekendRate);
    String getSpecialRatesForRewardCustomer(String hotelName);
}
class HotelNotFoundException extends Exception{
    HotelNotFoundException(String errorMsg){
        super(errorMsg);
    }
}
class Hotel implements  Hotel_Requirements{
    String hotelName;
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
    int ratings,rewardCustomerWeekendRate, rewardCustomerWeekdayRate,regularCustomerWeekdayRate,regularCustomerWeekendRate;
    Hotel(String hotelName, int rewardCustomerWeekdayRate, int rewardCustomerWeekendRate, int ratings){
        this.hotelName = hotelName;
        this.rewardCustomerWeekdayRate = rewardCustomerWeekdayRate;
        this.rewardCustomerWeekendRate = rewardCustomerWeekendRate;
        this.ratings = ratings;
    }
    // Ability to add special rates for reward customers as a part of Loyalty Program
    public void setSpecialRatesForRewardCustomer(String hotelName,int rewardCustomerWeekdayRate,int rewardCustomerWeekendRate){
        for(int i=0;i<allHotels.size();i++){
            if(allHotels.get(i).hotelName.equals(hotelName)){
                allHotels.get(i).rewardCustomerWeekdayRate=rewardCustomerWeekdayRate;
                allHotels.get(i).rewardCustomerWeekendRate = rewardCustomerWeekendRate;
            }
        }
    }
    public String getSpecialRatesForRewardCustomer(String hotelName){
        String txt="";
        for(int i=0;i<allHotels.size();i++){
            if(allHotels.get(i).hotelName == hotelName){
                txt = "For "+allHotels.get(i).hotelName+" reward customer weekday rate is : $"+allHotels.get(i).rewardCustomerWeekdayRate+
                        " and reward customer weekend rate is : $"+allHotels.get(i).rewardCustomerWeekendRate;
            }
        }
        return txt;
    }
    public void setSpecialRatesForRegularCustomer(String hotelName,int rewardCustomerWeekdayRate,int rewardCustomerWeekendRate){
        for(int i=0;i< allHotels.size();i++){
            if(allHotels.get(i).hotelName.equals(hotelName)){
                allHotels.get(i).regularCustomerWeekdayRate = rewardCustomerWeekdayRate;
                allHotels.get(i).regularCustomerWeekendRate = rewardCustomerWeekendRate;
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
    static long totalDays;
    static String firstDay,lastDay;
    String day;
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
    int price,lakewoodPrice,lakewoodRatings,bridgewoodPrice,bridgewoodRatings,ridgewoodPrice,ridgewoodRatings;
    // Ability to find the cheapest best rated Hotel for a given Date Range for a Reward Customer using Java Streams
    int findHotelPriceforRewardCustomer(List<Hotel> allHotels, String hotelName) {
        Integer i = allHotels.stream()
                .filter(hotel -> hotel.getHotelName().equals(hotelName))
                .findFirst()
                .map(hotel1 -> {
                    try {
                        return hotel1.getPriceForRewardCustomer();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElse(0);
        return i;
    }
    int findHotelPriceforRegularCustomer(List<Hotel> allHotels, String hotelName) {
        Integer i = allHotels.stream()
                .filter(hotel -> hotel.getHotelName().equals(hotelName))
                .findFirst()
                .map(hotel1 -> {
                    try {
                        return hotel1.getPriceForRegularCustomer();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElse(0);
        return i;
    }
    public String findCheapestBestRatedHotelForRewardCustomer(String fromDate,String toDate) throws ParseException, HotelNotFoundException {
        //Check whether dates are weekend or weekday
        //if date is weekend --> weekend++ else --> Weekday++
        //Parse date into day
        firstDay = getDay(fromDate);
        lastDay = getDay(toDate);
//        // Store weekdays and weekends
        int weekendCount=0,weekdayCount=0;
        if(firstDay == "Saturday" || firstDay == "Sunday") {
            firstWeekendCount++;
        } else{
            firstWeekdayCount++;
        }
        if(lastDay == "Monday" || lastDay == "Tuesday" || lastDay == "Wednesday" || lastDay == "Thursday" || lastDay == "Friday") {
            lastWeekdayCount++;
        } else{
            lastWeekendCount++;
        }
        //Counting days`
        totalDays = countDays(fromDate,toDate);
        //Iterate over all hotels and calculate price accordingly

        lakewoodPrice = findHotelPriceforRewardCustomer(allHotels,"Lakewood");
        bridgewoodPrice = findHotelPriceforRewardCustomer(allHotels,"Bridgewood");
        ridgewoodPrice = findHotelPriceforRewardCustomer(allHotels,"Ridgewood");
        System.out.println("Lakewood Price : "+lakewoodPrice+
                "Bridgewood price : "+bridgewoodPrice+
                "Ridgewood price : "+ridgewoodPrice);
        lakewoodRatings = getRatings("Lakewood");
        bridgewoodRatings = getRatings("Bridgewood");
        ridgewoodRatings = getRatings("Ridgewood");
        // Ability to find the cheapest best rated Hotel for a given Date Range
        // - I/P – 11Sep2020, 12Sep2020
        // - O/P – Bridgewood, Rating: 4 and Total Rates: $200
        if(lakewoodPrice < bridgewoodPrice && lakewoodPrice < ridgewoodPrice){
            return "Lakewood, Rating: "+lakewoodRatings+" and Total Rates: $"+lakewoodPrice;
        }
        else if(bridgewoodPrice < lakewoodPrice && bridgewoodPrice < ridgewoodPrice){
            return "Bridgewood, Rating: "+bridgewoodRatings+" and Total Rates: $"+bridgewoodPrice;
        }
        else if(ridgewoodPrice < lakewoodPrice && ridgewoodPrice < bridgewoodPrice){
            return "Ridgewood, Rating: "+ridgewoodRatings+" and Total Rates: $"+ridgewoodPrice;
        }
        else if(lakewoodPrice == bridgewoodPrice){
            // Now check ratings, according to higher ratings hotel details will be returned
            if(lakewoodRatings > bridgewoodRatings){
                return "Lakewood, Rating: "+lakewoodRatings+" and Total Rates: $"+lakewoodPrice;
            }else{
                return "Bridgewood, Rating: "+bridgewoodRatings+" and Total Rates: $"+bridgewoodPrice;
            }
        }
        else if(bridgewoodPrice == ridgewoodPrice) {
            if(bridgewoodRatings > ridgewoodRatings){
                return "Bridgewood, Rating: "+bridgewoodRatings+" and Total Rates: $"+bridgewoodPrice;
            }else{
                return "Ridgewood, Rating: "+ridgewoodRatings+" and Total Rates: $"+ridgewoodPrice;
            }
        }
        else{       // lakewoodPrice == ridgewoodPrice
            if(lakewoodRatings > ridgewoodRatings){
                return "Lakewood, Rating: "+lakewoodRatings+" and Total Rates: $"+lakewoodPrice;
            }else{
                return "Ridgewood, Rating: "+ridgewoodRatings+" and Total Rates: $"+ridgewoodPrice;
            }
        }
    }
    public String findCheapestBestRatedHotelForRegularCustomer(String fromDate,String toDate) throws ParseException, HotelNotFoundException {
        //Check whether dates are weekend or weekday
        //if date is weekend --> weekend++ else --> Weekday++
        //Parse date into day
        firstDay = getDay(fromDate);
        lastDay = getDay(toDate);
//        // Store weekdays and weekends
        int weekendCount=0,weekdayCount=0;
        if(firstDay == "Saturday" || firstDay == "Sunday") {
            firstWeekendCount++;
        } else{
            firstWeekdayCount++;
        }
        if(lastDay == "Monday" || lastDay == "Tuesday" || lastDay == "Wednesday" || lastDay == "Thursday" || lastDay == "Friday") {
            lastWeekdayCount++;
        } else{
            lastWeekendCount++;
        }
        //Counting days`
        totalDays = countDays(fromDate,toDate);
        //Iterate over all hotels and calculate price accordingly

        lakewoodPrice = findHotelPriceforRegularCustomer(allHotels,"Lakewood");
        bridgewoodPrice = findHotelPriceforRegularCustomer(allHotels,"Bridgewood");
        ridgewoodPrice = findHotelPriceforRegularCustomer(allHotels,"Ridgewood");
        System.out.println("Lakewood Price : "+lakewoodPrice+
                "Bridgewood price : "+bridgewoodPrice+
                "Ridgewood price : "+ridgewoodPrice);
        lakewoodRatings = getRatings("Lakewood");
        bridgewoodRatings = getRatings("Bridgewood");
        ridgewoodRatings = getRatings("Ridgewood");
        // Ability to find the cheapest best rated Hotel for a given Date Range
        // - I/P – 11Sep2020, 12Sep2020
        // - O/P – Bridgewood, Rating: 4 and Total Rates: $200
        if(lakewoodPrice < bridgewoodPrice && lakewoodPrice < ridgewoodPrice){
            return "Lakewood, Rating: "+lakewoodRatings+" and Total Rates: $"+lakewoodPrice;
        }
        else if(bridgewoodPrice < lakewoodPrice && bridgewoodPrice < ridgewoodPrice){
            return "Bridgewood, Rating: "+bridgewoodRatings+" and Total Rates: $"+bridgewoodPrice;
        }
        else if(ridgewoodPrice < lakewoodPrice && ridgewoodPrice < bridgewoodPrice){
            return "Ridgewood, Rating: "+ridgewoodRatings+" and Total Rates: $"+ridgewoodPrice;
        }
        else if(lakewoodPrice == bridgewoodPrice){
            // Now check ratings, according to higher ratings hotel details will be returned
            if(lakewoodRatings > bridgewoodRatings){
                return "Lakewood, Rating: "+lakewoodRatings+" and Total Rates: $"+lakewoodPrice;
            }else{
                return "Bridgewood, Rating: "+bridgewoodRatings+" and Total Rates: $"+bridgewoodPrice;
            }
        }
        else if(bridgewoodPrice == ridgewoodPrice) {
            if(bridgewoodRatings > ridgewoodRatings){
                return "Bridgewood, Rating: "+bridgewoodRatings+" and Total Rates: $"+bridgewoodPrice;
            }else{
                return "Ridgewood, Rating: "+ridgewoodRatings+" and Total Rates: $"+ridgewoodPrice;
            }
        }
        else{       // lakewoodPrice == ridgewoodPrice
            if(lakewoodRatings > ridgewoodRatings){
                return "Lakewood, Rating: "+lakewoodRatings+" and Total Rates: $"+lakewoodPrice;
            }else{
                return "Ridgewood, Rating: "+ridgewoodRatings+" and Total Rates: $"+ridgewoodPrice;
            }
        }
    }
    public String findBestRatedHotel(String fromDate,String toDate) throws ParseException{
        //Check whether dates are weekend or weekday
        //if date is weekend --> weekend++ else --> Weekday++
        //Parse date into day
        firstDay = getDay(fromDate);
        lastDay = getDay(toDate);
        // Store weekdays and weekends
        int weekendCount=0,weekdayCount=0;
        if(firstDay == "Saturday" || firstDay == "Sunday") {
            firstWeekendCount++;
        } else{
            firstWeekdayCount++;
        }
        if(lastDay == "Monday" || lastDay == "Tuesday" || lastDay == "Wednesday" || lastDay == "Thursday" || lastDay == "Friday") {
            lastWeekdayCount++;
        } else{
            lastWeekendCount++;
        }
        //Counting days`
        totalDays = countDays(fromDate,toDate);

        //Iterate over all hotels and calculate price accordingly
        for(int i=0; i<allHotels.size();i++){
            if(allHotels.get(i).hotelName == "Lakewood") {
                lakewoodPrice = allHotels.get(i).getPriceForRewardCustomer();
            } else if (allHotels.get(i).hotelName == "Bridgewood") {
                bridgewoodPrice = allHotels.get(i).getPriceForRewardCustomer();
            } else if(allHotels.get(i).hotelName == "Ridgewood"){
                ridgewoodPrice = allHotels.get(i).getPriceForRewardCustomer();
            }
            else {
                System.out.println("Hotel not found");
            }
        }
        lakewoodRatings = getRatings("Lakewood");
        bridgewoodRatings = getRatings("Bridgewood");
        ridgewoodRatings = getRatings("Ridgewood");
        // Ability to find the Best Rated Hotel for a given Date Range
        // - I/P – 11Sep2020, 12Sep2020
        // - O/P – Ridgewood & Total Rates $370
        if(lakewoodRatings > bridgewoodRatings && lakewoodRatings > ridgewoodRatings){
            return "Lakewood & Total Rates $"+lakewoodPrice;
        }else if(bridgewoodRatings > lakewoodRatings && bridgewoodRatings > ridgewoodRatings){
            return "Brigewood & Total Rates $"+bridgewoodPrice;
        }else{      //Ridgewood have higher ratings as compare to others.
            return "Ridgewood & Total Rates $"+ridgewoodPrice;
        }
    }
    int getPriceForRewardCustomer() throws ParseException {
        if(firstWeekdayCount >= 1){
            price += rewardCustomerWeekdayRate * firstWeekdayCount;
        }
        if(firstWeekendCount >= 1){
            price += rewardCustomerWeekendRate * firstWeekendCount;
        }
        if(lastWeekdayCount >= 1){
            price += rewardCustomerWeekdayRate * lastWeekdayCount;
        }
        if(lastWeekendCount >= 1){
            price += rewardCustomerWeekendRate * lastWeekendCount;
        }
        return price;
    }
    int getPriceForRegularCustomer() throws ParseException {
        if(firstWeekdayCount >= 1){
            price += regularCustomerWeekdayRate * firstWeekdayCount;
        }
        if(firstWeekendCount >= 1){
            price += regularCustomerWeekendRate * firstWeekendCount;
        }
        if(lastWeekdayCount >= 1){
            price += regularCustomerWeekdayRate * lastWeekdayCount;
        }
        if(lastWeekendCount >= 1){
            price += regularCustomerWeekendRate * lastWeekendCount;
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
                "\tWeekend Rate : "+rewardCustomerWeekendRate+
                "\tWeekday Rate : "+rewardCustomerWeekdayRate+
                "\tRatings : "+ratings;
    }
}
public class Hotel_Reservation {
    public static void main(String[] args) {
        System.out.println("Welcome to Hotel Reservation System");
    }
}
