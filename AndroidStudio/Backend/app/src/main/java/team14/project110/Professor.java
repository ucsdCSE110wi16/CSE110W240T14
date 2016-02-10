
package team14.project110;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/2/2016.
 */
public class Professor {

    String name;    //Department name
    Week[] weeks = new Week[11]; //List of weeks within the course

    //Default constructor
    public Professor(){
        name = "";
    }
    //Constructor with parameters
    public Professor(String n, int days){
        name = n;
        initWeeks(days);
    }

        @Override
    public String toString(){
        return name;
    }

    public void initWeeks(int numDays){
        for(int i = 0; i < weeks.length; i++){
            weeks[i].initDays(numDays);
        }
    }

    /*
    //Add course to list of courses belonging to this department
    private void addWeek(String name){
        //Check if course had already been added
        if(weeks.contains(name)){
            return;
        }
        else {
            Week c = new Week(name);
            Week.add(c);
        }
    }*/
}
