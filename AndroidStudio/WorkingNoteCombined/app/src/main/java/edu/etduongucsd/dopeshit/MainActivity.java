package edu.etduongucsd.dopeshit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import java.util.List;
//Note display page
public class MainActivity extends AppCompatActivity {

    ArrayList<Bitmap> picture;
    ArrayList<ArrayList<Bitmap>> arrayOfPicture = new ArrayList<ArrayList<Bitmap>>();

    Lecture currentLecture;

    Note current = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_display);

        currentLecture = HomeScreen.selectedLecture;

        picture = new ArrayList<Bitmap>();

        for(int i = 0; i < currentLecture.notes.size(); i++){
            current = currentLecture.notes.get(i);
            picture = current.convertToNoteBitmap(100, 100);
            arrayOfPicture.add(picture);
        }

        ArrayAdapter<Note> adapter = new MyNoteListAdapter();
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
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

            return noteView;
        }
    }


}
