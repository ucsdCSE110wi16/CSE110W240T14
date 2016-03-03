package edu.etduongucsd.dopeshit;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//Professor's page
public class SelectedCourse extends AppCompatActivity{

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_note);

        button = (Button) findViewById(R.id.addButton);

        /* Find the toolbar by id, and set it as the action bar. Whenever the 'Note' is clicked,
         * it will return to the home screen.
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectedCourse.this, HomeScreen.class));
            }
        });
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectedCourse.this, SettingsPage.class));
            }
        });

        createCourseList();

        registerClickCallback();

        buttonOnClick();

    }

    public void buttonOnClick(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeScreen.selectedCourse.addProfessor("Professor "+(HomeScreen.selectedCourse.professors.size()+1));
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });
    }

    //Create the list of courses that is displayed on the screen
    private void createCourseList(){
        ArrayAdapter professorArrayAdapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, HomeScreen.selectedCourse.professors);
        System.out.println(R.id.listView5);
        ListView professorListview = (ListView) this.findViewById(R.id.listView5);
        professorListview.setAdapter(professorArrayAdapter);

    }

    private void registerClickCallback(){

        //List shown on screen
        ListView list = (ListView) findViewById(R.id.listView5);

        //Listener for the list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //What will happen when the list is clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                HomeScreen.selectedProfessor = HomeScreen.selectedCourse.professors.get(position);  //Deparment selected from the list of departments

                //Create new intent to open course list contained by selected department
                Intent selectedIntent = new Intent(SelectedCourse.this, testNote.class);
                startActivity(selectedIntent);

            }
        });
    }
}
