package edu.etduongucsd.dopeshit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
//MyNotes screen
public class MyNotes extends AppCompatActivity {

    private static ListView myNoteView;
    private static CourseListAdapter myNoteAdapter;

    public static Course noteCourseSel;
    public static ArrayList<Note> myNotes = new ArrayList<Note>();

    public void addToMyNotes(Note newNote) {
        if(myNotes.contains(newNote)) {}
        else {
            myNotes.add(newNote);
            Collections.sort(myNotes, Note.ASC_NOTES);
        }
    }

    public void delFromMyNotes(Note delNote) {
        if(myNotes.contains(delNote)) {
            myNotes.remove(delNote);
            Collections.sort(myNotes, Note.ASC_NOTES);
        }
    }


    public void displayMyNoteList_real() {
        myNoteView = (ListView) findViewById(R.id.myNotesList);
        myNoteAdapter = new CourseListAdapter(MyNotes.this, 0, UserProfile.myUploadCourses);
        myNoteView.setAdapter(myNoteAdapter);
        myNoteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                noteCourseSel = (Course) myNoteView.getItemAtPosition(position);
                //lectureHeading = courseSel.getName();
                //classProf = courseSel.getProfessors().get(position);

                //HomeScreen.selectedProfessor = classProf;
                HomeScreen.selectedDepart = UserProfile.myUploadCourses.get(position).parentDepartment;
                HomeScreen.selectedCourse = UserProfile.myUploadCourses.get(position);

                //classProfName = classProf.getName();

                //int numLecs = classProf.numberOfLectures;

                Intent selectedIntent = new Intent(MyNotes.this, MyNotesNext.class);
                startActivity(selectedIntent);
            }
        });

    }

    /*
    public void displayMyNoteList() {
        myNoteView = (ListView) findViewById(R.id.myNotesList);
        myNoteAdapter = new MyNoteListAdapter(MyNotes.this, 0, myNotes);
        myNoteView.setAdapter(myNoteAdapter);
        myNoteView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        noteSel = (Note) myNoteView.getItemAtPosition(position);
                    }
                }
        );
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        /* Find the toolbar by id, and set it as the action bar. Whenever the 'Note' is clicked,
         * it will return to the home screen.
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyNotes.this, HomeScreen.class));
            }
        });
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyNotes.this, SettingsPage.class));
            }
        });

        displayMyNoteList_real();

    }

}
