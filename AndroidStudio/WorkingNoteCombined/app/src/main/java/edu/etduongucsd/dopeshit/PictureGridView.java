package edu.etduongucsd.dopeshit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by cwasley on 3/6/2016.
 */
public class PictureGridView extends AppCompatActivity {

    GridView gridView;
    ArrayList<String> bmapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        bmapList = i.getStringArrayListExtra("bmaps");

        setContentView(R.layout.grid_view_layout);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new GridViewAdapter(getApplicationContext(), bmapList));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Success, bitch");
            }
        });

/*
        Intent intent = getIntent();
        final Intent i = new Intent();
        String bms = intent.getStringExtra("bmap");
        Bitmap bmap = null;
        try {
            bmap = BitmapFactory.decodeStream(context.openFileInput(bms));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final int numPic = intent.getIntExtra("numPic", -1);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        Button backButton = (Button) findViewById(R.id.backButton);
        ImageView picture = (ImageView) findViewById(R.id.insertPicture);
        picture.setImageBitmap(bmap);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int toDelete = 100 + numPic;
                i.putExtra("delete", "yes");
                i.putExtra("picToDelete", toDelete);
                setResult(Activity.RESULT_OK, i);
                finish();
                // deletePicture(toDelete);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
        */
    }

}
