
package team14.project110;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/2/2016.
 */
public class Professor {

    String name;    //Professor name
    Week[] weeks = new Week[11]; //List of weeks within the course
    int numberOfNotes;
    Course thisCourse;

    public Professor(){};

    //Constructor with parameters
    public Professor(String n, int days, Course course){
        name = n;
        initWeeks(days);
        numberOfNotes = 0;
        thisCourse = course;
        addProf(thisCourse);
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return getName();
    }

    public Week[] getWeeks(){
        return weeks;
    }

    //Initiate weeks
    public void initWeeks(int numDays){
        for(int i = 0; i < weeks.length; i++){
            Week thisWeek = new Week(numDays);
            weeks[i] = thisWeek;
        }
    }

    public void addProf(Course course){
        Firebase ref = new Firebase("https://note110.firebaseio.com/Departments/"+course.departName+"/"+course.toString()+"/");
        for(int i = 0; i < weeks.length; i++){
            ref.child(getName()).child("Week " + (i + 1)).setValue(0);
            for(int j = 0; j < weeks[i].days.length; j++){
                ref.child(getName()).child("Week "+(i+1)).child("Day "+(j+1)).child("Notes").setValue(0);
            }
        }
    }
}
