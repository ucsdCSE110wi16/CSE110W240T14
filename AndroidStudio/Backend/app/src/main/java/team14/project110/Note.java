package team14.project110;

/**
 * Created by steven on 2/10/2016.
 */
public class Note {
    int flag;       //Flags a set of notes has received.

    //Notes constructor
    public Note() {
        flag = 0;
    }

    public void incrFlag() {
        if( flag == 1 ) {
            flag = 0;
        }
        else {
            flag = 1;
        }
    }


}
