package edu.etduongucsd.dopeshit;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

        Typeface myType2 = Typeface.createFromAsset(getAssets(), "Lob.otf");
        title.setTypeface(myType2);

        Button upSetBut = (Button) findViewById(R.id.button);
        upSetBut.setTypeface(myType2);

        Button myNSetBut = (Button) findViewById(R.id.button2);
        myNSetBut.setTypeface(myType2);

        Button myCSetBut = (Button) findViewById(R.id.button3);
        myCSetBut.setTypeface(myType2);

        Button allCSetBut = (Button) findViewById(R.id.button4);
        allCSetBut.setTypeface(myType2);

        Button resetBut = (Button) findViewById(R.id.resetAccBut);
        resetBut.setTypeface(myType2);
        resetBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Resetting Account");
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsPage.this);
                builder.setTitle("Reset Account");
                builder.setMessage("WARNING: Doing this will delete all your account information, including your list of classes and uploaded notes. Do you still wish to reset your account?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO REMOVE CLASS FUNC CALL
                        StartingPoint.preferenceSettings.edit().remove(StartingPoint.myProfile.name + "myCourses").commit();
                        StartingPoint.preferenceSettings.edit().remove(StartingPoint.myProfile.name + "myNotes").commit();
                        StartingPoint.preferenceSettings.edit().remove(StartingPoint.myProfile.name + "likedNotes").commit();
                        StartingPoint.preferenceSettings.edit().remove(StartingPoint.myProfile.name + "flaggedNotes").commit();

                        HomeScreen.userProfile.myCourses = new ArrayList<Professor>();
                        HomeScreen.userProfile.myFlags = new ArrayList<>();
                        HomeScreen.userProfile.myUpvotes = new ArrayList<>();
                        HomeScreen.userProfile.userUpNotes = new ArrayList<Note>();

                        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void
                    onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });


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
