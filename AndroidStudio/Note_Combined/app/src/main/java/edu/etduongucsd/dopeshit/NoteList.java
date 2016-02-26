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

public class NoteList extends AppCompatActivity {

    private static ListView noteListView;
    private static NoteListAdapter adapter;

    public static Note noteSelected;
    public static ArrayList<Note> noteList = new ArrayList<Note>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        /* Find the toolbar by id, and set it as the action bar. Whenever the 'Note' is clicked,
         * it will return to the home screen.
         *
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteList.this, HomeScreen.class));
            }
        });
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteList.this, SettingsPage.class));
            }
        });
        */

        Note note1 = new Note(1, 0);
        Note note2 = new Note(2, 0);
        Note note3 = new Note(3, 0);
        Note note4 = new Note(4, 0);

        addToNoteList(note1);
        addToNoteList(note2);
        addToNoteList(note3);
        addToNoteList(note4);

        displayNoteList();


    }

    public void addToNoteList(Note newNote) {
        if(noteList.contains(newNote)){}
        else {
            noteList.add(newNote);
            Collections.sort(noteList, Note.ASC_NOTES);
        }
    }

    public void delFromNoteList(Note delNote) {
        if(noteList.contains(delNote)) {
            noteList.remove(delNote);
            Collections.sort(noteList, Note.ASC_NOTES);
        }
    }

    public void displayNoteList() {
        noteListView = (ListView) findViewById(R.id.notesList);
        adapter = new NoteListAdapter(NoteList.this, 0, noteList);
        noteListView.setAdapter(adapter);
        noteListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        noteSelected = (Note) noteListView.getItemAtPosition(position);


                    }
                }
        );
    }

}
