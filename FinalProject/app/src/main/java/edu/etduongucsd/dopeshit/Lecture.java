package edu.etduongucsd.dopeshit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created on 2/21/2016.
 */
//Lecture object
public class Lecture {

    String dataBaseRef;
    int lectureNum;
    List<Note> notes;
    public int numberOfNotes;
    int count;
    public Professor parentProfessor;
    public String date;

    public Lecture(String parentFirebaseRef, int numberOfLectures){
        dataBaseRef = parentFirebaseRef;
        lectureNum = numberOfLectures;
        notes = new ArrayList<Note>(); //List of courses within department
        numberOfNotes = 0;
        date = "";
    }

    public Lecture(String parentFirebaseRef, int numberOfLectures, int month, int day){
        dataBaseRef = parentFirebaseRef;
        lectureNum = numberOfLectures;
        notes = new ArrayList<Note>(); //List of courses within department
        numberOfNotes = 0;
        date = (month + "/" + day);
    }

    public Lecture(String parentFirebaseRef, int numberOfLectures, String date){
        dataBaseRef = parentFirebaseRef;
        lectureNum = numberOfLectures;
        notes = new ArrayList<Note>(); //List of courses within department
        numberOfNotes = 0;
        this.date = date;
    }


    @Override
    public String toString(){
        return "Lecture "+lectureNum;
    }

    public static final Comparator<Lecture> ASCENDING_LECS = new Comparator<Lecture>() {
        @Override
        public int compare(Lecture lhs, Lecture rhs) {
            return lhs.lectureNum - rhs.lectureNum;
        }
    };

    public static final Comparator<Lecture> ASCENDING_LECS2 = new Comparator<Lecture>() {
        @Override
        public int compare(Lecture lhs, Lecture rhs) {
            return lhs.date.compareTo(rhs.date);
        }
    };


    public void addLectureToFirebase(){
        Firebase ref = new Firebase(dataBaseRef);
        ref.child(toString()).setValue(0); //need to get number from firebase
    }

    public void addLectureToFirebase(int month, int day){
        Firebase ref = new Firebase(dataBaseRef);
        System.out.println("Lecture name "+toString());
        ref.child(toString()).setValue(0); //need to get number from firebase
        ref.child(toString()).child("Date").setValue((month + "/" + day));
    }

    //Add note to Lecture
    public void addNotes(ArrayList<Bitmap> bmp){
        numberOfNotes++;
        Note addedNote = new Note(bmp, numberOfNotes, dataBaseRef+"Lecture "+lectureNum+"/");
        addedNote.parentLecture = this;
        notes.add(addedNote);
        addedNote.addNoteToFirebase();
    }
}
