package team14.project110;

import java.io.File;

/**
 * Created by steven on 2/10/2016.
 */
public class Note {
    int flag;       //Flags a set of notes has received.
    File noteFile;

    //Notes constructor
    public Note(File file) {
        flag = 0;
        noteFile = file;
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
