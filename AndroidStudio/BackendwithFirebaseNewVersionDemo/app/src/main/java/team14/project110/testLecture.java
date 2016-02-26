package team14.project110;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class testLecture extends AppCompatActivity {

    public static List<Lecture> lectureList;

    Professor current;

    public static int selectedLecture = -1;          //Index of department that is clicked on

    boolean canClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_lecture);

        current = testProfessor.professorList.get(testProfessor.selectedProfessor);
        lectureList = current.lectures;

        createCourseList();

        registerClickCallback();
    }
    //Create the list of courses that is displayed on the screen
    void createCourseList(){

        ArrayAdapter lectureArrayAdapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, lectureList);
        System.out.println(R.id.listView4);
        ListView lectureListview = (ListView) this.findViewById(R.id.listView4);
       lectureListview.setAdapter(lectureArrayAdapter);

    }

    public void buttonOnClick(View v){
        Button button = (Button)v;
        if(canClick == true) {

            current.addLecture();
            button.setText("Added!");
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
        ListView list = (ListView) findViewById(R.id.listView4);

        //Listener for the list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //What will happen when the list is clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                selectedLecture = position;  //Deparment selected from the list of departments

                //Create new intent to open course list contained by selected department
                Intent selectedIntent = new Intent(testLecture.this, testNote.class);
                startActivity(selectedIntent);

            }
        });
    }
}
