package team14.project110;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

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
    Bitmap pictureNote;

    public Lecture(String parentFirebaseRef, int numberOfLectures){
        dataBaseRef = parentFirebaseRef;
        lectureNum = numberOfLectures;
        notes = new ArrayList<Note>(); //List of courses within department
        numberOfNotes = 0;
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

    @Override
    public String toString(){
        return "Lecture "+(lectureNum + 1);
    }

    public void addLectureToFirebase(){
        Firebase ref = new Firebase(dataBaseRef);
        ref.child("Lecture "+lectureNum).setValue(0); //need to get number from firebase
    }
    //Add note to Lecture
    public void addNotes(Bitmap bmp){
        Note addedNote = new Note(bmp, numberOfNotes, dataBaseRef+"Lecture "+lectureNum+"/");
        notes.add(addedNote);
        numberOfNotes++;
        addedNote.addNoteToFirebase();
    }

    public Bitmap convertToNoteBitmap(String imageFile) {
        byte[] imageAsBytes = Base64.decode(imageFile, Base64.DEFAULT);
        pictureNote = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        return pictureNote;
    }

    public void displayNotes(){

        Firebase ref = new Firebase(dataBaseRef+"Lecture "+lectureNum+"/");

        //  addListenerForSingleValueEvent
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot noteSnapshot : snapshot.getChildren()) {
                    Bitmap bmp;
                    bmp = convertToNoteBitmap((String) noteSnapshot.getValue());
                    Note note = new Note( bmp, numberOfNotes, dataBaseRef + "Lecture " + lectureNum + "/");
                    notes.add(note);
                }
            }

            //    @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
}
