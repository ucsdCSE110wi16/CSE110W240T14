
package edu.etduongucsd.dopeshit;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/2/2016.
 */
public class Professor {

    String name;    //Professor name
    String dataBaseRef;
    List<Lecture> lectures; //List of weeks within the course
    int numberOfLectures;
//    Course thisCourse;

    public Professor(){};

    //Constructor with parameters
    public Professor(String profName, String parentFirebaseRef){
        name = profName;
        dataBaseRef = parentFirebaseRef;
        lectures = new ArrayList<Lecture>();
        numberOfLectures = 0; //Need to use in firebase
       // addProfToFirebase();
      //  thisCourse = course;
      //  addProf(thisCourse);
    }

    public String getName(){
        return name;
    }

    public String getDataBaseRef(){
        return dataBaseRef;
    }

    public List<Lecture> getLectures(){
        return lectures;
    }

    public int getNumberOfLectures(){
        return  numberOfLectures;
    }

    @Override
    public String toString(){
        return getName();
    }

    public void addProfToFirebase(){
        Firebase ref = new Firebase(dataBaseRef);
        ref.child(getName()).setValue(0);
    }

    public void addLecture(){
        Lecture c = new Lecture(dataBaseRef+name+"/", numberOfLectures);
        lectures.add(c);
        numberOfLectures++;
        c.addLectureToFirebase();
    }
}
