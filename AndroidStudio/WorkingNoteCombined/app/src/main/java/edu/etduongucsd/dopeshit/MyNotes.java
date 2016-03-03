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
//MyNotes screen
public class MyNotes extends AppCompatActivity {

    private static ListView myNoteView;
    private static MyNoteListAdapter myNoteAdapter;

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

    public void displayMyNoteList() {
        myNoteView = (ListView) findViewById(R.id.myNotesList);
        myNoteAdapter = new MyNoteListAdapter(MyNotes.this, 0, myNotes);
        myNoteView.setAdapter(myNoteAdapter);
        myNoteView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        noteSel = (Note) myNoteView.getItemAtPosition(position);
                    }
                }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        displayMyNoteList();

    }

}
