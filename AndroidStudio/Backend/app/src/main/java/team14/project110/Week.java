package team14.project110;

import java.util.List;

/**
 * Created by steven on 2/10/2016.
 */
public class Week {
    Day[] days;      //An array for the number of days per week.

    //Default constructor
    public Week(int numDays){
        initDays(numDays);
    }

    //Initiate days array
    public void initDays(int numDays){

        days = new Day[numDays];
        //Fill array with day objects
        for(int i = 0; i < numDays; i++){
            Day newDay = new Day();
            days[i] = newDay;
        }
    }
}