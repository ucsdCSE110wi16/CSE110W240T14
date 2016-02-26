package edu.etduongucsd.dopeshit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class ProfList extends AppCompatActivity {

    private ListView profListView;
    private ProfListAdapter profAdapter;

    public static ArrayList<Professor> profList = new ArrayList<Professor>();

    public static Professor profSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_list);
        /* Find the toolbar by id, and set it as the action bar. Whenever the 'Note' is clicked,
         * it will return to the home screen.
         *
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfList.this, SettingsPage.class));
            }
        });
        */

        addProfessor(new Professor("Kesden"));
        addProfessor(new Professor("Gary"));
        addProfessor(new Professor("Ord"));

        displayProfList();
    }

    public void addProfessor(Professor newProf) {
        if(profList.contains(newProf)) {}
        else {
            profList.add(newProf);
            Collections.sort(profList, Professor.ASC_PROF);
        }
    }

    public void delProfessor(Professor delProf) {
        if(profList.contains(delProf)) {
            profList.remove(delProf);
            Collections.sort(profList, Professor.ASC_PROF);
        }
    }

    public void displayProfList() {
        profListView = (ListView) findViewById(R.id.profListView);
        profAdapter = new ProfListAdapter(ProfList.this, 0, profList);
        profListView.setAdapter(profAdapter);
        profListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                profSelect = (Professor) profListView.getItemAtPosition(position);
            }
        });
    }
}
