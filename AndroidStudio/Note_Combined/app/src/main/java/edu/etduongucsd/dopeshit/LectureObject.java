package edu.etduongucsd.dopeshit;

import java.util.ArrayList;

/**
 * Created by ERIC on 2/23/2016.
 */
public class LectureObject {

    public String name;
    public int lecNum;
    public ArrayList<NoteObject> lecList = new ArrayList<NoteObject>();

    public LectureObject(int lecNum, ArrayList<NoteObject> lecList) {
        super();
        this.lecNum = lecNum;
        this.lecList = lecList;
        this.name = "Lecture " + lecNum;
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

    public ArrayList<NoteObject> getLecList() {
        return lecList;
    }

    public void setLecList(ArrayList<NoteObject> lecList) {
        this.lecList = lecList;
    }
}
