package team14.project110;

import com.firebase.client.Firebase;

import java.util.List;

/**
 * Created on 2/21/2016.
 */
public class DatabaseSetup {

    List<Department> listOfDepartments;

    public DatabaseSetup(){};

    public DatabaseSetup(List<Department> departList){
        listOfDepartments = departList;
        setup();
    }

    public void setup(){
        Firebase ref = new Firebase("https://note110.firebaseio.com/");
        ref.child("Departments").setValue(0);

        for(Department currDepart : listOfDepartments){
            ref.child("Departments").child(currDepart.toString()).setValue(0);
            for(Course currCourse : currDepart.courses){
                ref.child("Departments").child(currDepart.toString()).child(currCourse.toString()).setValue(0);
            }
        }
    }

}
