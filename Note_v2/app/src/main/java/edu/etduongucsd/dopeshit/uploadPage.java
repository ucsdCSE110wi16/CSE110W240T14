package edu.etduongucsd.dopeshit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class uploadPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner classSpin;
    Spinner profSpin;
    Spinner weekSpin;
    Spinner lecNumSpin;
    private Button upload;
    ImageView imgView;
    private final static int SELECT_PHOTO = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_page);

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
                Intent photoPick = new Intent(Intent.ACTION_PICK);
                photoPick.setType("image/*");
                startActivityForResult(photoPick, SELECT_PHOTO);
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

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri pickedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            imgView.setImageBitmap(bitmap);


            // TODO: do something with the photo (add it to db?)
            cursor.close();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
