package team14.project110;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by steven on 2/10/2016.
 */
public class Day {

    List<Note> notes;

    public Day(){
        notes = new ArrayList<Note>(); //List of courses within department
    }

    //Add note to day
    public void addNotes(File file){
        Note addedNote = new Note(file);
        notes.add(addedNote);
    }

 //   public void removeNotes(Note note){
  //  }

}
