package edu.etduongucsd.dopeshit;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class uploadPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner classSpin;
    Spinner profSpin;
    Spinner weekSpin;
    Spinner lecNumSpin;
    private Button upload;
    private Button moreButton;
    private Button finalUpload;
    private ImageButton imageSlot1;
    private ImageButton imageSlot2;
    private ImageButton imageSlot3;
    private final static int SELECT_PHOTO = 12345;
    private ArrayList<Bitmap> bmapArray = new ArrayList<Bitmap>();
    private int numPictures = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_page);
        moreButton = (Button) findViewById(R.id.moreButton);
        imageSlot1 = (ImageButton) findViewById(R.id.imageSlot1);
        imageSlot2 = (ImageButton) findViewById(R.id.imageSlot2);
        imageSlot3 = (ImageButton) findViewById(R.id.imageSlot3);
        finalUpload = (Button) findViewById(R.id.final_upload);
        moreButton.setVisibility(View.INVISIBLE);
        imageSlot1.setVisibility(View.INVISIBLE);
        imageSlot2.setVisibility(View.INVISIBLE);
        imageSlot3.setVisibility(View.INVISIBLE);
        finalUpload.setVisibility(View.INVISIBLE);

        moreButton.setEnabled(true);

        /* Find the toolbar by id, and set it as the action bar. Whenever the 'Note' is clicked,
         * it will return to the home screen.
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(uploadPage.this, HomeScreen.class));
            }
        });
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(uploadPage.this, SettingsPage.class));
            }
        });

        // Choosing which photos from the gallery you want
        upload = (Button) findViewById(R.id.noteSelButton);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hasReadExternalPerm = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                if (hasReadExternalPerm != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);
                    return;
                }
                Intent photoPick = new Intent(Intent.ACTION_PICK);
                photoPick.setType("image/*");
                startActivityForResult(photoPick, SELECT_PHOTO);
                numPictures++;
            }
        });


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageButton iView;
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri pickedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            // BitmapFactory.Options options = new BitmapFactory.Options();
            // options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            bmapArray.add(bitmap);
            int numPictures = bmapArray.size();
            String imageSlot = "R.id.imageSlot" + numPictures;
            if (numPictures == 1) {
                imageSlot1.setImageBitmap(bitmap);
                imageSlot1.setVisibility(View.VISIBLE);
            }
            else if (numPictures == 2) {
                imageSlot2.setImageBitmap(bitmap);
                imageSlot2.setVisibility(View.VISIBLE);
            }
            else if (numPictures == 3) {
                imageSlot3.setImageBitmap(bitmap);
                imageSlot3.setVisibility(View.VISIBLE);
            }
            else {
                int morePictures = numPictures - 3;
                moreButton.setVisibility(View.VISIBLE);
                moreButton.setText(morePictures + " more - click here to view all pictures");

                // TODO set some variable that says how many more you have + 1
            }
            finalUpload.setVisibility(View.VISIBLE);
            // Close the cursor so we go back to the main page
            cursor.close();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
