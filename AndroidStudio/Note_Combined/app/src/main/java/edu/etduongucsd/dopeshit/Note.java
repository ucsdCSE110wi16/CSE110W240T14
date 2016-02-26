package edu.etduongucsd.dopeshit;

import android.graphics.Bitmap;
import android.util.Base64;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Comparator;

/**
 * Created by steven on 2/10/2016.
 */
public class Note {

    int flag;       //Flags a set of notes has received
    int noteNum;
    String dataBaseRef;
    Bitmap pictureNote;
    String imageFile;

    public Note(){};

    //Notes constructor
    public Note(Byte[] bytes, int numberOfNotes, String parentFireBaseRef) {
        int flag;       //Flags a set of notes has received
        int noteNum;
        String dataBaseRef;
        Bitmap pictureNote;
        String imageFile;

    }

    public Note(int numNote, int flagNum) {
        flag = flagNum;
        noteNum = numNote;
        dataBaseRef = "";
        imageFile = "";
        pictureNote = null;
    }

    //Notes constructor
    public Note(Bitmap bmp, int numberOfNotes, String parentFireBaseRef) {
        flag = 0;
        pictureNote = bmp;
        noteNum = numberOfNotes;
        dataBaseRef = parentFireBaseRef;
    }

    public static final Comparator<Note> ASC_NOTES = new Comparator<Note>() {
        @Override
        public int compare(Note lhs, Note rhs) {
            return lhs.noteNum - rhs.noteNum;
        }
    };

    public int getFlag(){
        return flag;
    }

    public Bitmap getNoteImage(){
        return pictureNote;
    }

    public int getNoteNum(){
        return noteNum;
    }

    public void addNoteToFirebase(){
        Firebase ref = new Firebase(dataBaseRef);
        storeBitmap(pictureNote);
        ref.child("Note "+noteNum).setValue(imageFile);
    }


    public void storeBitmap(Bitmap bmp){
        ByteArrayOutputStream bYte = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bYte);
        bmp.recycle();
        byte[] byteArray = bYte.toByteArray();
        imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
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
