package edu.etduongucsd.dopeshit;

import java.util.ArrayList;

/**
 * Created by ERIC on 2/16/2016.
 */
public class ClassList {

    public String name;
    public ArrayList<ClassInfo> classList = new ArrayList<ClassInfo>();

    public ClassList(String name, ArrayList<ClassInfo> classList) {
        super();
        this.name = name;
        this.classList = classList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ClassInfo> getClassList() {
        return classList;
    }

    public void setClassList(ArrayList<ClassInfo> classList) {
        this.classList = classList;
    }
}
