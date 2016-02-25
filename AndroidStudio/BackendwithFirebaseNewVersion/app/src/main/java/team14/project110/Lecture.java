package team14.project110;

import android.graphics.Bitmap;

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
    public void addNotes(Bitmap bmp){
        Note addedNote = new Note(bmp, numberOfNotes, dataBaseRef+"Lecture "+lectureNum+"/");
        notes.add(addedNote);
        numberOfNotes++;
        addedNote.addNoteToFirebase();
    }

    public Bitmap convertToBitmap(String imageFile) {
        byte[] imageAsBytes = Base64.decode(imageFile, Base64.DEFAULT);
        pictureNote = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    public void displayNotes(){
        Firebase ref = dataBaseRef+"Lecture "+lectureNum+"/";
        //  addListenerForSingleValueEvent
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                            for (DataSnapshot snapshot : snapshot.getChildren()) {
                                Note note = new Note(convertToBitmap(snapshot.getValue()), numberOfNotes, dataBaseRef+"Lecture "+lectureNum+"/");
                                notes.add(note);
                            }
                        }
                    }
                }
            }
    }
}
