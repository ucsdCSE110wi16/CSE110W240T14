package team14.project110;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class testCourse extends AppCompatActivity {

    public static List<Course> courseList;

    public static int selectedCourse = -1;          //Index of department that is clicked on

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_course);

        Department current = MainActivity.depart.get(MainActivity.selectedDepartment);

        courseList = current.courses;

        createCourseList();

        registerClickCallback();
    }
    //Create the list of courses that is displayed on the screen
    void createCourseList(){

        ArrayAdapter courseArrayAdapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, courseList);

        ListView deptList = (ListView) findViewById(R.id.listView2);
        System.out.println(R.id.listView2);
        deptList.setAdapter(courseArrayAdapter);
    }

    private void registerClickCallback(){

        //List shown on screen
        ListView list = (ListView) findViewById(R.id.listView2);

        //Listener for the list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //What will happen when the list is clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                selectedCourse = position;  //Deparment selected from the list of departments

                //Create new intent to open course list contained by selected department
                Intent selectedIntent = new Intent(testCourse.this, testProfessor.class);
                startActivity(selectedIntent);

            }
        });
    }
}
