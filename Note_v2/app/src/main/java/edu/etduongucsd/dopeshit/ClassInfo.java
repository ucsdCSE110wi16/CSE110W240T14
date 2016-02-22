package edu.etduongucsd.dopeshit;

/**
 * Created by ERIC on 2/16/2016.
 */
public class ClassInfo {

    private String code = "";
    private String className = "";
    private String professor = "";
    private int numLecPerWeek;

    public ClassInfo(String code, String className, String professor, int numLecPerWeek) {
        super();
        this.code = code;
        this.className = className;
        this.professor = professor;
        this.numLecPerWeek = numLecPerWeek;
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

    public int getNumLecPerWeek() { return numLecPerWeek; }

    public void setNumLecPerWeek(int numLecsPerWeek) {this.numLecPerWeek = numLecPerWeek;}
}
