package edu.etduongucsd.dopeshit;

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

public class MyNotes extends AppCompatActivity {

    private static ListView list_view;
    private static NoteListAdapter adapter;

    public static Note noteSel;
    public static ArrayList<Note> myNotes = new ArrayList<Note>();

    public void addToMyNotes(Note newNote) {
        if(myNotes.contains(newNote)) {}
        else {
            myNotes.add(newNote);
            Collections.sort(myNotes, Note.ASC_NOTES);
        }
    }

    public void delFromMyNotes(Note delNote) {
        if(myNotes.contains(delNote)) {
            myNotes.remove(delNote);
            Collections.sort(myNotes, Note.ASC_NOTES);
        }
    }

    public void displayNoteList() {
        list_view = (ListView) findViewById(R.id.notesList);
        adapter = new NoteListAdapter(MyNotes.this, 0, myNotes);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        noteSel = (Note) list_view.getItemAtPosition(position);


                    }
                }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

    }

}
