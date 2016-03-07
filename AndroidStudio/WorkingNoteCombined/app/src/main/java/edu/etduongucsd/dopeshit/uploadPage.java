package edu.etduongucsd.dopeshit;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
//Upload page
public class uploadPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner classSpin, profSpin, weekSpin, lecNumSpin;
    private Button upload, moreButton, finalUpload, deleteButton, backButton;
    private ImageButton imageSlot1, imageSlot2, imageSlot3, xOne, xTwo, xThree;
    private final static int SELECT_PHOTO = 12345;
    private final static int DISPLAY_PHOTO = 777;
    private final static int GRID_VIEW = 666;
    private ArrayList<Bitmap> bmapArray = new ArrayList<Bitmap>();
    private int numPictures = 0;

    int departNumber;
    int courseNumber;
    int profNumber;
    int lectNumber;

    Bundle b;
    Context context = this;
    Lecture currentLecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_page);
        moreButton = (Button) findViewById(R.id.moreButton);
        imageSlot1 = (ImageButton) findViewById(R.id.imageSlot1);
        imageSlot2 = (ImageButton) findViewById(R.id.imageSlot2);
        imageSlot3 = (ImageButton) findViewById(R.id.imageSlot3);
        finalUpload = (Button) findViewById(R.id.final_upload);
        xOne = (ImageButton) findViewById(R.id.xone);
        xTwo = (ImageButton) findViewById(R.id.xtwo);
        xThree = (ImageButton) findViewById(R.id.xthree);
        moreButton.setVisibility(View.INVISIBLE);
        imageSlot1.setVisibility(View.INVISIBLE);
        imageSlot2.setVisibility(View.INVISIBLE);
        imageSlot3.setVisibility(View.INVISIBLE);
        finalUpload.setVisibility(View.INVISIBLE);
        xOne.setVisibility(View.INVISIBLE);
        xTwo.setVisibility(View.INVISIBLE);
        xThree.setVisibility(View.INVISIBLE);

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
                int hasReadExternalPerm = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
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

        List<Department> depart = HomeScreen.depart;

        ArrayAdapter adapter = new ArrayAdapter<Department>(this, android.R.layout.simple_spinner_item, depart);
        classSpin.setAdapter(adapter);
        classSpin.setOnItemSelectedListener(this);

        //Need to handle case if no courses added yet
        if(HomeScreen.depart.size() != 0) {
            if(HomeScreen.depart.get(departNumber).courses.size() != 0) {
                if(HomeScreen.depart.get(departNumber).courses.get(courseNumber).professors.size() != 0) {
                    if (HomeScreen.depart.get(departNumber).courses.get(courseNumber).professors.get(profNumber).lectures.size() != 0) {
                        currentLecture = HomeScreen.depart.get(departNumber).courses.get(courseNumber).professors.get(profNumber).lectures.get(lectNumber);
                    }
                }
            }
        }
        finalUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLecture != null) {
                    Toast.makeText(getApplicationContext(),
                            " Expanded bmps: " + bmapArray.size(),
                            Toast.LENGTH_SHORT).show();
                    System.out.println(currentLecture.toString() + "2");

                    currentLecture.addNotes(bmapArray);
                    Note noteBeingAdded = currentLecture.notes.get(currentLecture.notes.size() - 1);
                    HomeScreen.userProfile.myNotes.addToMyNotes(noteBeingAdded);
                    HomeScreen.selectedProfessor = noteBeingAdded.parentLecture.parentProfessor;
                    HomeScreen.selectedLecture = noteBeingAdded.parentLecture;
                    HomeScreen.selectedCourse = noteBeingAdded.parentLecture.parentProfessor.parentCourse;
                    HomeScreen.selectedDepart = noteBeingAdded.parentLecture.parentProfessor.parentCourse.parentDepartment;

                    if(HomeScreen.userProfile.myCourses.contains(noteBeingAdded.parentLecture.parentProfessor.parentCourse)){
                    }
                    else {
                        HomeScreen.userProfile.myCourses.add(noteBeingAdded.parentLecture.parentProfessor.parentCourse);
                    }

                    if(HomeScreen.userProfile.myUploadCourses.contains(noteBeingAdded.parentLecture.parentProfessor.parentCourse)){}
                    else {
                        HomeScreen.userProfile.myUploadCourses.add((noteBeingAdded.parentLecture.parentProfessor.parentCourse));
                    }
                    //Need to save note info to user phone
                    finish();
                }
            }
        });

        // Options for deleting any of the three slots available
        xOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePicture(0);
            }
        });
        xTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePicture(1);
            }
        });
        xThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePicture(2);
            }
        });

        imageSlot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(uploadPage.this, FullScreenPreview.class);
                String bms = bm2s(bmapArray.get(0));
                intent.putExtra("numPic", 0);
                intent.putExtra("bmap", bms);
                startActivityForResult(intent, DISPLAY_PHOTO);
            }
        });
        imageSlot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(uploadPage.this, FullScreenPreview.class);
                String bms = bm2s(bmapArray.get(1));
                intent.putExtra("numPic", 1);
                intent.putExtra("bmap", bms);
                startActivityForResult(intent, DISPLAY_PHOTO);
            }
        });
        imageSlot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(uploadPage.this, FullScreenPreview.class);
                String bms = bm2s(bmapArray.get(2));
                intent.putExtra("numPic", 2);
                intent.putExtra("bmap", bms);
                startActivityForResult(intent, DISPLAY_PHOTO);
            }
        });

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(uploadPage.this, PictureGridView.class);
                String tmp;
                ArrayList<String> stringarr = new ArrayList<String>();
                for (int i = 0; i < bmapArray.size(); i++) {
                    stringarr.add(bm2s(bmapArray.get(i)));
                }
                intent.putStringArrayListExtra("bmaps", stringarr);
                startActivityForResult(intent, GRID_VIEW);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.classSpinner:
                departNumber = position;
                List<Course> course = HomeScreen.depart.get(position).courses;
                ArrayAdapter adapter2 = new ArrayAdapter<Course>(this, android.R.layout.simple_spinner_item, course);
                profSpin.setAdapter(adapter2);
                profSpin.setOnItemSelectedListener(this);
                break;

            case R.id.profSpinner:
                courseNumber = position;
                List<Professor> professor = HomeScreen.depart.get(departNumber).courses.get(position).professors;
                ArrayAdapter adapter3 = new ArrayAdapter<Professor>(this, android.R.layout.simple_spinner_item, professor);
                weekSpin.setAdapter(adapter3);
                weekSpin.setOnItemSelectedListener(this);
                break;
            case R.id.weekSpinner:
                profNumber = position;
                List<Lecture> lecture = HomeScreen.depart.get(departNumber).courses.get(courseNumber).professors.get(position).lectures;
                ArrayAdapter adapter4 = new ArrayAdapter<Lecture>(this, android.R.layout.simple_spinner_item, lecture);
                lecNumSpin.setAdapter(adapter4);
                weekSpin.setOnItemSelectedListener(this);
                break;
            case R.id.lecNumSpinner:
                lectNumber = position;

                break;
        }

    }

    /* Used for returning the values from the picture select, the full screen
     * preview, and the grid view of every photo.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /* Section for picture selection */
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
            String imageSlot = "R.id.imageSlot" + numPictures;
            if (numPictures == 1) {
                imageSlot1.setImageBitmap(bitmap);
                imageSlot1.setVisibility(View.VISIBLE);
                xOne.setVisibility(View.VISIBLE);
            }
            else if (numPictures == 2) {
                imageSlot2.setImageBitmap(bitmap);
                imageSlot2.setVisibility(View.VISIBLE);
                xTwo.setVisibility(View.VISIBLE);
            }
            else if (numPictures == 3) {
                imageSlot3.setImageBitmap(bitmap);
                imageSlot3.setVisibility(View.VISIBLE);
                xThree.setVisibility(View.VISIBLE);
            }
            else {
                int morePictures = numPictures - 3;
                moreButton.setVisibility(View.VISIBLE);
                moreButton.setText(morePictures + " more - click here to view all pictures");
            }
            finalUpload.setVisibility(View.VISIBLE);
            // Close the cursor so we go back to the main page
            cursor.close();
        }

        /* Section for returning values from the full screen picture view */
        else if (requestCode == DISPLAY_PHOTO && resultCode == RESULT_OK && data != null) {
            String delete = data.getStringExtra("delete");
            if (delete != null) {
                if (delete.equals("yes")) {
                    int numToDelete = data.getIntExtra("numToDelete", -1);
                    deletePicture(numToDelete);
                }
            }
        }

        // TODO: section for returning values from the grid view

    }

    private String bm2s(Bitmap bmap) {
        String bmapString = "bmap";//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(bmapString, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            bmapString = null;
        }
        return bmapString;
    }


    /* Private helper method to create a dialog to prevent a note from being uploaded
     * and then delete that picture
     */
    private void deletePicture(final int whichX) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        int toDelete = whichX;
        builder.setTitle("Delete this note");
        builder.setMessage("Are you sure you don't want to upload this note?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                // Deleting from the full screen preview page
                if (whichX >= 100) {
                    finish();
                }
                if (bmapArray.size() == 1) {
                    imageSlot1.setImageBitmap(null);
                    imageSlot1.setVisibility(View.INVISIBLE);
                    xOne.setVisibility(View.INVISIBLE);
                    bmapArray.remove(0);
                    // System.out.println("Removed first picture. Bitmap size is now: " + bmapArray.size());
                } else if (bmapArray.size() == 2) {
                    if (whichX == 0 || whichX == 100) {
                        imageSlot1.setImageBitmap(bmapArray.get(1));
                        bmapArray.set(0, bmapArray.get(1));
                    }
                    imageSlot2.setImageBitmap(null);
                    imageSlot2.setVisibility(View.INVISIBLE);
                    xTwo.setVisibility(View.INVISIBLE);
                    bmapArray.remove(1);
                }

                // otherwise the size is 3 or more, meaning all three spots are taken
                else {
                    if (whichX == 0 || whichX == 100) {
                        imageSlot1.setImageBitmap(bmapArray.get(1));
                        imageSlot2.setImageBitmap(bmapArray.get(2));
                        bmapArray.set(0, bmapArray.get(1));
                        bmapArray.set(1, bmapArray.get(2));
                    }
                    if (whichX == 1 || whichX == 101) {

                        imageSlot2.setImageBitmap(bmapArray.get(2));
                        bmapArray.set(1, bmapArray.get(2));
                    }

                    /* Deal with the third slot */

                    // if size is greater than 3, then we have more pictures that we can pull from
                    if (whichX == 2 || whichX == 102) {
                        if (bmapArray.size() > 3) {
                            imageSlot3.setImageBitmap(bmapArray.get(3));
                            bmapArray.set(2, bmapArray.get(3));
                            bmapArray.remove(bmapArray.size() - 1);
                            if (bmapArray.size() < 4) {
                                moreButton.setVisibility(View.INVISIBLE);
                            }
                        } else {
                            imageSlot3.setImageBitmap(null);
                            imageSlot3.setVisibility(View.INVISIBLE);
                            xThree.setVisibility(View.INVISIBLE);
                            bmapArray.remove(2);
                        }
                    }
                }
                numPictures--;
            }

        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Don't delete anything
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
