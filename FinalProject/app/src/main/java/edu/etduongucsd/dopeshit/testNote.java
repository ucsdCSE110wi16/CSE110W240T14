package edu.etduongucsd.dopeshit;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

//LectureList page
public class testNote extends AppCompatActivity {

    public static Professor currentProfessor;

    private static LectureListAdapter lecAdapter;
    public ListView lecListView;
    public static List<Lecture> lecList;

    public static Lecture lecSel;
    public static String lecName;

    Button button;
    Button button2;

    public static int daySel;
    public static int monthSel;
    public static int yearSel;

    static final int Dialog_ID = 0;

    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_note);

        button = (Button) findViewById(R.id.addMyClassBut);
        button2 = (Button) findViewById(R.id.newLecBut);

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
                Intent intent = new Intent(testNote.this, HomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(testNote.this, SettingsPage.class));
            }
        });

        Typeface myType = Typeface.createFromAsset(getAssets(), "Lob.otf");

        currentProfessor = HomeScreen.selectedProfessor;
        TextView lecTitle = (TextView) findViewById(R.id.lectureTitle);
        lecTitle.setText(currentProfessor.getName());
        lecTitle.setTypeface(myType);

        lecList  = HomeScreen.selectedProfessor.lectures;
        createLectureList();

        addToMyClassesOnClick();

        daySel = calendar.get(Calendar.DAY_OF_MONTH);
        monthSel = calendar.get(Calendar.MONTH);
        yearSel = calendar.get(Calendar.YEAR);

        newLecOnClick();

    }


    public void addToMyClassesOnClick(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Professor newProf = HomeScreen.selectedProfessor;
                if(HomeScreen.userProfile.myCourses.contains(newProf)) {
                    Toast.makeText(testNote.this, HomeScreen.selectedDepart.getName() + " " + HomeScreen.selectedCourse.getName() + " is already in your list of classes!", Toast.LENGTH_LONG).show();
                }
                else {
                    HomeScreen.userProfile.myCourses.add(newProf);
                    StartingPoint.myCourses.add(newProf.dataBaseRef + newProf.name + "/");
                    StartingPoint.preferenceEditor.remove((StartingPoint.myProfile.name + "myCourses"));
                    StartingPoint.preferenceEditor.commit();
                    StartingPoint.preferenceEditor.putStringSet((StartingPoint.myProfile.name + "myCourses"), StartingPoint.myCourses);
                    StartingPoint.preferenceEditor.commit();
                    Toast.makeText(testNote.this, HomeScreen.selectedDepart.getName() + " " + HomeScreen.selectedCourse.getName() + " has been added to your classes!", Toast.LENGTH_LONG).show();
                }

                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });
    }

    //Add a Lecture
    public void newLecOnClick(){
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Dialog_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == Dialog_ID){
            return new DatePickerDialog(this, datePickListener, yearSel, monthSel, daySel);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            yearSel = year;
            monthSel = monthOfYear + 1;
            daySel = dayOfMonth;
            boolean lectureExists = false;


            System.out.println("Number of lectures in prof: " + HomeScreen.selectedProfessor.lectures.size());
            for(Lecture lecture : HomeScreen.selectedProfessor.lectures){
                if(lecture.date.equals((monthSel + "/" + daySel))){
                    Toast.makeText(testNote.this, monthSel + "/" + daySel + " Lecture is already available!", Toast.LENGTH_LONG).show();
                    lectureExists = true;
                }
            }
            if(lectureExists == false) {
                Toast.makeText(testNote.this, monthSel + "/" + daySel + " Lecture has been added!", Toast.LENGTH_LONG).show();
                HomeScreen.selectedProfessor.addLecture(monthSel, daySel, HomeScreen.selectedProfessor.lectures.size() + 1);

                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        }
    };

        //Create the list of courses that is displayed on the screen
    void createLectureList(){

        lecListView = (ListView)findViewById(R.id.lectureListView);
        lecAdapter = new LectureListAdapter(testNote.this, 0, lecList);
        lecListView.setAdapter(lecAdapter);
        lecListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HomeScreen.selectedLecture = HomeScreen.selectedProfessor.lectures.get(position);
                lecSel = (Lecture) lecListView.getItemAtPosition(position);
                lecName = lecSel.toString();
                Intent selectedIntent = new Intent(testNote.this, MainActivity.class);
                startActivity(selectedIntent);

            }
        });
    }
}
