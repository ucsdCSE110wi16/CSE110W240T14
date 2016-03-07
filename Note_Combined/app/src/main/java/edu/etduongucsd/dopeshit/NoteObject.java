package edu.etduongucsd.dopeshit;

/**
 * Created by ERIC on 2/23/2016.
 */
public class NoteObject {

    public String forClass;
    public int forLecNum;

    public NoteObject(String className, int lecNum) {
        super();
        this.forClass = className;
        this.forLecNum = lecNum;
    }

    public String getForClass() {
        return forClass;
    }

    public void setForClass(String name) {
        this.forClass = name;
    }

    public int getForLecNum() {
        return forLecNum;
    }

    public void setForLecNum(int num) {
        this.forLecNum = num;
    }
}
