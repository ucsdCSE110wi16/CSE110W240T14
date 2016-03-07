package edu.etduongucsd.dopeshit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ERIC on 2/16/2016.
 */
public class ClassInfo {

    public String code = "";
    public String className = "";
    public String professor = "";
    public ArrayList<Lecture> classLectures;
    public int numLec;
    //private int numLecPerWeek;

    public ClassInfo(String code, String className, String professor) {
        super();
        this.code = code;
        this.className = className;
        this.professor = professor;
        //this.numLecPerWeek = numLecPerWeek;
        this.classLectures = new ArrayList<Lecture>();
        this.numLec = this.classLectures.size();
    }

    public ClassInfo(String className, String professor) {
        super();
        this.code = "";
        this.className = className;
        this.professor = professor;
        this.classLectures = new ArrayList<Lecture>();
        this.numLec = this.classLectures.size();
    }

    public ClassInfo(String code, String className, String professor, ArrayList<Lecture> classLecs) {
        super();
        this.code = code;
        this.className = className;
        this.professor = professor;
        this.classLectures = classLecs;
        this.numLec = this.classLectures.size();
    }

    public static final Comparator<ClassInfo> ASC_ORDER = new Comparator<ClassInfo>() {
        @Override
        public int compare(ClassInfo lhs, ClassInfo rhs) {
            String lhsName = lhs.getClassName();
            String rhsName = rhs.getClassName();
            return lhsName.compareTo(rhsName);
        }
    };


    public void addLecToClass(Lecture newLecture) {
        if(classLectures.contains(newLecture)) {
            Lecture lecSelected = classLectures.get(newLecture.getLectureNum());
            List<Note> notesSelected = lecSelected.getNotes();
            List<Note> newNotes = newLecture.getNotes();
            for (Note note : newNotes) {
                notesSelected.add(note);
            }
            Collections.sort(classLectures, Lecture.ASCENDING_LECS);
        }
        else {
            classLectures.add(newLecture);
            Collections.sort(classLectures, Lecture.ASCENDING_LECS);
        }
    }

    public void remLecFromClass(Lecture remLec) {
        if(classLectures.contains(remLec)) {
            classLectures.remove(remLec);
        }
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

    public int getNumLec() {return this.numLec;}

    public void setNumLec(int i) {this.numLec = i;}

    //public int getNumLecPerWeek() { return numLecPerWeek; }

    //public void setNumLecPerWeek(int numLecsPerWeek) {this.numLecPerWeek = numLecPerWeek;}
}
