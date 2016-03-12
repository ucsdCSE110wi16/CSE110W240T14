package edu.etduongucsd.dopeshit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashSet;

/**
 * Created on 3/11/2016.
 */


public class Data {
    boolean canProceed;
    Activity currentActivity;
    ProgressBar progressBar;
    ProgressBarAnimation pba;
    Intent nextActivity;
    public void setupData( Activity currContext, Intent nextIntent){
       canProceed = false;
        //context = currContext;
        nextActivity = nextIntent;
        currentActivity= currContext;
       // currentActivity.setContentView(R.layout.activity_loading);
       // progressBar = (ProgressBar) currentActivity.findViewById(R.id.progressBar);
      //  progressBar.setMax(100);
      //  pba = new ProgressBarAnimation(progressBar, 1000);
        Firebase ref = new Firebase("https://note110.firebaseio.com/Departments/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // do some stuff once
                HomeScreen.depart.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Department dept = new Department(postSnapshot.getKey());
                    HomeScreen.depart.add(dept);
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
                                        boolean removed = false;
                                        for (DataSnapshot pictureSnapshot : noteSnapshot.getChildren()) {
                                            if ((pictureSnapshot.getKey().toString()).equals("Removed")) {
                                                removed = Boolean.parseBoolean(pictureSnapshot.getValue().toString());
                                            }
                                        }
                                        if (removed == false) {
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
                                                } else if(!((pictureSnapshot.getKey().toString()).equals("Removed"))){
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
//                    pba.setProgress(100);
                }
                canProceed = true;
                if (canProceed) {
                    System.out.println("REFRESHED");
                    currentActivity.startActivity(nextActivity);
                    //currentActivity.setContentView(currentLayout);
                    canProceed = false;
                }
            }

            //    @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });


        // setContentView(R.layout.activity_login);
    }
}
