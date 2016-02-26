package team14.project110;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by on 2/2/2016.
 */
public class WordScanner {
    //Current context
    private Context currContext;
    //Selected department when sorting courses
    Department thisDept;
    //List of departments
    List<Department> departments = new ArrayList<Department>();

    //WordScanner Constructor
    public WordScanner(Context context){
        this.currContext = context;
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
