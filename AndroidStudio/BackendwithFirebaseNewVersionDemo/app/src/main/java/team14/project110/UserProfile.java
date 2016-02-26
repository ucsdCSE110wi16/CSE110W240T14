package team14.project110;

import java.util.List;

/**
 * Created on 2/25/2016.
 */
public class UserProfile {
    String name;
    List<Course> myCourses;
    List<Note> myNotes;

    public UserProfile(String n) {
            name = n;
    }

    public void addClass(Course c) {
        boolean hasCourse = false;
        for(Course currCourse : myCourses){
            if(currCourse.name.equals(c.name)){
                hasCourse = true;
            }
        }
        if(!hasCourse){
            myCourses.add(c);
        }
    }

    public void addMyNote(Note note){
        myNotes.add(note);
    }

    public boolean isUser(String s) {
        return name.equals(s);
    }

}
