package edu.etduongucsd.dopeshit;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//My classes page
public class MyClasses extends AppCompatActivity {

    public static List<Course> MYCLASSES = new ArrayList<Course>();

    public static String lectureHeading;
    public static String classProf;

    Button button;

    public void addToMyClasses (Course newMyClass) {
        if(MYCLASSES.contains(newMyClass)){}
        else {
            MYCLASSES.add(newMyClass);
        }
    }

    public void delFromMyClasses (Course remMyClass) {
        MYCLASSES.remove(remMyClass);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_note);

        button = (Button) findViewById(R.id.addButton);
        button.setVisibility(View.INVISIBLE);


        /* Find the toolbar by id, and set it as the action bar. Whenever the 'Note' is clicked,
         * it will return to the home screen.
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyClasses.this, HomeScreen.class));
            }
        });
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyClasses.this, SettingsPage.class));
            }
        });
        if(HomeScreen.userProfile.myCourses != null) {
            MYCLASSES = HomeScreen.userProfile.myCourses;
        }
        createCourseList();

        registerClickCallback();
    }

    //Create the list of courses that is displayed on the screen
    void createCourseList(){

        ArrayAdapter courseArrayAdapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, MYCLASSES);
        ListView courseListview = (ListView) this.findViewById(R.id.listView5);
        courseListview.setAdapter(courseArrayAdapter);

    }

    private void registerClickCallback(){

        //List shown on screen
        ListView list = (ListView) findViewById(R.id.listView5);

        //Listener for the list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //What will happen when the list is clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                HomeScreen.selectedDepart = MYCLASSES.get(position).parentDepartment;
                HomeScreen.selectedCourse = MYCLASSES.get(position);
                //Create new intent to open course list contained by selected department
                Intent selectedIntent = new Intent(MyClasses.this, SelectedCourse.class);
                startActivity(selectedIntent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
