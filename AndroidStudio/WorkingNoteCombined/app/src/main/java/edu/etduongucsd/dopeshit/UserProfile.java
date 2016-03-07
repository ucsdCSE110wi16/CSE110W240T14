package edu.etduongucsd.dopeshit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/25/2016.
 */
//Saves myCourses and myNotes lists
public class UserProfile {
    String name;
    public static List<Course> myCourses = new ArrayList<Course>();
<<<<<<< HEAD
    public List<Note> myFlags = new ArrayList<>();
    public List<Note> myUpvotes = new ArrayList<>();
=======
    public static List<Course> myUploadCourses = new ArrayList<Course>();
>>>>>>> a031d36575f4269c0fba2858e6a66ebbe04528ef
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

    //Compares a note to add or remove from Note List (either myFlags or myUpvotes)
    public boolean containsNote( Note n, List<Note> myList) {
        if(myList == null) {
            return false;
        }

        for(Note currentNote : myList) {
            if( (currentNote.dataBaseRef + currentNote.toString()).equals(n.dataBaseRef + n.toString())) {
                return true;
            }
        }

        return false;
   }

    //Toggle the upvotes by user, if note is in the myUpvotes list, remove it, else add it.
    //Returns true if added, false if removed.
    public void toggleMyUpvote(Note n) {
        if ( containsNote( n, myUpvotes)) {
            myUpvotes.remove(n);
        }
        else {
            myUpvotes.add(n);
        }
    }

    //Toggle the flags by user, if note is in the myFlags list, remove it, else add it.
    //Returns true if added, false if removed
    public void toggleMyFlags(Note n) {
        if ( containsNote( n, myFlags)) {
            myFlags.remove(n);
        }
        else {
            myFlags.add(n);
        }
    }

    public boolean isUser(String s) {
        return name.equals(s);
    }

}
