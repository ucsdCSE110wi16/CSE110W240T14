package edu.etduongucsd.dopeshit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import junit.framework.Test;

import java.util.ArrayList;

/**
 * Created by ERIC on 2/16/2016.
 */
public class MyListAdapter extends BaseExpandableListAdapter{

    private Context context;
    private ArrayList<ClassList> classList;
    private ArrayList<ClassList> originalList;

    public MyListAdapter(Context context, ArrayList<ClassList> classList) {
        this.context = context;
        this.classList = new ArrayList<ClassList>();
        this.classList.addAll(classList);
        this.originalList = new ArrayList<ClassList>();
        this.originalList.addAll(classList);
    }

    @Override
    public int getGroupCount() {
        return classList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<ClassInfo> listofClasses = classList.get(groupPosition).getClassList();
        return listofClasses.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return classList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ClassInfo> listofClasses = classList.get(groupPosition).getClassList();
        return listofClasses.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ClassList classes = (ClassList) getGroup(groupPosition);
        if(convertView == null) {
            LayoutInflater layInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layInflater.inflate(R.layout.group_row, null);
        }

        TextView heading = (TextView) convertView.findViewById(R.id.heading);
        heading.setText(classes.getName().trim());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ClassInfo class_info = (ClassInfo) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layInflater.inflate(R.layout.child_row, null);

        }

        TextView code = (TextView) convertView.findViewById(R.id.classCode);
        TextView className = (TextView) convertView.findViewById(R.id.className);
        TextView professor = (TextView) convertView.findViewById(R.id.classProfessor);
        code.setText(class_info.getCode().trim());
        className.setText(class_info.getClassName().trim());
        professor.setText(class_info.getProfessor().trim());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterDate(String query) {
        query = query.toLowerCase();
        Log.v("MyListAdapter", String.valueOf(classList.size()));
        classList.clear();

        if(query.isEmpty()) {
            classList.addAll(originalList);
        }
        else {
            for(ClassList classInList: originalList) {
                ArrayList<ClassInfo> class_info = classInList.getClassList();
                ArrayList<ClassInfo> newList = new ArrayList<ClassInfo>();
                for (ClassInfo classinfo: class_info) {
                    if(classinfo.getCode().toLowerCase().contains(query) || classinfo.getClassName().toLowerCase().contains(query)) {
                        newList.add(classinfo);
                    }
                }
                if(newList.size() > 0) {
                    ClassList nClassList = new ClassList(classInList.getName(), newList);
                    classList.add(nClassList);
                }
            }
        }

        Log.v("MyListAdapter", String.valueOf(classList.size()));
        notifyDataSetChanged();

    }
}
