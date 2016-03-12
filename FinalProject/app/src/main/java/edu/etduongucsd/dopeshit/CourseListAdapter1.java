package edu.etduongucsd.dopeshit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ERIC on 3/5/2016.
 */
public class CourseListAdapter1 extends ArrayAdapter<Professor> {

    private Context context;
    private int resource;
    private List<Professor> classList;

    public CourseListAdapter1(Context context, int resource, List<Professor> objects) {
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
            convertView = layInflater.inflate(R.layout.myclasses_list, null);
        }

        TextView className = (TextView) convertView.findViewById(R.id.myClassHeading);
        TextView profName = (TextView) convertView.findViewById(R.id.myClassProf);

        className.setText((myClass.parentCourse.parentDepartment.getName() + " " + myClass.parentCourse.getName()).trim());
        profName.setText(myClass.getName().trim());
        //className.setText((HomeScreen.selectedDepart.getName() + " " + HomeScreen.selectedCourse.getName()).trim());
        //profName.setText(HomeScreen.selectedProfessor.getName().trim());
       ////profName.setText(myClass.professors.get(position).getName().trim());

        final ImageButton delClassBut = (ImageButton) convertView.findViewById(R.id.delClassBut);

        delClassBut.setTag(position);
        delClassBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Remove My Class");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Removing From My Courses");
                builder.setMessage("Are you sure you want to remove this course?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO REMOVE CLASS FUNC CALL
                        int buttonPosition = (Integer) delClassBut.getTag();
                       Professor newProf = classList.get(buttonPosition);

                        if (!(HomeScreen.userProfile.myCourses.contains(newProf))) {
                            Toast.makeText(context, HomeScreen.selectedDepart.getName() + " " + HomeScreen.selectedCourse.getName() + " is not in your list of classes!", Toast.LENGTH_LONG).show();
                        } else {
                            HomeScreen.userProfile.myCourses.remove(newProf);

                            StartingPoint.myCourses.remove(newProf.dataBaseRef + newProf.name + "/");

                            StartingPoint.preferenceEditor.remove((StartingPoint.myProfile.name + "myCourses"));
                            StartingPoint.preferenceEditor.commit();
                            StartingPoint.preferenceEditor.putStringSet((StartingPoint.myProfile.name + "myCourses"), StartingPoint.myCourses);
                            StartingPoint.preferenceEditor.commit();

                          //  Toast.makeText(context, HomeScreen.selectedDepart.getName() + " " + HomeScreen.selectedCourse.getName() + " has been removed to your classes!", Toast.LENGTH_LONG).show();
                        }
                        Intent intent = ((Activity) context).getIntent();
                        Activity act = (Activity)context;
                        act.finish();
                        act.startActivity(intent);


                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void
                    onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });

        return convertView;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
