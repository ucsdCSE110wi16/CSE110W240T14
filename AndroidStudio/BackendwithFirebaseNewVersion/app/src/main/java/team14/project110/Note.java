package team14.project110;

import com.firebase.client.Firebase;

import java.io.File;

/**
 * Created by steven on 2/10/2016.
 */
public class Note {
    int flag;       //Flags a set of notes has received.
    Byte[] noteImage;
    int noteNum;
    String dataBaseRef;

    public Note(){};

    //Notes constructor
    public Note(Byte[] bytes, int numberOfNotes, String parentFireBaseRef) {
        flag = 0;
        noteImage = bytes;
        noteNum = numberOfNotes;
        dataBaseRef = parentFireBaseRef;
    //    addNoteToFirebase();
    }


    public int getFlag(){
        return flag;
    }

    public Byte[] getNoteImage(){
        return noteImage;
    }

    public int getNoteNum(){
        return noteNum;
    }

    public void addNoteToFirebase(){
        Firebase ref = new Firebase(dataBaseRef);
      //  ref.child("Note "+noteNum).setValue(Base64.encodeToString(noteImage, Base64.DEFAULT));
    }

    public void convertToImage(Byte[] bytesOfImage){

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

}
