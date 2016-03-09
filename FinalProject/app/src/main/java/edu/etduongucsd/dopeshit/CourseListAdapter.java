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
public class CourseListAdapter extends ArrayAdapter<Note> {

    private Context context;
    private int resource;
    private List<Note> classList;

    public CourseListAdapter(Context context, int resource, List<Note> objects) {
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

        Course myClass = (Course) getGroup(position);
        if(convertView == null) {
            LayoutInflater layInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layInflater.inflate(R.layout.myclasses_list, null);
        }

        TextView className = (TextView) convertView.findViewById(R.id.myClassHeading);
        TextView profName = (TextView) convertView.findViewById(R.id.myClassProf);

        className.setText((myClass.parentDepartment.getName() + " " + myClass.getName()).trim());                       //I added
        profName.setText(myClass.professors.get(0).getName().trim());                                                   //I added
       // className.setText((HomeScreen.selectedDepart.getName() + " " + HomeScreen.selectedCourse.getName()).trim());  //I took off
       // profName.setText(HomeScreen.selectedProfessor.getName().trim());                                              //I took off
       ////profName.setText(myClass.professors.get(position).getName().trim());

        return convertView;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
