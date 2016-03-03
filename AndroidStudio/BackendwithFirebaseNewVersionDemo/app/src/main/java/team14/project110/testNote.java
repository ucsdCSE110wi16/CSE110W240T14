package team14.project110;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class testNote extends AppCompatActivity {

    public static List<Note> noteList;

    public static int selectedNote = -1;          //Index of department that is clicked on

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_note);

        Lecture current = testLecture.lectureList.get(testLecture.selectedLecture);
        noteList = current.notes;

        createCourseList();

        registerClickCallback();
    }
    //Create the list of courses that is displayed on the screen
    void createCourseList(){

        ArrayAdapter noteArrayAdapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, noteList);
        System.out.println(R.id.listView5);
        ListView noteListview = (ListView) this.findViewById(R.id.listView5);
        noteListview.setAdapter(noteArrayAdapter);

    }

    private void registerClickCallback(){

        //List shown on screen
        ListView list = (ListView) findViewById(R.id.listView5);

        //Listener for the list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //What will happen when the list is clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                selectedNote = position;  //Deparment selected from the list of departments

                //Create new intent to open course list contained by selected department
                Intent selectedIntent = new Intent(testNote.this, MainActivity.class);
                startActivity(selectedIntent);

            }
        });
    }
}
