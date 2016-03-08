package edu.etduongucsd.dopeshit;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 3/6/2016.
 */
public class newMainActivity {
    //TEST
    public static final String path = "cse110-course-list.txt";   //Text file name

    private newWordScanner wordScanner;                    //Object that will read text file

    public static int selectedDepartment = -1;          //Index of department that is clicked on

    public static List<Department> depart = new ArrayList<Department>();              //List of departments

    public boolean creatingDatabase = false;

    //@Override
    protected void onCreate(Bundle savedInstanceState) {
     /*   super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        if(creatingDatabase) {
            depart.clear();
            Firebase ref = new Firebase("https://note110.firebaseio.com/");
            ref.setValue(0);
            wordScanner = new newWordScanner(this);    //Construct wordScanner object
            wordScanner.parseList(path);            //Go through words in lines and sort according to dept and course
        }
        if(!creatingDatabase) {
            Firebase ref = new Firebase("https://note110.firebaseio.com/Departments/");
            //  addListenerForSingleValueEvent
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    // do some stuff once
                    depart.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Department dept = new Department(postSnapshot.getKey());

                        depart.add(dept);
                        for (DataSnapshot innerSnapshot : postSnapshot.getChildren()) {
                            Course course = new Course(innerSnapshot.getKey(), dept.dataBaseRef + dept.name + "/");
                            dept.courses.add(course);
                            for (DataSnapshot profSnapshot : innerSnapshot.getChildren()) {
                                Professor professor = new Professor(profSnapshot.getKey(), course.dataBaseRef + course.name + "/");
                                course.professors.add(professor);
                                for (DataSnapshot lectureSnapshot : profSnapshot.getChildren()) {
                                    Lecture lecture = new Lecture(professor.dataBaseRef + professor.name + "/", professor.numberOfLectures);
                                    professor.lectures.add(lecture);
                                }
                            }
                        }
                    }
                    createList();                           //Create ui list of departments shown on phone
                }

                //    @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        }
    }
    //This will list departments on the screen
    void createList(){
        if(creatingDatabase) {
            depart = wordScanner.departments;   //List of departments
        }
        //Adapter that controls what is shown by UI list
        ArrayAdapter departmentArrayAdapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, depart);

        //Find and assign UI list object
        ListView deptList = (ListView) findViewById(R.id.listView);
        //Set adapter to this UI list
        deptList.setAdapter(departmentArrayAdapter);
    }
    // used for login txt
    private EditText loginID;
    // used for login button
    private Button loginButton;
    // sample button used for back
    public void buttonOnClick (View v) {
        String loginName;
        Login log;
        boolean success = false;


        loginButton = (Button) v;

        loginID = (EditText) findViewByID(R.id.txtLogin); //this is a sample id
        loginName = loginID.getText().toString();

        log = new Login(/* should contain our course list*///); // maybe already have this constructed
      /*  success = log.getNames(loginName);

*/
    }


}
