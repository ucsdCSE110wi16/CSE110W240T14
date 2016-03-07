package edu.etduongucsd.dopeshit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by wasle on 3/6/2016.
 */
public class FullScreenPreview extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.full_image_layout);
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
    }
}
