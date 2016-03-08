package edu.etduongucsd.dopeshit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ERIC on 3/6/2016.
 */
public class FirstNoteListAdapter extends ArrayAdapter<Note> {
    private Context context;
    private int resource;
    private List<Note> noteList;

    public FirstNoteListAdapter(Context context, int resource, List<Note> objects) {
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

        Note myNote = (Note) getGroup(position);
        if(convertView == null) {
            LayoutInflater layInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layInflater.inflate(R.layout.myclasses_list, null);
        }

        TextView className = (TextView) convertView.findViewById(R.id.myClassHeading);
        TextView profName = (TextView) convertView.findViewById(R.id.myClassProf);

        className.setText(myNote.parentLecture.parentProfessor.parentCourse.getName().trim());
        profName.setText(HomeScreen.selectedProfessor.getName().trim());
        //profName.setText(myClass.professors.get(position).getName().trim());

        return convertView;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }



}
