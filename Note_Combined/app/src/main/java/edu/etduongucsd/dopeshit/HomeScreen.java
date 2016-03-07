package edu.etduongucsd.dopeshit;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    public static List<Department> depart = new ArrayList<Department>();              //List of departments

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Firebase.setAndroidContext(this);



        Firebase ref = new Firebase("https://note110.firebaseio.com/Departments/");
        //  addListenerForSingleValueEvent
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // do some stuff once
                depart.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Department dept = new Department(postSnapshot.getKey());

                    //   postSnapshot.getValue(Department.class);
                    //new Department(postSnapshot.toString());
                    depart.add(dept);
                    for (DataSnapshot innerSnapshot : postSnapshot.getChildren()) {
                        Course course = new Course(innerSnapshot.getKey(), dept.dataBaseRef + dept.name + "/");
                        dept.courses.add(course);
                        for (DataSnapshot profSnapshot : innerSnapshot.getChildren()) {
                            Professor professor = new Professor(profSnapshot.getKey(), course.dataBaseRef + course.name + "/");
                            course.professors.add(professor);
                            for (DataSnapshot lectureSnapshot : profSnapshot.getChildren()) {
                                Lecture lecture = new Lecture(professor.dataBaseRef + professor.name + "/", professor.numberOfLectures);
                                professor.lectures.add(lecture);
                            }
                        }
                    }
                }
            }

            //    @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

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
     //   startActivity(new Intent(this, MyNotes.class));
    }

    public void openAllClasses (View view) {
        startActivity(new Intent(this, AllClasses.class));
    }


}
