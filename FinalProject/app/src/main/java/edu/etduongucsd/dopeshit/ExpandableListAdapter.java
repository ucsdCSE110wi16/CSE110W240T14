package edu.etduongucsd.dopeshit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    public HashMap<String, List<String>> _listDataChild;

    private List<String> copy_listDataHeader;
    private HashMap<String, List<String>> copy_listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;

        this._listDataHeader = new ArrayList<String>();
        this._listDataHeader.addAll(listDataHeader);

        this._listDataChild = listChildData;

        this.copy_listDataHeader = new ArrayList<String>();
        this.copy_listDataHeader.addAll(listDataHeader);

        this.copy_listDataChild = (HashMap) listChildData.clone();

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

        txtListChild.setText(childText);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
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


    public void filterDate(String query) {

        query = query.toLowerCase();

        _listDataHeader.clear();

        if(query.isEmpty()) {
            _listDataHeader.addAll(copy_listDataHeader);
        }

        else {

            //Header
            for(String cString: copy_listDataHeader) {

                List<String> newList = new ArrayList<String>();
                if(cString.toLowerCase().contains(query)) {
                    newList.add(cString);
                }
                if(newList.size() > 0) {
                    _listDataHeader.addAll(newList);

                }
            }
        }

        notifyDataSetChanged();
    }
}
