package edu.etduongucsd.dopeshit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/25/2016.
 */
//Saves myCourses and myNotes lists
public class UserProfile {
    String name;
    List<Course> myCourses = new ArrayList<Course>();
    //List<Note> myNotes;
    MyNotes myNotes = new MyNotes();

    public UserProfile(String n) {
            name = n;
    }

    public void addClass(Course c) {
        boolean hasCourse = false;
        for(Course currCourse : myCourses){
            if(currCourse.name.equals(c.name)){
                hasCourse = true;
            }
        }
        if(!hasCourse){
            myCourses.add(c);
        }
    }

    //public void addMyNote(Note note){
    //    myNotes.add(note);
    //}

    public boolean isUser(String s) {
        return name.equals(s);
    }

}
