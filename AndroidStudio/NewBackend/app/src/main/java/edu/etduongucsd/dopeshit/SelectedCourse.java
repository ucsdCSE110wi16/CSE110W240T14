package edu.etduongucsd.dopeshit;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//Professor's page
public class SelectedCourse extends AppCompatActivity{

    Button button2;

    private ListView profListView;
    private ProfListAdapter profAdapter;

    public static List<Professor> profList;
    public static Professor profSel;
    private String newProfName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_course);

        button2 = (Button) findViewById(R.id.addNewProfBut);

        /* Find the toolbar by id, and set it as the action bar. Whenever the 'Note' is clicked,
         * it will return to the home screen.
         *
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectedCourse.this, HomeScreen.class));
            }
        });
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectedCourse.this, SettingsPage.class));
            }
        });*/

        TextView className = (TextView) findViewById(R.id.profListTitle);
        className.setText(HomeScreen.selectedDepart.getName()+" "+HomeScreen.selectedCourse.getName().trim());
        profList = HomeScreen.selectedCourse.getProfessors();

        createCourseList();

        //registerClickCallback();

        button2OnClick();

    }


    public void button2OnClick(){
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SelectedCourse.this);
                builder.setTitle("Add New Professor For " + HomeScreen.selectedDepart.getName() + " " + HomeScreen.selectedCourse.getName());
                builder.setMessage("What is the new professor's name?");

                // Set up the input
                final EditText input = new EditText(SelectedCourse.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newProfName = input.getText().toString();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();

                if(newProfName != "") {
                    HomeScreen.selectedCourse.addProfessor(newProfName);
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
                }

            }
        });
    }

    //Create the list of courses that is displayed on the screen
    private void createCourseList(){

        profListView = (ListView) findViewById(R.id.profListView);
        profAdapter = new ProfListAdapter(SelectedCourse.this, 0, profList);
        profListView.setAdapter(profAdapter);
        profListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                profSel = (Professor) profListView.getItemAtPosition(position);
                HomeScreen.selectedProfessor = profSel;

                //HomeScreen.selectedProfessor = HomeScreen.selectedCourse.professors.get(position);  //Deparment selected from the list of departments

                //Create new intent to open course list contained by selected department
                Intent selectedIntent = new Intent(SelectedCourse.this, testNote.class);
                startActivity(selectedIntent);
            }
        });

        /*ArrayAdapter professorArrayAdapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, HomeScreen.selectedCourse.professors);
        //ListView professorListview = (ListView) this.findViewById(R.id.listView5);
        //professorListview.setAdapter(professorArrayAdapter);*/

    }

    /*
    private void registerClickCallback(){

        //List shown on screen
        //ListView list = (ListView) findViewById(R.id.listView5);

        //Listener for the list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //What will happen when the list is clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                HomeScreen.selectedProfessor = HomeScreen.selectedCourse.professors.get(position);  //Deparment selected from the list of departments

                //Create new intent to open course list contained by selected department
                Intent selectedIntent = new Intent(SelectedCourse.this, testNote.class);
                startActivity(selectedIntent);

            }
        });
    }*/
}
