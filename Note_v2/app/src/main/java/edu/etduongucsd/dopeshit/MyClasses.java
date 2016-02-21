package edu.etduongucsd.dopeshit;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyClasses extends AppCompatActivity {

    private static ListView list_view;
    private static String[] MYCLASSES = new String[] {"CSE 100",
                                                      "CSE 101", "CSE 105", "CSE 110", "CSE 140", "GARY"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes);
        //android.support.v7.app.ActionBar ab = getSupportActionBar();
        //ab.setLogo(R.drawable.lettern);
        //ab.setDisplayUseLogoEnabled(true);
        //ab.setDisplayShowHomeEnabled(true);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Sweet Pea_2.ttf");
        TextView myClassesTitle = (TextView) findViewById(R.id.myClassesTitle);
        myClassesTitle.setTypeface(myTypeface);

        myClassesView();
    }

    public void myClassesView () {
        list_view = (ListView)findViewById(R.id.myClassesList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.myclasses_list, MYCLASSES);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String value = (String) list_view.getItemAtPosition(position);
                        Toast.makeText(MyClasses.this, "Class: " + value, Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void openClassNotes(View view) {
        startActivity(new Intent(this, WeekLectureSelection.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
