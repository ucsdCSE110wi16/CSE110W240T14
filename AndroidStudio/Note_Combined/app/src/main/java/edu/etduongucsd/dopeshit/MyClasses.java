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

public class MyClasses extends AppCompatActivity {

    private static ListView list_view;

    private static List<String> MYCLASSES = new ArrayList<String>();

    public static String lectureHeading;
    public static String classProf;

    //TextView lecHeading = (TextView) findViewById(R.id.lectureTitle);


    //private static String[] MYCLASSES = new String[] {"CSE 100",
    //                                                  "CSE 101", "CSE 105", "CSE 110", "CSE 140", "GARY"};

    public void addToMyClasses (String className) {
        MYCLASSES.add(className);
        Collections.sort(MYCLASSES, String.CASE_INSENSITIVE_ORDER);
    }

    public void delFromMyClasses (String className) {
        MYCLASSES.remove(className);
        Collections.sort(MYCLASSES, String.CASE_INSENSITIVE_ORDER);
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

        //android.support.v7.app.ActionBar ab = getSupportActionBar();
        //ab.setLogo(R.drawable.lettern);
        //ab.setDisplayUseLogoEnabled(true);
        //ab.setDisplayShowHomeEnabled(true);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Sweet Pea_2.ttf");
        TextView myClassesTitle = (TextView) findViewById(R.id.myClassesTitle);
        myClassesTitle.setTypeface(myTypeface);

<<<<<<< HEAD
        ClassInfo example = new ClassInfo("CSE 110", "Kesden");
        //ClassInfo example3 = new ClassInfo("CSE 113", "Kesden");
        //ClassInfo example4 = new ClassInfo("CSE 114", "Kesden");
        ClassInfo example2 = new ClassInfo("CSE 105", "Shacham");
        ClassInfo example5 = new ClassInfo("CSE 100", "Diba");
        ClassInfo example1 = new ClassInfo("CSE 131", "Ord");

        addToMyClasses(example);
        addToMyClasses(example1);
        addToMyClasses(example2);
        //addToMyClasses(example3);
        //addToMyClasses(example4);
        addToMyClasses(example5);
        delFromMyClasses(example5);
=======
        addToMyClasses("CSE 100");
        addToMyClasses("CSE 105");
        addToMyClasses("CSE 101");
        addToMyClasses("CSE 110");
        addToMyClasses("CSE 130");
        addToMyClasses("CSE GARY");
        delFromMyClasses("CSE GARY");
>>>>>>> 5ef9270ab7c17fdbce5e72fe0807110eadcbd11f

        myClassesView();
    }

    public void myClassesView () {
        list_view = (ListView)findViewById(R.id.myClassesList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.myclasses_list, MYCLASSES);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
<<<<<<< HEAD
                        //TextView lecHeading = (TextView) findViewById(R.id.lectureTitle);
                        classSel = (ClassInfo) list_view.getItemAtPosition(position);
                        lectureHeading = classSel.getClassName();
                        classProf = classSel.getProfessor();
                        //lecHeading.setText(classSelected);
                        LectureList.lecList = classSel.classLectures; //Set LectureList's array of Lecs to selected class' lecList
                        int numLecs = classSel.getNumLec();
                        //Toast.makeText(MyClasses.this, "Class: " + classSelected + " has " + numLecs + " lectures.", Toast.LENGTH_LONG).show();
                        openLectureList(view);
                        //openLectureList();
=======
                        String value = (String) list_view.getItemAtPosition(position) ;
                        Toast.makeText(MyClasses.this, "Class: " + value, Toast.LENGTH_LONG).show();

>>>>>>> 5ef9270ab7c17fdbce5e72fe0807110eadcbd11f
                    }
                }
        );
    }

<<<<<<< HEAD
    public void openLectureList(View view) {
        Intent moveToLecList = new Intent(this, LectureList.class);
        startActivity(moveToLecList);
    }

=======
>>>>>>> 5ef9270ab7c17fdbce5e72fe0807110eadcbd11f
    public void openClassNotes(View view) {
        startActivity(new Intent(this, LectureList.class));
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
