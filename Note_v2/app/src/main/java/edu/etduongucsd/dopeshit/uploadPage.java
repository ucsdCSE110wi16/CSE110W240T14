package edu.etduongucsd.dopeshit;

import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class uploadPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner classSpin;
    Spinner profSpin;
    Spinner weekSpin;
    Spinner lecNumSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_page);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        classSpin = (Spinner) findViewById(R.id.classSpinner);
        profSpin = (Spinner) findViewById(R.id.profSpinner);
        weekSpin = (Spinner) findViewById(R.id.weekSpinner);
        lecNumSpin = (Spinner) findViewById(R.id.lecNumSpinner);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.classUploadList, android.R.layout.simple_spinner_item);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.profUploadList, android.R.layout.simple_spinner_item);
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this, R.array.weekUploadList, android.R.layout.simple_spinner_item);
        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this, R.array.lectureNumUploadList, android.R.layout.simple_spinner_item);

        classSpin.setAdapter(adapter);
        profSpin.setAdapter(adapter2);
        weekSpin.setAdapter(adapter3);
        lecNumSpin.setAdapter(adapter4);

        classSpin.setOnItemSelectedListener(this);
        profSpin.setOnItemSelectedListener(this);
        weekSpin.setOnItemSelectedListener(this);
        lecNumSpin.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.classSpinner:
                break;
            case R.id.profSpinner:
                break;
            case R.id.weekSpinner:
                break;
            case R.id.lecNumSpinner:
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
