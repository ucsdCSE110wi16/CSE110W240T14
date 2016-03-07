package edu.etduongucsd.dopeshit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
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

    Button upBut;
    Button flagBut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentLecture = HomeScreen.selectedLecture;
        currNoteList = currentLecture.notes;
        Collections.sort(currNoteList, Note.ASC_NOTES);

        picture = new ArrayList<Bitmap>();

        upBut = (Button) findViewById(R.id.likeButton);
        flagBut = (Button) findViewById(R.id.flagButton);

        for (int i = 0; i < currentLecture.notes.size(); i++) {

            current = currentLecture.notes.get(i);
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

    public void upButOnClick() {
        upBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.incUpvote();
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });
    }


    public void flagButOnClick() {
        flagBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.incUpvote();
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });
    }

    //Not implemented yet
    public void pictureButtonClick(){
        String message = "Display fullscreen images";
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
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

            Note currentNote = currentLecture.notes.get(position);

            ImageView imageView = (ImageView)noteView.findViewById(R.id.imageButton);
            imageView.setImageBitmap(arrayOfPicture.get(position).get(0));

            TextView textView = (TextView)noteView.findViewById(R.id.textView4);
            textView.setText(currentNote.toString());

            TextView numUpVotes = (TextView) findViewById(R.id.numVotes);
            //numUpVotes.setText(currentNote.getUpvote());

            return noteView;
        }
    }


}
