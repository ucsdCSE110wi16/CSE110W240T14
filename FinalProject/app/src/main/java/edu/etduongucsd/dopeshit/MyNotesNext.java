package edu.etduongucsd.dopeshit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ERIC on 3/6/2016.
 */
public class MyNotesNext extends AppCompatActivity {

    ArrayList<Bitmap> picture;
    ArrayList<ArrayList<Bitmap>> arrayOfPicture = new ArrayList<ArrayList<Bitmap>>();

    int index;
    List<Note> currNoteList;
    Note current = null;
    ImageButton notePic;
    int FULL_VIEW = 212;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* Find the toolbar by id, and set it as the action bar. Whenever the 'Note' is clicked,
         * it will return to the home screen.
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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

        Typeface myType = Typeface.createFromAsset(getAssets(), "AD.ttf");

        TextView noteHead = (TextView) findViewById(R.id.noteListTitle);
        TextView noteClass = (TextView) findViewById(R.id.classNote);
        TextView noteProf = (TextView) findViewById(R.id.noteProf);

        noteHead.setText(HomeScreen.selectedLecture.toString().trim());
        noteHead.setTypeface(myType);
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
            index = position;

            TextView textView = (TextView)noteView.findViewById(R.id.myTextView4);
            textView.setText(currentNote.toString());

            notePic = (ImageButton) noteView.findViewById(R.id.myImageButton);

            notePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyNotesNext.this, PictureGridView.class);
                    ArrayList<String> stringarr = new ArrayList<>();

                    ArrayList<Bitmap> tmp = arrayOfPicture.get(index);


                    int picCount;
                    int actualCount = 0;
                    for (picCount = 0; picCount < tmp.size()/2; picCount++) {
                        //if (picCount % 2 == 0) {
                        intent.putExtra("picture" + picCount + ".png", bm2s(tmp.get(picCount), picCount));
                        // actualCount++;
                        //}
                    }
                    intent.putExtra("numPics", tmp.size()/2);
                    intent.putExtra("rcode", FULL_VIEW);
                    startActivityForResult(intent, FULL_VIEW);

                }
            });

            TextView numUpVotes = (TextView) noteView.findViewById(R.id.myRatingNum);
            if(numUpVotes == null){
                System.out.println("numUpVotes is null");
            }
            if(currentNote == null){
                System.out.println("currentNote is null");
            }

            numUpVotes.setText(String.valueOf(currentNote.upvote));

            return noteView;
        }
    }

    private void notePicClick() {
        notePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyNotesNext.this, PictureGridView.class);
                ArrayList<String> stringarr = new ArrayList<>();

                ArrayList<Bitmap> tmp = arrayOfPicture.get(index);

                int picCount;
                for (picCount = 0; picCount < tmp.size(); picCount++) {
                    intent.putExtra("picture" + picCount + ".png", bm2s(tmp.get(picCount), picCount));
                }
                intent.putExtra("numPics", tmp.size());
                startActivityForResult(intent, FULL_VIEW);

            }
        });
    }

    /* Convert any bitmap into a string in order to send it across intents
     * because the limit for any intent file is only 1MB
     */
    private String bm2s( Bitmap bmap, int where) {

        String bmapString = "picture" + where + ".png";
        try {
            FileOutputStream fos = this.openFileOutput(bmapString, Context.MODE_PRIVATE);
            bmap.compress(Bitmap.CompressFormat.PNG, 10, fos);
            fos.close();
            // bmap.recycle();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bmapString;
    }

}
