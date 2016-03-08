package edu.etduongucsd.dopeshit;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 3/6/2016.
 */
public class newWordScanner {
    //Current context
    private Context currContext;
    //Selected department when sorting courses
    Department thisDept;
    //Selected department when sorting profiles
    UserProfile thisProf;
    //List of departments
    List<Department> departments = new ArrayList<Department>();
    //List of departments
    List<UserProfile> profiles = new ArrayList<UserProfile>();
    //List of flagged courses
    List<Course> courses = new ArrayList<Course>();

    //WordScanner Constructor
    public newWordScanner(Context context){
        this.currContext = context;
    }
    //WordScanner Constructor for Profiles
    public newWordScanner (Context context, List<Course> c) {
        this.currContext = context;
        courses = c;
    }
    //List of lines that are taken from text file
    public List<String> readLine(String path){
        List<String> lines = new ArrayList<>(); //List to hold lines of text file

        AssetManager asset = currContext.getAssets();   //Location of text file

        try {
            InputStream input = asset.open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;    //Line of text currently read
            //Read through all lines of text file
            while ((line = reader.readLine()) != null)
                lines.add(line);    //Add each line to list
        } catch (IOException e){
            e.printStackTrace();
        }

        return lines;   //List of lines that make up text file
    }

    public void parseList(String textFile){

        //List of lines that make up the text file
        List<String> lines;
        //Array of the words in a single line
        String[] words;

        lines = readLine(textFile); //Add lines of text file into lines list
        //Loop through individual lines of list
        for(String currString : lines) {

            words = currString.split("/");  //Separate words that are in the same line(Need to change to / or some other symbol)
            //Loop through individual words in a line
            for (String word : words) {
                //Check what department to add to (by checking first word)
                //If not department add as a course(need to add professors, weeks, ...)
                if(words[0].equals(word)){
                    addDepartment(word);
                }
                else{
                    thisDept.addCourse(word);
                }
            }
        }
    }

    public void parseProfile (String textFile) {
        //List of lines that make up the text file
        List<String> lines;
        //Array of the words in a single line
        String[] words;

        lines = readLine(textFile); //Add lines of text file into lines list
        //Loop through individual lines of list
        for(String currString : lines) {

            words = currString.split("/");  //Separate words that are in the same line(Need to change to / or some other symbol)
            //Loop through individual words in a line
            for (String word : words) {
                if (words[0].equals (word)) {
                    addProfile(word);
                }
                else {
                    for (Course tmpC : courses) {
                        if (tmpC.name.equals(word)) {
                            thisProf.addClass(tmpC);
                        }
                    }
                }
            }
        }
    }
    //Add profile to the profile list and save name of the current profile being added to
    private void addProfile (String name) {
        boolean found = false;
        for (UserProfile tmpPrf : profiles) {
            if (tmpPrf.name.equals(name)) {
                found = true;
                thisProf = tmpPrf;
            }
        }
        if (!found) {
            UserProfile p = new UserProfile (name);
            thisProf = p;
            profiles.add (p);
        }
    }
    //Add department to department list and save name of current department being added to
    private void addDepartment(String name){
        boolean found = false;
        for(Department tempDepartment : departments){
            if(tempDepartment.name.equals(name)){
                found = true;
            }
        }
        if(!found){
            Department d = new Department(name);    //Create new department object
            thisDept = d;                           //Save reference to department
            thisDept.addDepartmentToFirebase();
            departments.add(d);                     //Add this department to list of departments
        }
    /*    //If department had been added already save name.
        if(departments.contains(name)){
            int index = departments.indexOf(name);  //Found index of department
            thisDept = departments.get(index);      //Saving reference to department
        }
        //If department hadn't been added yet add it. Save name of department.
        else {
            Department d = new Department(name);    //Create new department object
            thisDept = d;                           //Save reference to department
            thisDept.addDepartmentToFirebase();
            departments.add(d);                     //Add this department to list of departments
        }*/
    }

}
