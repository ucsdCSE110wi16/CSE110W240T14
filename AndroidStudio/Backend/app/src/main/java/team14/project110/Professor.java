
package team14.project110;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/2/2016.
 */
public class Professor {

    String name;    //Professor name
    Week[] weeks = new Week[11]; //List of weeks within the course

    //Constructor with parameters
    public Professor(String n, int days){
        name = n;
        initWeeks(days);
    }

        @Override
    public String toString(){
        return name;
    }

    //Initiate weeks
    public void initWeeks(int numDays){
        for(int i = 0; i < weeks.length; i++){
            Week thisWeek = new Week(numDays);
            weeks[i] = thisWeek;
        }
    }
}
