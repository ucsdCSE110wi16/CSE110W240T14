package edu.etduongucsd.dopeshit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//Note display page
public class MainActivity extends AppCompatActivity {

    ArrayList<Bitmap> picture;
    ArrayList<ArrayList<Bitmap>> arrayOfPicture = new ArrayList<ArrayList<Bitmap>>();

    Lecture currentLecture;
    List<Note> currNoteList;
    Note current = null;

    ImageButton upBut;
    ImageButton flagBut;

    int FULL_VIEW = 212;
    ListView listView;
    // Note currentNote;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Find the toolbar by id, and set it as the action bar. Whenever the 'Note' is clicked,
         * it will return to the home screen.
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HomeScreen.class));
            }
        });
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsPage.class));
            }
        });

        currentLecture = HomeScreen.selectedLecture;
        currNoteList = currentLecture.notes;
        Collections.sort(currNoteList, Note.ASC_NOTES);
        Collections.reverse(currNoteList);

        picture = new ArrayList<Bitmap>();

        upBut = (ImageButton) findViewById(R.id.likeButton);
        flagBut = (ImageButton) findViewById(R.id.flagButton);

        for (int i = 0; i < currentLecture.notes.size(); i++) {

            current = currentLecture.notes.get(i);
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
        listView = (ListView) findViewById(R.id.notesList);
        listView.setAdapter(adapter);

        //May cause an issue
        //upvoteButOnClick();
     //   flagButOnClick();
    }
    //not implemented
    public void upvoteButOnClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("upVotedButton");
                //  current.toggleUpvotes();
                //    Intent intent = getIntent();
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                //   finish();
                //    startActivity(intent);
            }
        });
    }

    private class MyNoteListAdapter extends ArrayAdapter<Note>{
        public MyNoteListAdapter(){
            super(MainActivity.this, R.layout.note_display_layout, currentLecture.notes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View noteView = convertView;
            if(noteView == null){
                noteView = getLayoutInflater().inflate(R.layout.note_display_layout, parent, false);
            }

            final Note currentNote = currentLecture.notes.get(position);

            final int index = position;
            ImageView imageView = (ImageView)noteView.findViewById(R.id.imageButton);
            imageView.setImageBitmap(arrayOfPicture.get(position).get(0));

            TextView textView = (TextView)noteView.findViewById(R.id.textView4);
            textView.setText(currentNote.toString());

            TextView numUpVotes = (TextView) noteView.findViewById(R.id.ratingNum);
            numUpVotes.setText(String.valueOf(currentNote.upvote));

            ImageButton upVote = (ImageButton)noteView.findViewById(R.id.likeButton);
            if(currentNote.upvoteBool) {
                upVote.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint
            }
            ImageButton flagButton = (ImageButton)noteView.findViewById(R.id.flagButton);
            if(currentNote.flagBool) {
                flagButton.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint
            }
            ImageButton notePic = (ImageButton) noteView.findViewById(R.id.imageButton);

            upVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Upvote");
                    currentNote.toggleUpvotes();
                    Intent intent = getIntent();
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
                }
            });


            flagButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Flagged");
                    currentNote.toggleFlag();
                    Intent intent = getIntent();

                    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
                }
            });

            notePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, PictureGridView.class);
                    ArrayList<String> stringarr = new ArrayList<>();

                    ArrayList<Bitmap> tmp = arrayOfPicture.get(index);


                    int picCount = 0;
                    int actualCount = 0;
                    //for (picCount = 0; picCount < tmp.size()/2; picCount++) {
                    //if (picCount % 2 == 0) {
                    intent.putExtra("picture" + picCount + ".png", bm2s(tmp.get(picCount), picCount));
                    // actualCount++;
                    //}
                    //}
                    intent.putExtra("numPics", 1);// tmp.size()/2);
                    intent.putExtra("rcode", FULL_VIEW);
                    startActivityForResult(intent, FULL_VIEW);

                }
            });


            return noteView;
        }
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
