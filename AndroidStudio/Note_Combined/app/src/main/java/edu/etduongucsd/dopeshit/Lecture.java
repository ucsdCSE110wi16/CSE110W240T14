package edu.etduongucsd.dopeshit;

import com.firebase.client.Firebase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/21/2016.
 */
public class Lecture {

    String dataBaseRef;
    int lectureNum;
    List<Note> notes;
    int numberOfNotes;

    public Lecture(String parentFirebaseRef, int numberOfLectures){
        dataBaseRef = parentFirebaseRef;
        lectureNum = numberOfLectures;
        notes = new ArrayList<Note>(); //List of courses within department
        numberOfNotes = 0;
        //addLectureToFirebase();
    }

    public String getDataBaseRef(){
        return dataBaseRef;
    }

    public int getLectureNum(){
        return lectureNum;
    }

    //Get notes
    public List<Note> getNotes(){
        return notes;
    }

    public int getNumberOfNotes(){
        return numberOfNotes;
    }

    public void addLectureToFirebase(){
        Firebase ref = new Firebase(dataBaseRef);
        ref.child("Lecture "+lectureNum).setValue(0); //need to get number from firebase
    }
    //Add note to Lecture
    public void addNotes(Byte[] bytes){
        Note addedNote = new Note(bytes, numberOfNotes, dataBaseRef+"Lecture "+lectureNum+"/");
        notes.add(addedNote);
        numberOfNotes++;
        addedNote.addNoteToFirebase();
    }
}
