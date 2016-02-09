package team14.project110;

/**
 * Created on 2/2/2016.
 */
public class Course {
    String name;

    //Default constructor
    public Course(){
        name = "";
    }
    //Constructor with course name
    public Course(String n){
        name = n;
    }

    @Override
    public String toString(){

        return name;
    }
}
