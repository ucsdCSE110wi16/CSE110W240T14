package team14.project110;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String path = "testText.txt";   //Text file name

    private WordScanner wordScanner;                    //Object that will read text file

    public static int selectedDepartment = -1;          //Index of department that is clicked on

    public static List<Department> depart;              //List of departments

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        Firebase ref = new Firebase("https://note110.firebaseio.com/");

     //   ref.child("Department").child("Course").child("Professor").child("Day").child("Note").setValue(0);
      //  ref.child("Department").child("Course").child("Professor").child("Day").child("Note2").setValue(0);
        //Log.d("myTag", ref.getChildrenCount());
        wordScanner = new WordScanner(this);    //Construct wordScanner object
        wordScanner.parseList(path);            //Go through words in lines and sort according to dept and course

        DatabaseSetup databaseSetup = new DatabaseSetup(wordScanner.departments);

        Professor thisProf = new Professor("Test", 3, (wordScanner.departments.get(0)).courses.get(0));
        thisProf.addProf((wordScanner.departments.get(0)).courses.get(0));
        Note testNote = new Note("pictureURL", thisProf);
        testNote.addNote(0,0);
        createList();                           //Create ui list of departments shown on phone

        registerClickCallback();                //Respond to click on list of departments
    }
    //This will list departments on the screen
    void createList(){
        depart = wordScanner.departments;   //List of departments
        //Adapter that controls what is shown by UI list
        ArrayAdapter departmentArrayAdapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, depart);

        //Find and assign UI list object
        ListView deptList = (ListView) findViewById(R.id.listView);
        //Set adapter to this UI list
        deptList.setAdapter(departmentArrayAdapter);
    }
    //This will do something when a department on the list is clicked on
    private void registerClickCallback(){

        //List shown on screen
        ListView list = (ListView) findViewById(R.id.listView);

        //Listener for the list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //What will happen when the list is clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                selectedDepartment = position;  //Deparment selected from the list of departments

                //Create new intent to open course list contained by selected department
                Intent selectedIntent = new Intent(MainActivity.this, Course_Activity.class);
                startActivity(selectedIntent);

            }
        });
    }
}
