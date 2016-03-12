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
 * Created by ERIC on 3/5/2016.
 */
public class ProfListAdapter extends ArrayAdapter<Professor> {

    private Context context;
    private int resource;
    private List<Professor> profList;

    public ProfListAdapter(Context context, int resource, List<Professor> objects) {
        super(context, resource, objects);
        this.context = context;
        this.profList = objects;
    }

    public Object getGroup(int groupPos) {
        return profList.get(groupPos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Professor prof = (Professor) getGroup(position);
        if(convertView == null) {
            LayoutInflater layInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layInflater.inflate(R.layout.proflist, null);
        }

        TextView profNameField = (TextView) convertView.findViewById(R.id.profListHead);
        String profName = prof.getName();
        profNameField.setText((profName.trim()));

        return convertView;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
