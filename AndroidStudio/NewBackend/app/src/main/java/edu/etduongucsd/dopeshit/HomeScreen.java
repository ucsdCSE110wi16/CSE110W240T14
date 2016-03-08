package edu.etduongucsd.dopeshit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
//Main menu
public class HomeScreen extends AppCompatActivity {

    public static List<Department> depart = new ArrayList<Department>();              //List of departments

    public static Department selectedDepart;
    public static Course selectedCourse;
    public static Professor selectedProfessor;
    public static Lecture selectedLecture;
    public static Note selectedNote;
    public static UserProfile userProfile = Login.myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Firebase.setAndroidContext(this);

        setupData();

        /* Find the toolbar by id, and set it as the action bar. Whenever the 'Note' is clicked,
         * it will return to the home screen.
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this, SettingsPage.class));
            }
        });

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Sweet Pea_2.ttf");
        Button myNotesBut = (Button) findViewById(R.id.myNotesButton);
        Button myClassesBut = (Button) findViewById(R.id.myClassesButton);
        Button allClassesBut = (Button) findViewById(R.id.allClassesButton);
        Button uploadBut = (Button) findViewById(R.id.uploadButton);
        uploadBut.setTypeface(myTypeface);
        myClassesBut.setTypeface(myTypeface);
        myNotesBut.setTypeface(myTypeface);
        allClassesBut.setTypeface(myTypeface);


    }

    public void openMyClasses (View view) {

        startActivity(new Intent(this, MyClasses.class));
    }

    public void openUploadPage (View view) {
        startActivity(new Intent(this, uploadPage.class));
    }

    public void openMyNotes (View view) {
        startActivity(new Intent(this, MyNotes.class));
    }

    public void openAllClasses (View view) {
        startActivity(new Intent(this, AllClasses.class));
    }
    //Maybe change to always update?
    public void setupData(){
        Firebase ref = new Firebase("https://note110.firebaseio.com/Departments/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // do some stuff once
                depart.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Department dept = new Department(postSnapshot.getKey());
                    depart.add(dept);
                    for (DataSnapshot innerSnapshot : postSnapshot.getChildren()) {
                        Course course = new Course(innerSnapshot.getKey(), dept.dataBaseRef + dept.name + "/");
                        course.parentDepartment = dept;
                        dept.courses.add(course);
                        for (DataSnapshot profSnapshot : innerSnapshot.getChildren()) {
                            Professor professor = new Professor(profSnapshot.getKey(), course.dataBaseRef + course.name + "/");
                            professor.parentCourse = course;
                            course.professors.add(professor);
                            int i = 0;
                            for (DataSnapshot lectureSnapshot : profSnapshot.getChildren()) {
                                i++;
                                Lecture lect = new Lecture(professor.dataBaseRef+professor.name+"/", i);
                                lect.parentProfessor = professor;
                                professor.lectures.add(lect);
                                int j = 0;
                                for(DataSnapshot noteSnapshot : lectureSnapshot.getChildren()){
                                    j++;
                                    Note note = new Note(j,lect.dataBaseRef+ lect.toString()+"/");
                                    note.parentLecture = lect;
                                    lect.notes.add(note);
                                    lect.numberOfNotes = j;
                                    for(DataSnapshot pictureSnapshot : noteSnapshot.getChildren()){
                                        System.out.println(pictureSnapshot.getKey().toString());
                                        if((pictureSnapshot.getKey().toString()).equals("Rating")){
                                            note.upvote = Integer.parseInt(pictureSnapshot.getValue().toString());
                                        }
                                        else if((pictureSnapshot.getKey().toString()).equals("Flags")){
                                            note.flag = Integer.parseInt(pictureSnapshot.getValue().toString());
                                        }
                                        else{
                                            String added = pictureSnapshot.getValue().toString();
                                            note.pictureString.add(added);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //    @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }


}
