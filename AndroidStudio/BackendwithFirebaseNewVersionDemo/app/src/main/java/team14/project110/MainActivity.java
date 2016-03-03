package team14.project110;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//TEST
    public static final String path = "cse110-course-list.txt";   //Text file name

    private WordScanner wordScanner;                    //Object that will read text file

    public static int selectedDepartment = -1;          //Index of department that is clicked on

    public static List<Department> depart = new ArrayList<Department>();              //List of departments

    public boolean creatingDatabase = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

/*if(creatingDatabase) {
    depart.clear();
    Firebase ref = new Firebase("https://note110.firebaseio.com/");
    ref.setValue(0);
        wordScanner = new WordScanner(this);    //Construct wordScanner object
        wordScanner.parseList(path);            //Go through words in lines and sort according to dept and course
    depart = wordScanner.departments;
    creatingDatabase = false;
    Intent intent = getIntent();
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    finish();
    startActivity(intent);
}*/
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
            registerClickCallback();
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

    //This will do something when a department on the list is clicked on
    private void registerClickCallback(){

        //List shown on screen
        ListView list = (ListView) findViewById(R.id.listView);

        //Listener for the list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //What will happen when the list is clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                selectedDepartment = position;  //Deparment selected from the list of departments

                //Create new intent to open course list contained by selected department
                Intent selectedIntent = new Intent(MainActivity.this, testCourse.class);
                startActivity(selectedIntent);

            }
        });
    }

    public void buttonOnClickResetData(View v){
        Button button = (Button)v;
        depart.clear();
        Firebase ref = new Firebase("https://note110.firebaseio.com/");
        ref.setValue(0);
        wordScanner = new WordScanner(this);    //Construct wordScanner object
        wordScanner.parseList(path);            //Go through words in lines and sort according to dept and course
        depart = wordScanner.departments;
        creatingDatabase = false;
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }
}
