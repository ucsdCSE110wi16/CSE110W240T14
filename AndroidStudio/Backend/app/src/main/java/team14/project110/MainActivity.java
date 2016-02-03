package team14.project110;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        wordScanner = new WordScanner(this);    //Construct wordScanner object
        wordScanner.parseList(path);            //Go through words in lines and sort according to dept and course

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
