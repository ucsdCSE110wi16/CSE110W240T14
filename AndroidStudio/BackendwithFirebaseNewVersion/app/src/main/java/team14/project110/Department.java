package team14.project110;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/2/2016.
 */
public class Department {

    String name;    //Department name
    List<Course> courses; //List of courses within department
    String dataBaseRef;

    //Default constructor
    public Department(){}

    //Constructor with parameters
    public Department(String n){
        name = n;
        courses = new ArrayList<Course>();
        dataBaseRef = "https://note110.firebaseio.com/Departments/";
    }

    public String getName(){
        return name;
    }

    public List<Course> getCourses(){
        return courses;
    }

    public String getDataBaseRef(){
        return dataBaseRef;
    }

    @Override
    public String toString(){
        return getName();
    }

    public void addDepartmentToFirebase(){
        Firebase ref = new Firebase(dataBaseRef);
        ref.child(getName()).setValue(0);
    }

    //Add course to list of courses belonging to this department
    public void addCourse(String courseName){
        boolean found = false;
        for(Course tempCourse : courses){
            if(tempCourse.name.equals(courseName)){
                found = true;
            }
        }
        if(!found){
            Course c = new Course(courseName, dataBaseRef+name+"/");
            c.addCourseToFirebase();
            courses.add(c);
        }
     /*   //Check if course had already been added
        if(!courses.contains(courseName)){
            Course c = new Course(courseName, dataBaseRef+name+"/");
            c.addCourseToFirebase();
            courses.add(c);
        }*/
    }
}
