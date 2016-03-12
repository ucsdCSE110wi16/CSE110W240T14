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
    ArrayList<ArrayList<Bitmap>> arrayOfPicture;

    Lecture currentLecture;
    List<Note> currNoteList;
    Note current = null;

    ImageButton upBut;
    ImageButton flagBut;
    ImageButton notePic;

    int FULL_VIEW = 212;
    ListView listView;

    Note currentNote;
    // Note currentNote;
    int index;

    int wPixel;
    int hPixel;

    Typeface myType2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myType2 = Typeface.createFromAsset(getAssets(), "PBP.ttf");

        wPixel = this.getWindowManager().getDefaultDisplay().getWidth()/6;
        hPixel = this.getWindowManager().getDefaultDisplay().getHeight()/6;
        /* Find the toolbar by id, and set it as the action bar. Whenever the 'Note' is clicked,
         * it will return to the home screen.
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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

        upBut = (ImageButton) findViewById(R.id.likeButton);
        flagBut = (ImageButton) findViewById(R.id.flagButton);
//problem could be here
        if(arrayOfPicture != null){
            arrayOfPicture = null;
        }
        arrayOfPicture = new ArrayList<ArrayList<Bitmap>>();

        for (int i = 0; i < currentLecture.notes.size(); i++) {
            if(picture != null) {
                picture = null;
            }
            picture = new ArrayList<Bitmap>();

            current = currentLecture.notes.get(i);
            picture.add(current.bitmapForFirstThumbnail(wPixel, hPixel));
            arrayOfPicture.add(picture);

        }
        ///WOrkinf vwersion of loop
        /*for (int i = 0; i < currentLecture.notes.size(); i++) {

            current = currentLecture.notes.get(i);
            for(int j = 0; j < current.pictureString.size(); j++) {
              // byte[] notePicBytes = Base64.decode(current.pictureString.get(j), Base64.DEFAULT);
              // picture.add(BitmapFactory.decodeByteArray(notePicBytes, 0, notePicBytes.length));
                int wPixel = this.getWindowManager().getDefaultDisplay().getWidth();
                int hPixel = this.getWindowManager().getDefaultDisplay().getHeight();
                picture = current.convertToNoteBitmap(wPixel, hPixel);
            }
//                    picture = current.convertToNoteBitmap(100, 100);
            arrayOfPicture.add(picture);

        }*/


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

            index = position;

            currentNote = currentLecture.notes.get(position);

            ImageView imageView = (ImageView)noteView.findViewById(R.id.imageButton);
            imageView.setImageBitmap(arrayOfPicture.get(position).get(0));

            TextView textView = (TextView)noteView.findViewById(R.id.textView4);
            textView.setText(currentNote.toString());

            TextView numPages = (TextView) noteView.findViewById(R.id.numPages);
            System.out.println("Testdsayifhiasfhyuas11111111: "+currentNote.pictureString.size());
            System.out.println(position);
                System.out.println("Testdsayifhiasfhyuas: "+currentNote.pictureString.size());
            if (currentNote.pictureString == null) {
                numPages.setText((String.valueOf(currentNote.pictureNote.size()) + " Page(s)").trim());
                numPages.setTypeface(myType2);
            } else {
                System.out.println("Testdsayifhiasfhyuas: " + currentNote.pictureString.size());
                numPages.setText((String.valueOf(currentNote.pictureString.size()) + " Page(s)").trim());
                numPages.setTypeface(myType2);
            }


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
            notePic = (ImageButton) noteView.findViewById(R.id.imageButton);
            notePic.setTag(position);
            upVote.setTag(position);
            flagButton.setTag(position);

            upVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Upvote");
                    int buttonPosition = (Integer) v.getTag();
                    currentLecture.notes.get(buttonPosition).toggleUpvotes();
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
                    int buttonPosition = (Integer)v.getTag();
                    currentLecture.notes.get(buttonPosition).toggleFlag();
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

                    int buttonPosition = (Integer)v.getTag();

                    ArrayList<Bitmap> tmp = currentLecture.notes.get(buttonPosition).convertToNoteBitmap(wPixel, hPixel);

                    System.out.println("Button picture thing is: "+buttonPosition);


                    for (int picCount = 0; picCount < tmp.size(); picCount++) {
                    //if (picCount % 2 == 0) {
                        intent.putExtra("picture" + picCount + ".png", bm2s(tmp.get(picCount), picCount));
                    // actualCount++;
                    }
                 //   }
                    intent.putExtra("numPics", tmp.size());// tmp.size()/2);
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
            bmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            // bmap.recycle();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bmapString;
    }



}
