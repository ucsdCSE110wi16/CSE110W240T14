package edu.etduongucsd.dopeshit;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

//Settings drop down menu
public class SettingsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        /* Find the toolbar by id, and set it as the action bar. Whenever the 'Note' is clicked,
         * it will return to the home screen.
         */

        // TODO - I don't know if this should be included for the settings page;
        // TODO - uncomment if you want the toolbar with Note on top for this page
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsPage.this, HomeScreen.class));
            }
        });

        */

        TextView title = (TextView) findViewById(R.id.textView);
        Typeface myType = Typeface.createFromAsset(getAssets(), "AD.ttf");
        title.setTypeface(myType);


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

}
