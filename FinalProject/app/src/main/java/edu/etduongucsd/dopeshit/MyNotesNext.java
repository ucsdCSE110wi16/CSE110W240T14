package edu.etduongucsd.dopeshit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.firebase.client.Firebase;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ERIC on 3/6/2016.
 */
public class MyNotesNext extends AppCompatActivity {

    ArrayList<Bitmap> picture;
    ArrayList<ArrayList<Bitmap>> arrayOfPicture;

    int index;
    List<Note> currNoteList;
    Note current = null;
    ImageButton notePic;
    int FULL_VIEW = 212;
    int wPixel;
    int hPixel;
    public Typeface myType2;

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
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MyNotesNext.this, HomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyNotesNext.this, SettingsPage.class));
            }
        });
        currNoteList = new ArrayList<Note>();
        for(Lecture lecture : HomeScreen.selectedProfessor.lectures){
            for(Note note : lecture.notes){
                System.out.println("NUMBER OF MY NOTE: " + StartingPoint.myNotes.size());
                if(StartingPoint.myNotes.contains(note.dataBaseRef+"Note " + note.noteNum+"/")){
                    currNoteList.add(note);
                }
            }
        }

//        currNoteList = HomeScreen.userProfile.userUpNotes;
        //Collections.sort(currNoteList, Note.ASC_NOTES);

        if(arrayOfPicture != null){
            arrayOfPicture = null;
        }
        arrayOfPicture = new ArrayList<ArrayList<Bitmap>>();

        for (int i = 0; i < currNoteList.size(); i++) {
            if(picture != null) {
                picture = null;
            }
            picture = new ArrayList<Bitmap>();

            current = currNoteList.get(i);
            picture.add(current.bitmapForFirstThumbnail(wPixel, hPixel));
            arrayOfPicture.add(picture);
        }

        Typeface myType = Typeface.createFromAsset(getAssets(), "Lob.otf");
        Typeface myType2 = Typeface.createFromAsset(getAssets(), "PBP.ttf");

        TextView noteHead = (TextView) findViewById(R.id.noteListTitle);
        TextView noteClass = (TextView) findViewById(R.id.classNote);
        TextView noteProf = (TextView) findViewById(R.id.noteProf);

        noteHead.setText("My Notes".trim());
        noteHead.setTypeface(myType);
        noteClass.setText(HomeScreen.selectedDepart.getName() + " " + HomeScreen.selectedCourse.getName().trim());
        noteProf.setText(HomeScreen.selectedProfessor.getName().trim());

        ArrayAdapter<Note> adapter = new MyNoteListAdapter();
        ListView listView = (ListView) findViewById(R.id.notesList);
        listView.setAdapter(adapter);
    }

    private class MyNoteListAdapter extends ArrayAdapter<Note>{
        public MyNoteListAdapter(){
            super(MyNotesNext.this, R.layout.mynote_display_layout, currNoteList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View noteView = convertView;
            if(noteView == null){
                noteView = getLayoutInflater().inflate(R.layout.mynote_display_layout, parent, false);
            }

            final Note currentNote = currNoteList.get(position);

            ImageView imageView = (ImageView)noteView.findViewById(R.id.myImageButton);
            imageView.setImageBitmap(arrayOfPicture.get(position).get(0));
            index = position;

            TextView textView = (TextView)noteView.findViewById(R.id.myTextView4);
            textView.setText(currentNote.toString());

            TextView numPages = (TextView) noteView.findViewById(R.id.myNumPages);
            if(currentNote.pictureString == null){
                numPages.setText((0 + " Page(s)").trim());
                numPages.setTypeface(myType2);
            }
            else{
                numPages.setText(((currentNote.pictureString.size()) + " Page(s)").trim());
                numPages.setTypeface(myType2);
            }

            notePic = (ImageButton) noteView.findViewById(R.id.myImageButton);

            notePic.setTag(position);

            notePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyNotesNext.this, PictureGridView.class);
                    ArrayList<String> stringarr = new ArrayList<>();

                    int buttonPosition = (Integer)v.getTag();
                    ArrayList<Bitmap> tmp = currNoteList.get(buttonPosition).convertToNoteBitmap(wPixel, hPixel);


                    for (int picCount = 0; picCount < tmp.size(); picCount++) {
                        //if (picCount % 2 == 0) {
                        intent.putExtra("picture" + picCount + ".png", bm2s(tmp.get(picCount), picCount));
                        // actualCount++;
                        //}
                    }
                    intent.putExtra("numPics", tmp.size());
                    intent.putExtra("rcode", FULL_VIEW);
                    startActivityForResult(intent, FULL_VIEW);

                }
            });

            TextView numUpVotes = (TextView) noteView.findViewById(R.id.myRatingNum);


            numUpVotes.setText(String.valueOf(currentNote.upvote));

            ImageButton delBut = (ImageButton) noteView.findViewById(R.id.deleteButton);
            delBut.setTag(position);

            delBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int buttonPosition = (Integer) v.getTag();
                    final Note currNote = currNoteList.get(buttonPosition);

                    AlertDialog.Builder builder = new AlertDialog.Builder(MyNotesNext.this);
                    builder.setTitle("Delete Note");
                    builder.setMessage("Are you sure you want to remove these notes?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO REMOVE NOTE FUNC CALL
                            Note removedNote = currNote;
                            for (Note note : HomeScreen.userProfile.userUpNotes) {
                                if ((note.dataBaseRef + "Note " + note.noteNum + "/").equals(removedNote.dataBaseRef + "Note " + removedNote.noteNum + "/")) {
                                    StartingPoint.myNotes.remove(note);
                                    StartingPoint.preferenceEditor.remove((StartingPoint.myProfile.name + "myNotes"));
                                    StartingPoint.preferenceEditor.commit();
                                    StartingPoint.preferenceEditor.putStringSet((StartingPoint.myProfile.name + "myNotes"), StartingPoint.myNotes);
                                    StartingPoint.preferenceEditor.commit();
                                    Firebase ref = new Firebase(currNote.dataBaseRef);
                                    ref.child("Note " + currNote.noteNum).child("Removed").setValue(true);
                                    note.removed = true; //May not need
                                    int index = 0;
                                    for (Lecture lecture : HomeScreen.selectedProfessor.lectures) {
                                        for (Note removeNote : lecture.notes) {
                                            index++;
                                        }
                                    }
                                    for (Lecture lecture : HomeScreen.selectedProfessor.lectures) {
                                        for (Note removeNote : lecture.notes) {
                                            if ((removeNote.dataBaseRef + removeNote.toString()).equals(note.dataBaseRef + note.toString())) {
                                                lecture.notes.remove(note);
                                                    Intent intent = getIntent();
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                    finish();
                                                    startActivity(intent);

                                            }
                                        }
                                    }
                                }
                            }
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void
                        onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.show();


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
