package edu.etduongucsd.dopeshit;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LectureList extends AppCompatActivity {

    public ListView lecListView;
    public ArrayList<LectureObject> lecList = new ArrayList<LectureObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_list);

        /* Find the toolbar by id, and set it as the action bar. Whenever the 'Note' is clicked,
         * it will return to the home screen.
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

        ArrayList<NoteObject> lec1notes = new ArrayList<NoteObject>();
        LectureObject lec1 = new LectureObject(1, lec1notes);
        lecList.add(lec1);
        displayLecList();

    }

    public void addToLecList(int lecNum, ArrayList<NoteObject> noteList) {
        LectureObject newLec = new LectureObject(lecNum, noteList);
        lecList.add(newLec);
    }

    public void delFromLecList(ArrayList<LectureObject> delList) {
        lecList.remove(delList);
    }

    public void displayLecList() {
        lecListView = (ListView) findViewById(R.id.lectureListView);
        ArrayAdapter<LectureObject> adapter = new ArrayAdapter<LectureObject>(this, R.layout.lecture_list, lecList);
        lecListView.setAdapter(adapter);
        lecListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        LectureObject lecSelect = (LectureObject) lecListView.getItemAtPosition(position);


                    }
                }
        );
    }

}
