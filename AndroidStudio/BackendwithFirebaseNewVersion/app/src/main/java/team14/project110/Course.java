package team14.project110;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/2/2016.
 */
public class Course {
    String name;
 //   String departName;
    String dataBaseRef;
    List<Professor> professors;  //List of courses within department

    //Default constructor
    public Course() {}

    //Constructor with course name
    public Course(String n, String parentFirebaseRef) {
        name = n;
        dataBaseRef = parentFirebaseRef;
        professors = new ArrayList<Professor>();
    //    addCourseToFirebase();
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
        //Check if course had already been added
        if (professors.contains(profName)) {
            return;
        } else {
            Professor c = new Professor(profName, dataBaseRef+name+"/");
            professors.add(c);
            c.addProfToFirebase();
        }
    }

    /*
    public String getDepartName(){
        return departName;
    }
*/
}
