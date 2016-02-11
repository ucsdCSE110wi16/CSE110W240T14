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

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
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

    /*public void openMyNotes (View view) {
        startActivity(new Intent(this, MyNotes.class));
    }

    public void openAllClasses (View view) {
        startActivity(new Intent(this, AllClasses.class));
    }*/


}
