package edu.etduongucsd.dopeshit;

import java.util.ArrayList;

/**
 * Created by ERIC on 2/23/2016.
 */
public class LectureObject {

    public String name;
    public int lecNum;
    public ArrayList<Note> lecList;

    public LectureObject(int lecNum, ArrayList<Note> lecList) {
        super();
        this.lecNum = lecNum;
        this.lecList = lecList;
        this.name = "Lecture " + lecNum;
    }

    public void addNoteToLec(Note newNote) {
        this.lecList.add(newNote);
    }

    public void delNoteFromLec(Note delNote) {
        this.lecList.remove(delNote);
    }

    public String getName() {
        return name;
    }

    public void setName(int i) {
        this.name = "Lecture " + i;
    }

    public int getLecNum() {
        return lecNum;
    }

    public void setLecNum(int num) {
        this.lecNum = num;
    }

    public ArrayList<Note> getLecList() {
        return lecList;
    }

    public void setLecList(ArrayList<Note> lecList) {
        this.lecList = lecList;
    }
}
