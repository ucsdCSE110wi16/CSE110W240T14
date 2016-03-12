package edu.etduongucsd.dopeshit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ERIC on 3/5/2016.
 */
public class LectureListAdapter extends ArrayAdapter<Lecture> {

    private Context context;
    private List<Lecture> lectureList;

    public LectureListAdapter(Context context, int resource, List<Lecture> objects) {
        super(context, resource, objects);
        this.context = context;
        this.lectureList = objects;
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
        String displayDate = lec.date;
        lectureHead.setText((displayDate + " Lecture").trim());

        TextView lecNums = (TextView) convertView.findViewById(R.id.lecListNum);
        int numNotes = lec.numberOfNotes;
        String lecNumText = (numNotes + " Notes");
        lecNums.setText(lecNumText);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
