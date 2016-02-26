package edu.etduongucsd.dopeshit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class WeekLectureSelection extends AppCompatActivity {

    private static ListView exp_list_view;
    private static String[] WeekList = new String[] {"Week 1",
            "Week 2", "Week 3", "Week 4", "Week 5", "Week 6",
            "Week 7", "Week 8", "Week 9", "Week 10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_lecture_selection);

        /* Find the toolbar by id, and set it as the action bar. Whenever the 'Note' is clicked,
         * it will return to the home screen.
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeekLectureSelection.this, HomeScreen.class));
            }
        });
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeekLectureSelection.this, SettingsPage.class));
            }
        });

    }

    public void ListWeeks () {
        exp_list_view = (ListView)findViewById(R.id.expWeekList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.weeks_list, WeekList);
        exp_list_view.setAdapter(adapter);
        exp_list_view.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String value = (String) exp_list_view.getItemAtPosition(position);
                        Toast.makeText(WeekLectureSelection.this, "Class: " + value, Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
}
