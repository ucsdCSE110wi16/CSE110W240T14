package edu.etduongucsd.dopeshit;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//MyNotes screen
public class MyNotes extends AppCompatActivity {

    private static ListView myNoteView;
    private static CourseListAdapter1 myNoteAdapter;

    public static Professor noteCourseSel;
    public static ArrayList<Note> myNotes = new ArrayList<Note>();
    public List<Professor> noteProfs;

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
/////STOPDTOPSROPSTOPSTOPSTOP

    public void displayMyNoteList_real() {
        noteProfs = new ArrayList<Professor>();
        for(Note note : HomeScreen.userProfile.userUpNotes){
            noteProfs.add(note.parentLecture.parentProfessor);
        }

        myNoteView = (ListView) findViewById(R.id.myNotesList);
        myNoteAdapter = new CourseListAdapter1(MyNotes.this, 0, noteProfs);
        myNoteView.setAdapter(myNoteAdapter);
        myNoteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                noteCourseSel = (Professor) myNoteView.getItemAtPosition(position);
                //lectureHeading = courseSel.getName();
                //classProf = courseSel.getProfessors().get(position);

                //HomeScreen.selectedProfessor = classProf;
                HomeScreen.selectedDepart = HomeScreen.userProfile.userUpNotes.get(position).parentLecture.parentProfessor.parentCourse.parentDepartment;
                HomeScreen.selectedCourse = HomeScreen.userProfile.userUpNotes.get(position).parentLecture.parentProfessor.parentCourse;
                HomeScreen.selectedProfessor = HomeScreen.userProfile.userUpNotes.get(position).parentLecture.parentProfessor;  //I added
                HomeScreen.selectedLecture = HomeScreen.userProfile.userUpNotes.get(position).parentLecture;    //I added

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

        TextView title = (TextView) findViewById(R.id.myNotesTitle);
        Typeface myType = Typeface.createFromAsset(getAssets(), "AD.ttf");
        title.setTypeface(myType);

        displayMyNoteList_real();

    }

}
