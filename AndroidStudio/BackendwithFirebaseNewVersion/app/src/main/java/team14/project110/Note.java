package team14.project110;

import com.firebase.client.Firebase;

import java.io.File;

/**
 * Created by steven on 2/10/2016.
 */
public class Note {
    int flag;       //Flags a set of notes has received
    int noteNum;
    String dataBaseRef;
    Bitmap pictureNote;

    public Note(){};

    //Notes constructor
    public Note(Bitmap bmp, int numberOfNotes, String parentFireBaseRef) {
        flag = 0;
        pictureNote = bmp;
        noteNum = numberOfNotes;
        dataBaseRef = parentFireBaseRef;
    //    addNoteToFirebase();
    }


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
        ref.child("Note "+noteNum).setValue(storeBitmap(pictureNote));
    }


    public void storeBitmap(Bitmap bmp){
        ByteArrayOutputStream bYte = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bYte);
        bmp.recycly();
        byte[] byteArray = bYte.toByteArray();
        String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
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
