package team14.project110;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class testProfessor extends AppCompatActivity {

    public static List<Professor> professorList;

    public static int selectedProfessor = -1;          //Index of department that is clicked on

    public int numOfProf;

    Course current;

    boolean canClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_professor);

        current = testCourse.courseList.get(testCourse.selectedCourse);

        professorList = current.professors;

        numOfProf = current.professors.size();


            createCourseList();

            registerClickCallback();
    }
    //Create the list of courses that is displayed on the screen
    void createCourseList(){

        ArrayAdapter professorArrayAdapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, professorList);
        System.out.println(R.id.listView3);
        ListView profListview = (ListView) this.findViewById(R.id.listView3);
            profListview.setAdapter(professorArrayAdapter);

    }

    public void buttonOnClick(View v){
        Button button = (Button)v;
        if(canClick == true) {
            current.addProfessor("Professor " + (1 + numOfProf));
            button.setText("Clicked!");
            canClick = false;
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            startActivity(intent);
        }
    }

    public void buttonOnClickRefresh(View v){
        Button button = (Button)v;
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }

    private void registerClickCallback(){

        //List shown on screen
        ListView list = (ListView) findViewById(R.id.listView3);

        //Listener for the list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //What will happen when the list is clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                selectedProfessor = position;  //Deparment selected from the list of departments

                //Create new intent to open course list contained by selected department
                Intent selectedIntent = new Intent(testProfessor.this, testLecture.class);
                startActivity(selectedIntent);

            }
        });
    }
}
