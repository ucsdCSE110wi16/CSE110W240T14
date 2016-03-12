package edu.etduongucsd.dopeshit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ERIC on 3/5/2016.
 */
public class CourseListAdapter2 extends ArrayAdapter<Professor> {

    private Context context;
    private int resource;
    private List<Professor> classList;

    public CourseListAdapter2(Context context, int resource, List<Professor> objects) {
        super(context, resource, objects);
        this.context = context;
        this.classList = objects;
    }

    public int getGroupCount(int pos) {
        return classList.size();
    }

    public Object getGroup(int groupPos) {
        return classList.get(groupPos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Professor myClass = (Professor) getGroup(position);
        if(convertView == null) {
            LayoutInflater layInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layInflater.inflate(R.layout.mynotes_list, null);
        }

        TextView className = (TextView) convertView.findViewById(R.id.myNoteClassHeading);
        TextView profName = (TextView) convertView.findViewById(R.id.myNoteClassProf);

        className.setText((myClass.parentCourse.parentDepartment.getName() + " " + myClass.parentCourse.getName()).trim());
        profName.setText(myClass.getName().trim());

        return convertView;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
