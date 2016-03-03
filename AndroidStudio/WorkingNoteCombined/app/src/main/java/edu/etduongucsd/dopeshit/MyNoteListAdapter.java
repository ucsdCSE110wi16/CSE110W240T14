package edu.etduongucsd.dopeshit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ERIC on 2/26/2016.
 */
//Adapter used in mynotes screen
public class MyNoteListAdapter extends ArrayAdapter<Note> {

    private Context context;
    private int resource;
    private ArrayList<Note> myNoteList;

    public MyNoteListAdapter(Context context, int resource, ArrayList<Note> objects) {
        super(context, resource, objects);
        this.context = context;
        this.myNoteList = objects;
    }

    public int getGroupCount(int pos) {
        return myNoteList.size();
    }

    public Object getGroup(int groupPos) {
        return myNoteList.get(groupPos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Note not = (Note) getGroup(position);
        if(convertView == null) {
            LayoutInflater layInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layInflater.inflate(R.layout.mynotes_layout, null);
        }

        //TextView forClass = (TextView) convertView.findViewById(R.id.noteListHead);
        TextView forMyNote = (TextView) convertView.findViewById(R.id.myNotesHead);
        int noteNum = not.getNoteNum();
        //forClass.setText(("Note " + noteNum).trim());
        forMyNote.setText(("Note " + noteNum).trim());

        return convertView;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}