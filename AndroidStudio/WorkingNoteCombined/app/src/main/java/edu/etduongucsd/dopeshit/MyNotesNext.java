package edu.etduongucsd.dopeshit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ERIC on 3/6/2016.
 */
public class MyNotesNext extends AppCompatActivity {

    ArrayList<Bitmap> picture;
    ArrayList<ArrayList<Bitmap>> arrayOfPicture = new ArrayList<ArrayList<Bitmap>>();

    List<Note> currNoteList;
    Note current = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* Find the toolbar by id, and set it as the action bar. Whenever the 'Note' is clicked,
         * it will return to the home screen.
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyNotesNext.this, HomeScreen.class));
            }
        });
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyNotesNext.this, SettingsPage.class));
            }
        });


        currNoteList = HomeScreen.userProfile.userUpNotes;
        //Collections.sort(currNoteList, Note.ASC_NOTES);

        picture = new ArrayList<Bitmap>();

        for (int i = 0; i < currNoteList.size(); i++) {

            current = currNoteList.get(i);
            picture = current.convertToNoteBitmap(100, 100);
            arrayOfPicture.add(picture);
        }

        TextView noteHead = (TextView) findViewById(R.id.noteListTitle);
        TextView noteClass = (TextView) findViewById(R.id.classNote);
        TextView noteProf = (TextView) findViewById(R.id.noteProf);

        noteHead.setText(HomeScreen.selectedLecture.toString().trim());
        noteClass.setText(HomeScreen.selectedDepart.getName() + " " + HomeScreen.selectedCourse.getName().trim());
        noteProf.setText(HomeScreen.selectedProfessor.getName().trim());

        ArrayAdapter<Note> adapter = new MyNoteListAdapter();
        ListView listView = (ListView) findViewById(R.id.notesList);
        listView.setAdapter(adapter);
    }

    private class MyNoteListAdapter extends ArrayAdapter<Note>{
        public MyNoteListAdapter(){
            super(MyNotesNext.this, R.layout.mynote_display_layout, HomeScreen.userProfile.userUpNotes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View noteView = convertView;
            if(noteView == null){
                noteView = getLayoutInflater().inflate(R.layout.mynote_display_layout, parent, false);
            }

            Note currentNote = HomeScreen.userProfile.userUpNotes.get(position);

            ImageView imageView = (ImageView)noteView.findViewById(R.id.myImageButton);
            imageView.setImageBitmap(arrayOfPicture.get(position).get(0));

            TextView textView = (TextView)noteView.findViewById(R.id.myNoteName);
            textView.setText(currentNote.toString());

            TextView numUpVotes = (TextView) findViewById(R.id.myNumVotes);
            //numUpVotes.setText(currentNote.getUpvote());

            return noteView;
        }
    }

}
