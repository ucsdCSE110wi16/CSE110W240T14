package edu.etduongucsd.dopeshit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * Created on 2/29/2016.
 */
//Adapter used on list in AllClasses.class
public class ExpandableListAdapter extends BaseExpandableListAdapter{
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    private List<String> copy_listDataHeader;
    private HashMap<String, List<String>> copy_listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;

        this.copy_listDataHeader = listDataHeader;
        this.copy_listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_row, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.classCode);
       // TextView listProf = (TextView) convertView
       // .findViewById(R.id.classProfessor);
       // TextView listClassName = (TextView) convertView
        //        .findViewById(R.id.className);

        txtListChild.setText(childText);
       // listProf.setText("");
       // listClassName.setText("");
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_row, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.heading);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /*
    public void filterDate(String query) {
        query = query.toLowerCase();
        Log.v("ExpandableListAdapter", String.valueOf(_listDataChild.size()));
        _listDataChild.clear();

        if(query.isEmpty()) {
            _listDataChild = (HashMap) copy_listDataChild.clone();
        }
        else {
            for(String listString: copy_listDataChild.) {
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

        Log.v("ExpandableListAdapter", String.valueOf(classList.size()));
        notifyDataSetChanged();

    }*/
}
