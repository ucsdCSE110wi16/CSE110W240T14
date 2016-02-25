package team14.project110;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/2/2016.
 */
public class Department {

    String name;    //Department name
    List<Course> courses = new ArrayList<Course>(); //List of courses within department

    //Default constructor
    public Department(){
        name = "";
    }

    //Constructor with parameters
    public Department(String n){
        name = n;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return getName();
    }

    public List<Course> getCourses(){
        return courses;
    }

    //Add course to list of courses belonging to this department
    private void addCourse(String name){
        //Check if course had already been added
        if(courses.contains(name)){
            return;
        }
        else {
            Course c = new Course(name);
            courses.add(c);
        }
    }
}
