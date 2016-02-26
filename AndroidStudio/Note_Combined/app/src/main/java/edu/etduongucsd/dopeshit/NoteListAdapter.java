package edu.etduongucsd.dopeshit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ERIC on 2/25/2016.
 */
public class NoteListAdapter extends ArrayAdapter<Note> {

    private Context context;
    private int resource;
    private ArrayList<Note> noteList;

    public NoteListAdapter(Context context, int resource, ArrayList<Note> objects) {
        super(context, resource, objects);
        this.context = context;
        this.noteList = objects;
    }

    public int getGroupCount(int pos) {
        return noteList.size();
    }

    public Object getGroup(int groupPos) {
        return noteList.get(groupPos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Note not = (Note) getGroup(position);
        if(convertView == null) {
            LayoutInflater layInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layInflater.inflate(R.layout.note_list, null);
        }

        TextView forClass = (TextView) convertView.findViewById(R.id.noteListHead);
        TextView forMyNote = (TextView) convertView.findViewById(R.id.myNotesHead);
        int noteNum = not.getNoteNum();
        forClass.setText(("Note " + noteNum).trim());
        forMyNote.setText(("Note " + noteNum).trim());

        return convertView;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
