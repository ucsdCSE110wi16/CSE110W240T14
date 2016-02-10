package team14.project110;

import java.util.List;

/**
 * Created by steven on 2/10/2016.
 */
public class Week {
    Day[] days;      //An array for the number of days per week.

    //Default constructor
    public Week(){

    }

    public void initDays(int numDays){
        days = new Day[numDays];
    }
}