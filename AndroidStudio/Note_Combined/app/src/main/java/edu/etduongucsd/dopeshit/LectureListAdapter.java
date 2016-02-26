package edu.etduongucsd.dopeshit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ERIC on 2/25/2016.
 */
public class LectureListAdapter extends ArrayAdapter<Lecture> {

    private Context context;
    private int resource;
    private ArrayList<Lecture> lectureList;

    public LectureListAdapter(Context context, int resource, ArrayList<Lecture> objects) {
        super(context, resource, objects);
        this.context = context;
        this.lectureList = objects;
    }

    public int getGroupCount(int pos) {
        return lectureList.size();
    }

    public Object getGroup(int groupPos) {
        return lectureList.get(groupPos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Lecture lec = (Lecture) getGroup(position);
        if(convertView == null) {
            LayoutInflater layInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layInflater.inflate(R.layout.lecture_list, null);
        }

        TextView lectureHead = (TextView) convertView.findViewById(R.id.lecListHead);
        int lecNum = lec.getLectureNum();
        lectureHead.setText(("Lecture " + lecNum).trim());

        return convertView;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
