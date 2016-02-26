package edu.etduongucsd.dopeshit;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ERIC on 2/24/2016.
 */
public class ClassInfoListAdapter extends ArrayAdapter<ClassInfo> {

    private Context context;
    private int resource;
    private ArrayList<ClassInfo> classList;
    //private ArrayList<ClassInfo> originalList;

    //public ClassInfoListAdapter(Context context, ArrayList<ClassInfo> classes) {
      //  this.classList = classes;
        //this.context = context;
    //}

    public ClassInfoListAdapter(Context context, int resource, ArrayList<ClassInfo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.classList = objects;
    }


    //public ClassInfoListAdapter(Context context, int resource, List<ClassInfo> objects) {
      //  super(context, resource, objects);
    //}

    public int getGroupCount(int pos) {
        return classList.size();
    }

    public Object getGroup(int groupPos) {
        return classList.get(groupPos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ClassInfo myClass = (ClassInfo) getGroup(position);
        if(convertView == null) {
            LayoutInflater layInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layInflater.inflate(R.layout.myclasses_list, null);
        }

        TextView className = (TextView) convertView.findViewById(R.id.myClassHeading);
        TextView profName = (TextView) convertView.findViewById(R.id.myClassProf);

        className.setText(myClass.getClassName().trim());
        profName.setText(myClass.getProfessor().trim());

        return convertView;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
