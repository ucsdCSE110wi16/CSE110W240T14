package edu.etduongucsd.dopeshit;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/2/2016.
 */
//Course object
public class Course {
    String name;
    String dataBaseRef;
    Department parentDepartment;
    List<Professor> professors;  //List of courses within department

    //Default constructor
    public Course() {}

    //Constructor with course name
    public Course(String n, String parentFirebaseRef) {
        name = n;
        dataBaseRef = parentFirebaseRef;
        professors = new ArrayList<Professor>();
    }

    public String getName() {
        return name;
    }

    public String getDataBaseRef(){
        return dataBaseRef;
    }

    public List<Professor> getProfessors(){
        return professors;
    }

    @Override
    public String toString(){
        return getName();
    }

    public void addCourseToFirebase(){
        Firebase ref = new Firebase(dataBaseRef);
        ref.child(getName()).setValue(0);
    }

    //Add course to list of courses belonging to this department
    public void addProfessor(String profName) {
        boolean found = false;
        for(Professor tempProf : professors){
            if(tempProf.name.equals(profName)){
                found = true;
            }
        }
        if(!found){
            Professor c = new Professor(profName, dataBaseRef+name+"/");
            c.parentCourse = this;
            professors.add(c);
            c.addProfToFirebase();
        }
    }
}
