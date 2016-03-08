package edu.etduongucsd.dopeshit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
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

//LectureList page
public class testNote extends AppCompatActivity {

    public static Professor currentProfessor;

    private static LectureListAdapter lecAdapter;
    public ListView lecListView;
    public static List<Lecture> lecList;

    public static Lecture lecSel;
    public static String lecName;

    Button button;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_note);

        button = (Button) findViewById(R.id.addMyClassBut);
        button2 = (Button) findViewById(R.id.newLecBut);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(testNote.this, HomeScreen.class));
            }
        });
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(testNote.this, SettingsPage.class));
            }
        });

        currentProfessor = HomeScreen.selectedProfessor;
        TextView lecTitle = (TextView) findViewById(R.id.lectureTitle);
        lecTitle.setText(currentProfessor.getName());

        lecList  = HomeScreen.selectedProfessor.lectures;
        createLectureList();

        //registerClickCallback();

        buttonOnClick();
        button2OnClick();
    }


    public void buttonOnClick(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Professor newProf = HomeScreen.selectedProfessor;
                Course newCourse = newProf.parentCourse;
                if(UserProfile.myCourses.contains(newCourse)) {
                    Toast.makeText(testNote.this, HomeScreen.selectedDepart.getName() + " " + HomeScreen.selectedCourse.getName() + " is already in your list of classes!", Toast.LENGTH_LONG).show();
                }
                else {
                    UserProfile.myCourses.add(newCourse);
                    Toast.makeText(testNote.this, HomeScreen.selectedDepart.getName() + " " + HomeScreen.selectedCourse.getName() + " has been added to your classes!", Toast.LENGTH_LONG).show();
                }

                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });
    }

    //Adda Lecture
    public void button2OnClick(){
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeScreen.selectedProfessor.addLecture();
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });
    }

    //Create the list of courses that is displayed on the screen
    void createLectureList(){

        lecListView = (ListView)findViewById(R.id.lectureListView);
        lecAdapter = new LectureListAdapter(testNote.this, 0, lecList);
        lecListView.setAdapter(lecAdapter);
        lecListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HomeScreen.selectedLecture = HomeScreen.selectedProfessor.lectures.get(position);
                lecSel = (Lecture) lecListView.getItemAtPosition(position);
                lecName = lecSel.toString();
                Intent selectedIntent = new Intent(testNote.this, MainActivity.class);
                startActivity(selectedIntent);

            }
        });

        /*ArrayAdapter noteArrayAdapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, HomeScreen.selectedProfessor.lectures);
        System.out.println(R.id.listView5);
        ListView noteListview = (ListView) this.findViewById(R.id.listView5);
        noteListview.setAdapter(noteArrayAdapter);*/

    }

    private void registerClickCallback(){

        //List shown on screen
        ListView list = (ListView) findViewById(R.id.lectureListView);

        //Listener for the list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //What will happen when the list is clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                HomeScreen.selectedLecture = HomeScreen.selectedProfessor.lectures.get(position);  //Deparment selected from the list of departments

                //Create new intent to open course list contained by selected department
                Intent selectedIntent = new Intent(testNote.this, MainActivity.class);
                startActivity(selectedIntent);

            }
        });
    }
}
