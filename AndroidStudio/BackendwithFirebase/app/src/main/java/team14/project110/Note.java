package team14.project110;

import com.firebase.client.Firebase;

import java.io.File;

/**
 * Created by steven on 2/10/2016.
 */
public class Note {
    int flag;       //Flags a set of notes has received.
    String pictureLink;
    int noteNum;
    Professor professor;

    public Note(){};

    //Notes constructor
    public Note(String url, Professor prof) {
        flag = 0;
        pictureLink = url;
        professor = prof;
        noteNum = professor.numberOfNotes;
        professor.numberOfNotes++;
    }
    //Toggle Flag
    public void incrFlag() {
        if( flag == 1 ) {
            flag = 0;
        }
        else {
            flag = 1;
        }
    }

    public int getFlag(){
        return flag;
    }

    public String getNoteFile(){
        return pictureLink;
    }

    public void addNote(int weekIndex, int dayIndex){
        Firebase ref = new Firebase("https://note110.firebaseio.com/Departments/"+professor.thisCourse.departName+"/"
                +professor.thisCourse.toString()+"/"+professor.toString()+"/Week "+weekIndex+"/Day "+dayIndex+"/Notes/");
        ref.child("Note "+noteNum).setValue(pictureLink);
    }

}
