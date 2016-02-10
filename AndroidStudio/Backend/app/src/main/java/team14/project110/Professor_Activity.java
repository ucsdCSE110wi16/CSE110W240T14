package team14.project110;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class Professor_Activity extends AppCompatActivity {

        public static List<Professor> professorList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_course_);

            Course current = Course_Activity.courseList.get(Course_Activity.selectedCourse);

            professorList = current.professors;

            createProfessorList();

            // registerClickCallback(); //(not added yet)
        }
        //Create the list of courses that is displayed on the screen
        void createProfessorList(){

            ArrayAdapter professorArrayAdapter = new ArrayAdapter
                    (this, android.R.layout.simple_list_item_1, professorList);

            ListView courseList = (ListView) findViewById(R.id.listView3);
            courseList.setAdapter(professorArrayAdapter);
        }
}
