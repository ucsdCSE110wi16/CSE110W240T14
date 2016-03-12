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
    private static CourseListAdapter2 myNoteAdapter;

    public static Professor noteCourseSel;
    public static ArrayList<Note> myNotes = new ArrayList<Note>();
    public List<Professor> noteProfs;

    public static boolean restart;

    public void addToMyNotes(Note newNote) {
        if(myNotes.contains(newNote)) {}
        else {
            myNotes.add(newNote);
            Collections.sort(myNotes, Note.ASC_NOTES);
        }
    }

    public void displayMyNoteList_real() {
        noteProfs = new ArrayList<Professor>();
        for(Note note : HomeScreen.userProfile.userUpNotes){
            boolean hasProf = false;
            for(Professor professor : noteProfs){
                if(note.parentLecture.parentProfessor.name.equals(professor.name)){
                    hasProf = true;
                }
            }
            if(hasProf == false) {
                noteProfs.add(note.parentLecture.parentProfessor);
            }
        }

        myNoteView = (ListView) findViewById(R.id.myNotesList);
        myNoteAdapter = new CourseListAdapter2(MyNotes.this, 0, noteProfs);
        myNoteView.setAdapter(myNoteAdapter);
        myNoteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                noteCourseSel = (Professor) myNoteView.getItemAtPosition(position);

                HomeScreen.selectedDepart = HomeScreen.userProfile.userUpNotes.get(position).parentLecture.parentProfessor.parentCourse.parentDepartment;
                HomeScreen.selectedCourse = HomeScreen.userProfile.userUpNotes.get(position).parentLecture.parentProfessor.parentCourse;
                HomeScreen.selectedProfessor = HomeScreen.userProfile.userUpNotes.get(position).parentLecture.parentProfessor;  //I added

                System.out.println("MyNotes: "+HomeScreen.selectedDepart);
                System.out.println("MyNotes: "+HomeScreen.selectedCourse);
                System.out.println("MyNotes: "+HomeScreen.selectedProfessor);
                System.out.println("MyNotes: "+HomeScreen.selectedLecture);

                Intent selectedIntent = new Intent(MyNotes.this, MyNotesNext.class);
                startActivity(selectedIntent);
            }
        });

    }

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
                finish();
                Intent intent = new Intent(MyNotes.this, HomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyNotes.this, SettingsPage.class));
            }
        });

        TextView title = (TextView) findViewById(R.id.myNotesTitle);
        Typeface myType = Typeface.createFromAsset(getAssets(), "Lob.otf");
        title.setTypeface(myType);
        displayMyNoteList_real();
        if (restart == true) {
            restart = false;

            displayMyNoteList_real();
            finish();
            startActivity(getIntent());
        }
    }

}
