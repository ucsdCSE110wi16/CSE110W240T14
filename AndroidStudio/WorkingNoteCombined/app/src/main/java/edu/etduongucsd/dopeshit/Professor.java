
package edu.etduongucsd.dopeshit;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created on 2/2/2016.
 */

//Professor object
public class Professor {

    String name;    //Professor name
    String dataBaseRef;
    List<Lecture> lectures; //List of weeks within the course
    public int numberOfLectures;
    public Course parentCourse;

    public Professor(){};

    //Constructor with parameters
    public Professor(String profName, String parentFirebaseRef){
        name = profName;
        dataBaseRef = parentFirebaseRef;
        lectures = new ArrayList<Lecture>();
        numberOfLectures = 0; //Need to use in firebase
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return getName();
    }

    public static final Comparator<Professor> ASC_PROF = new Comparator<Professor>() {
        @Override
        public int compare(Professor lhs, Professor rhs) {
            String lhsName = lhs.getName();
            String rhsName = rhs.getName();
            return lhsName.compareTo(rhsName);
        }
    };

    public void addProfToFirebase(){
        Firebase ref = new Firebase(dataBaseRef);
        ref.child(getName()).setValue(0);
    }

    public void addLecture(){
        numberOfLectures++;
        Lecture c = new Lecture(dataBaseRef+name+"/", numberOfLectures);
        c.parentProfessor = this;
        lectures.add(c);
        c.addLectureToFirebase();
    }
}
