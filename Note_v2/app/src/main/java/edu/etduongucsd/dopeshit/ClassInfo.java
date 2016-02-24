package edu.etduongucsd.dopeshit;

import java.util.ArrayList;

/**
 * Created by ERIC on 2/16/2016.
 */
public class ClassInfo {

    private String code = "";
    private String className = "";
    private String professor = "";
    //private int numLecPerWeek;
    public ArrayList<LectureObject> classLecs;

    public ClassInfo(String code, String className, String professor) {
        super();
        this.code = code;
        this.className = className;
        this.professor = professor;
        //this.numLecPerWeek = numLecPerWeek;
        this.classLecs = new ArrayList<LectureObject>();
    }

    public ClassInfo(String className, String professor) {
        super();
        this.code = "";
        this.className = className;
        this.professor = professor;
        this.classLecs = new ArrayList<LectureObject>();
    }

    public ClassInfo(String className, String professor, ArrayList<LectureObject> lecList) {
        super();
        this.code = "";
        this.className = className;
        this.professor = professor;
        this.classLecs = lecList;
    }

    public ClassInfo(String code, String className, String professor, ArrayList<LectureObject> lecList) {
        super();
        this.code = code;
        this.className = className;
        this.professor = professor;
        this.classLecs = lecList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    //public int getNumLecPerWeek() { return numLecPerWeek; }

    //public void setNumLecPerWeek(int numLecsPerWeek) {this.numLecPerWeek = numLecPerWeek;}

    public ArrayList<LectureObject> getClassLecs() {return classLecs;}

    public void setClassLecs(ArrayList<LectureObject> lecList) {this.classLecs = lecList;}
}
