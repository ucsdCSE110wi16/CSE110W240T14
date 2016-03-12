package edu.etduongucsd.dopeshit;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/25/2016.
 */
//Saves myCourses and myNotes lists
public class UserProfile {
    String name;
    //Need to change myCourses to myProfessors
    public List<Professor> myCourses = new ArrayList<Professor>();
    public List<Note> myFlags = new ArrayList<>();
    public List<Note> myUpvotes = new ArrayList<>();
    public List<Note> userUpNotes = new ArrayList<Note>();
    //List<Note> myNotes;
    MyNotes myNotes = new MyNotes();


    //to have unique lists of strings name them user.toString()+nameoflist

 //   preferenceSettings = getSharedPreferences(PREFERENCE_MODE_PRIVATE);
    //SharedPreferences.Editor editor = PreferenceManager.getSharedPreferences(0).edit();

    public UserProfile(String n) {
            name = n;
    }

    public void addClass(Professor c) {
        boolean hasCourse = false;
        for(Professor currCourse : myCourses){
            if(currCourse.name.equals(c.name)){
                hasCourse = true;
            }
        }
        if(!hasCourse){
            myCourses.add(c);
        }
    }

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
}
