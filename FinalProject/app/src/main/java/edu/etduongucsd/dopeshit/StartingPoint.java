package edu.etduongucsd.dopeshit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StartingPoint extends AppCompatActivity {

    public static final int PREFERENCE_MODE_PRIVATE = 0;
    public static final String MY_UNIQUE_PREFERENCE_FILE = "UserPreferenceFile";

    public static SharedPreferences preferenceSettings;
    public static SharedPreferences.Editor preferenceEditor;

    Set<String> mySet;


    public static UserProfile myProfile;

    public static List<Department> depart;

    Button loginButton;

    EditText emailText;

    String emailInput;

    String checkEmail;

    String checkName;

    public static Set<String> users;

    public static Set<String> myCourses;

    public static Set<String> myNotes;

    public static Set<String> likedNotes;

    public static Set<String> flaggedNotes;

    public static String lastUser;

    Login login;

    Boolean resetSavedData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        depart = new ArrayList<Department>();              //List of departments

        preferenceSettings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferenceEditor = preferenceSettings.edit();
        if(resetSavedData) {
            preferenceEditor.clear();
            preferenceEditor.commit();
        }
        users = preferenceSettings.getStringSet("Users", null);

        lastUser = preferenceSettings.getString("lastUser", null);

        if(users == null){
            users = new HashSet<String>();
        }

        setupData();



        Firebase.setAndroidContext(this);

        login = new Login();



        loginButton = (Button) findViewById(R.id.loginButton);

        emailText = (EditText)findViewById(R.id.email);

        if(lastUser != null){
            emailText.setText(lastUser, TextView.BufferType.EDITABLE);
        }

        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        if (login.checkName(emailText.getText().toString()) == true) {
                            myCourses = preferenceSettings.getStringSet((myProfile.name + "myCourses"), null);

                            if(myCourses == null){
                                myCourses = new HashSet<String>();
                            }
                            setupMyCourses(myCourses);
                            System.out.println("Number of myCourses: " + myCourses.size());
                            System.out.println("Number of myCourses: " + myProfile.myCourses.size());
                            myNotes = preferenceSettings.getStringSet((myProfile.name + "myNotes"), null);
                            if(myNotes == null){
                                myNotes = new HashSet<String>();
                            }
                            setupMyNotes(myNotes);
                            System.out.println("Number of myNotes: " + myNotes.size());
                            System.out.println("Number of myNotes: " + myProfile.userUpNotes.size());
                            likedNotes = preferenceSettings.getStringSet((myProfile.name + "likedNotes"), null);
                            if(likedNotes == null){
                                likedNotes = new HashSet<String>();
                            }
                            setupLikedNotes(likedNotes);
                            System.out.println("Number of likedNotes: " + likedNotes.size());
                            System.out.println("Number of likedNotes: " + myProfile.myUpvotes.size());
                            flaggedNotes = preferenceSettings.getStringSet((myProfile.name + "flaggedNotes"), null);
                            if(flaggedNotes == null){
                                flaggedNotes = new HashSet<String>();
                            }
                            setupFlaggedNotes(flaggedNotes);
                            System.out.println("Number of flaggedNotes: " + flaggedNotes.size());
                            System.out.println("Number of flaggedNotes: " + myProfile.myFlags.size());
                            startActivity(new Intent(StartingPoint.this, HomeScreen.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter valid UCSD email", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void setupMyCourses(Set<String> courses){
        if(courses != null){
            for(String c : courses){
                for(Department department : depart){
                    for(Course course : department.courses){
                        for(Professor professor : course.professors) {
                            if (c.equals(professor.dataBaseRef + professor.name + "/")) {
                                boolean found = false;
                                for(Professor checkProf : myProfile.myCourses){
                                    if(checkProf.name.equals(professor.name)){
                                        found = true;
                                    }
                                    if(found == false){
                                        myProfile.myCourses.add(professor);
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    private void setupMyNotes(Set<String> notes){
        if(notes != null){
            for(String c : notes){
                for(Department department : depart){
                    for(Course course : department.courses){
                        for(Professor professor : course.professors){
                            for(Lecture lecture : professor.lectures){
                                for(Note note : lecture.notes){
                                    if(c.equals(note.dataBaseRef+"Note " + note.noteNum+"/")){
                                        myProfile.userUpNotes.add(note);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void setupLikedNotes(Set<String> notes){
        if(notes != null){
            for(String c : notes){
                for(Department department : depart){
                    for(Course course : department.courses){
                        for(Professor professor : course.professors){
                            for(Lecture lecture : professor.lectures){
                                for(Note note : lecture.notes){
                                    if(c.equals(note.dataBaseRef+"Note " + note.noteNum+"/")){
                                        myProfile.myUpvotes.add(note);
                                        note.upvoteBool = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void setupFlaggedNotes(Set<String> notes){
        if(notes != null){
            for(String c : notes){
                for(Department department : depart){
                    for(Course course : department.courses){
                        for(Professor professor : course.professors){
                            for(Lecture lecture : professor.lectures){
                                for(Note note : lecture.notes){
                                    if(c.equals(note.dataBaseRef+"Note " + note.noteNum+"/")){
                                        myProfile.myFlags.add(note);
                                        note.flagBool = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

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

                                Lecture lect = new Lecture(professor.dataBaseRef + professor.name + "/", i);
                                lect.parentProfessor = professor;
                                professor.lectures.add(lect);
                                int j = 0;
                                for (DataSnapshot noteSnapshot : lectureSnapshot.getChildren()) {
                                    //Retrieve date for lectObject
                                    String date = null;
                                    if (noteSnapshot.getKey().toString().equals("Date")) {
                                        date = noteSnapshot.getValue().toString();
                                        lect.date = date;
                                    } else {
                                        j++;
                                        Note note = new Note(j, lect.dataBaseRef + lect.toString() + "/");
                                        note.parentLecture = lect;
                                        lect.notes.add(note);
                                        lect.numberOfNotes = j;
                                        for (DataSnapshot pictureSnapshot : noteSnapshot.getChildren()) {
                                            System.out.println(pictureSnapshot.getKey().toString());
                                            if ((pictureSnapshot.getKey().toString()).equals("Rating")) {
                                                note.upvote = Integer.parseInt(pictureSnapshot.getValue().toString());
                                            } else if ((pictureSnapshot.getKey().toString()).equals("Flags")) {
                                                note.flag = Integer.parseInt(pictureSnapshot.getValue().toString());
                                            } else {
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
            }

            //    @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
}
