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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LectureList extends AppCompatActivity {

    public ListView lecListView;
<<<<<<< HEAD
    public static ArrayList<Lecture> lecList = new ArrayList<Lecture>();

    public static Lecture lecSelected;
    public static int lecNum;

    //TextView noteTitle = (TextView) findViewById(R.id.noteListTitle);

=======
    public ArrayList<LectureObject> lecList = new ArrayList<LectureObject>();
>>>>>>> 5ef9270ab7c17fdbce5e72fe0807110eadcbd11f

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
<<<<<<< HEAD
*/
        Lecture lec1 = new Lecture(1);
        Lecture lec2 = new Lecture(2);
        Lecture lec3 = new Lecture(3);

        addToLecList(lec1);
        addToLecList(lec2);
        addToLecList(lec3);

        TextView lecHeading = (TextView) findViewById(R.id.lectureTitle);
        lecHeading.setText(MyClasses.lectureHeading);

=======

        ArrayList<NoteObject> lec1notes = new ArrayList<NoteObject>();
        LectureObject lec1 = new LectureObject(1, lec1notes);
        lecList.add(lec1);
>>>>>>> 5ef9270ab7c17fdbce5e72fe0807110eadcbd11f
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
<<<<<<< HEAD
                        //TextView noteTitle = (TextView) findViewById(R.id.noteListTitle);
                        lecSelected = (Lecture) lecListView.getItemAtPosition(position);
                        lecNum = lecSelected.getLectureNum();
                        int numNotes = lecSelected.getNumberOfNotes();
                        //noteTitle.setText("Lecture " + lecNum);
                        Toast.makeText(LectureList.this, "Lecture " + lecNum + " has " + numNotes + " notes.", Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(LectureList.this, NoteList.class));
                        openNoteList(view);
=======
                        LectureObject lecSelect = (LectureObject) lecListView.getItemAtPosition(position);
>>>>>>> 5ef9270ab7c17fdbce5e72fe0807110eadcbd11f


                    }
                }
        );
    }

    public void openNoteList(View view) {
        Intent moveToNoteList = new Intent(this, NoteList.class);
        startActivity(moveToNoteList);
    }

}
