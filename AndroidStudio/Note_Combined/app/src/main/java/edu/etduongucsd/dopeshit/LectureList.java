package edu.etduongucsd.dopeshit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LectureList extends AppCompatActivity {

    private static LectureListAdapter lecAdapter;

    public ListView lecListView;
    public static ArrayList<Lecture> lecList = new ArrayList<Lecture>();

    public static Lecture lecSelected;
    public static int lecNum;

    //TextView noteTitle = (TextView) findViewById(R.id.noteListTitle);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_list);

        /* Find the toolbar by id, and set it as the action bar. Whenever the 'Note' is clicked,
         * it will return to the home screen.
         *
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
/        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LectureList.this, HomeScreen.class));
            }
        });
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LectureList.this, SettingsPage.class));
            }
        });
*/
        Lecture lec1 = new Lecture(1);
        Lecture lec2 = new Lecture(2);
        Lecture lec3 = new Lecture(3);

        addToLecList(lec1);
        addToLecList(lec2);
        addToLecList(lec3);

        TextView lecHeading = (TextView) findViewById(R.id.lectureTitle);
        lecHeading.setText(MyClasses.lectureHeading);

        displayLecList();

    }

    public void addToLecList(Lecture newLec) {
        if(lecList.contains(newLec)) {}
        else {
            lecList.add(newLec);
            Collections.sort(lecList, Lecture.ASCENDING_LECS);
        }
    }

    public void delFromLecList(Lecture delLec) {
        if(lecList.contains(delLec)) {
            lecList.remove(delLec);
            Collections.sort(lecList, Lecture.ASCENDING_LECS);
        }
    }

    public void displayLecList() {
        lecListView = (ListView) findViewById(R.id.lectureListView);
        lecAdapter = new LectureListAdapter(LectureList.this, 0, lecList);
        lecListView.setAdapter(lecAdapter);
        lecListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //TextView noteTitle = (TextView) findViewById(R.id.noteListTitle);
                        lecSelected = (Lecture) lecListView.getItemAtPosition(position);
                        lecNum = lecSelected.getLectureNum();
                        int numNotes = lecSelected.getNumberOfNotes();
                        //noteTitle.setText("Lecture " + lecNum);
                        Toast.makeText(LectureList.this, "Lecture " + lecNum + " has " + numNotes + " notes.", Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(LectureList.this, NoteList.class));
                        openNoteList(view);


                    }
                }
        );
    }

    public void openNoteList(View view) {
        Intent moveToNoteList = new Intent(this, NoteList.class);
        startActivity(moveToNoteList);
    }

}
