package team14.project110;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class Course_Activity extends AppCompatActivity {

    public static List<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_);

        Department current = MainActivity.depart.get(MainActivity.selectedDepartment);

        courseList = current.courses;

        createCourseList();

        // registerClickCallback(); //(not added yet)
    }
    //Create the list of courses that is displayed on the screen
    void createCourseList(){

        ArrayAdapter courseArrayAdapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, courseList);

        ListView deptList = (ListView) findViewById(R.id.listView2);
        deptList.setAdapter(courseArrayAdapter);
    }
}
