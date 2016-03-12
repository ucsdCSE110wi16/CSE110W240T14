package edu.etduongucsd.dopeshit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by cwasley on 3/6/2016.
 */
public class PictureGridView extends AppCompatActivity {

    GridView gridView;
    ArrayList<String> bmapList;
    int DISPLAY_FROM_GRID = 1337;
    int deleted = -1;
    boolean noDelete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        if(i.getIntExtra("rcode", -1) != -1){
            noDelete = true;
        }
        bmapList = new ArrayList<>();
        int picCount = i.getIntExtra("numPics", -1);
        if (picCount != -1) {
            for (int j = 0; j < picCount; j++) {
                bmapList.add(j, i.getStringExtra("picture" + j + ".png"));
            }
        }

        setContentView(R.layout.grid_view_layout);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new GridViewAdapter(getApplicationContext(), bmapList));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PictureGridView.this, FullScreenPreview.class);
                intent.putExtra("numPic", position);
                if(noDelete){
                    intent.putExtra("nodelete", true);
                }
                intent.putExtra("bmap", bmapList.get(position));
                startActivityForResult(intent, DISPLAY_FROM_GRID);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /* Section for picture selection */
        if (requestCode == DISPLAY_FROM_GRID && resultCode == RESULT_OK && data != null) {
            String delete = data.getStringExtra("delete");
            if (delete != null) {
                if (delete.equals("yes")) {
                    int numToDelete = data.getIntExtra("picToDelete", -1);
                    deleted = numToDelete;
                    Intent i = new Intent();
                    i.putExtra("deleted", numToDelete);
                    setResult(Activity.RESULT_OK, i);
                    finish();
                }
            }
        }
    }

}
