package edu.etduongucsd.dopeshit;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

//My classes page
public class MyClasses extends AppCompatActivity {


    private static ListView list_view;
    private static CourseListAdapter1 listAdapter;

    public static Professor courseSel;
    public static List<Professor> MYCLASSES = new ArrayList<Professor>();

    public static String lectureHeading;

    Button button;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public void addToMyClasses(Professor newMyClass) {
        if (MYCLASSES.contains(newMyClass)) {
        } else {
            MYCLASSES.add(newMyClass);
        }
    }

    public void delFromMyClasses(Course remMyClass) {
        MYCLASSES.remove(remMyClass);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes);

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
                Intent intent = new Intent(MyClasses.this, HomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyClasses.this, SettingsPage.class));
            }
        });


        if (HomeScreen.userProfile.myCourses != null) {
            MYCLASSES = HomeScreen.userProfile.myCourses;
        }
        createCourseList();

        TextView title = (TextView) findViewById(R.id.myClassesTitle);
        Typeface myType = Typeface.createFromAsset(getAssets(), "Lob.otf");
        title.setTypeface(myType);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //Create the list of courses that is displayed on the screen
    void createCourseList() {

        list_view = (ListView) findViewById(R.id.myClassesList);
        listAdapter = new CourseListAdapter1(MyClasses.this, 0, MYCLASSES);
        list_view.setAdapter(listAdapter);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                courseSel = (Professor) list_view.getItemAtPosition(position);
                lectureHeading = courseSel.parentCourse.getName();

                HomeScreen.selectedDepart = MYCLASSES.get(position).parentCourse.parentDepartment;
                HomeScreen.selectedCourse = MYCLASSES.get(position).parentCourse;
                HomeScreen.selectedProfessor = MYCLASSES.get(position);

                Intent selectedIntent = new Intent(MyClasses.this, testNote.class);
                startActivity(selectedIntent);
            }
        });
    }

    private void registerClickCallback() {

        //List shown on screen
        ListView list = (ListView) findViewById(R.id.lectureListView);

        //Listener for the list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //What will happen when the list is clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                HomeScreen.selectedDepart = MYCLASSES.get(position).parentCourse.parentDepartment;
                HomeScreen.selectedCourse = MYCLASSES.get(position).parentCourse;
                HomeScreen.selectedProfessor = MYCLASSES.get(position);
                //Create new intent to open course list contained by selected department
                Intent selectedIntent = new Intent(MyClasses.this, testNote.class);
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MyClasses Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://edu.etduongucsd.dopeshit/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MyClasses Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://edu.etduongucsd.dopeshit/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
