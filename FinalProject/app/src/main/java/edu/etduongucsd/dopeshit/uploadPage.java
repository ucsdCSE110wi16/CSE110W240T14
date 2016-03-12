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
import android.content.res.Resources;
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
import android.util.Base64;
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
    Lecture currentLecture = null;

    private ArrayList<Bitmap> tempBmpArray = new ArrayList<Bitmap>();
    int wPixel;
    int hPixel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_upload_page);

        wPixel = this.getWindowManager().getDefaultDisplay().getWidth()/6;
        hPixel = this.getWindowManager().getDefaultDisplay().getHeight()/6;

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
                finish();
                Intent intent = new Intent(uploadPage.this, HomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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

        checkIfValid();

        finalUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLecture != null) {
                    Toast.makeText(getApplicationContext(),
                            " Expanded bmps: " + bmapArray.size(),
                            Toast.LENGTH_SHORT).show();

                    currentLecture.addNotes(bmapArray);
                    for (Bitmap bmp : bmapArray) {
                        bmp.recycle();
                        bmp = null;
                    }
                    Note noteBeingAdded = currentLecture.notes.get(currentLecture.notes.size() - 1);
                    HomeScreen.userProfile.myNotes.addToMyNotes(noteBeingAdded);
                    HomeScreen.selectedProfessor = noteBeingAdded.parentLecture.parentProfessor;
                    HomeScreen.selectedLecture = noteBeingAdded.parentLecture;
                    HomeScreen.selectedCourse = noteBeingAdded.parentLecture.parentProfessor.parentCourse;
                    HomeScreen.selectedDepart = noteBeingAdded.parentLecture.parentProfessor.parentCourse.parentDepartment;

                    boolean containsProf = false;
                    for (Professor professor : HomeScreen.userProfile.myCourses) {
                        if (professor.name.equals(noteBeingAdded.parentLecture.parentProfessor.name)) {
                            containsProf = true;
                        }
                    }

                    if (containsProf == true) {
                    } else {
                        Professor c = noteBeingAdded.parentLecture.parentProfessor;
                        HomeScreen.userProfile.myCourses.add(c);
                        StartingPoint.myCourses.add(c.dataBaseRef + c.name + "/");
                        StartingPoint.preferenceEditor.remove((StartingPoint.myProfile.name + "myCourses"));
                        StartingPoint.preferenceEditor.commit();
                        StartingPoint.preferenceEditor.putStringSet((StartingPoint.myProfile.name + "myCourses"), StartingPoint.myCourses);
                        StartingPoint.preferenceEditor.commit();
                    }
/*
                    boolean containsNote = false;

                    for(Note note : HomeScreen.userProfile.userUpNotes){
                        if((note.dataBaseRef+"Note " + note.noteNum+"/").equals(noteBeingAdded.dataBaseRef+"Note " + noteBeingAdded.noteNum+"/")){
                            containsNote = true;

                        }
                    }
                    if(containsNote == false){*/

                    HomeScreen.userProfile.userUpNotes.add(noteBeingAdded);
                    System.out.println("PROFESSOR ALREADY ADDED: " + HomeScreen.userProfile.userUpNotes.size());
                    StartingPoint.myNotes.add(noteBeingAdded.dataBaseRef + "Note " + noteBeingAdded.noteNum + "/");
                    StartingPoint.preferenceEditor.remove((StartingPoint.myProfile.name + "myNotes"));
                    StartingPoint.preferenceEditor.commit();
                    StartingPoint.preferenceEditor.putStringSet((StartingPoint.myProfile.name + "myNotes"), StartingPoint.myNotes);
                    StartingPoint.preferenceEditor.commit();

                    for (Department department : HomeScreen.depart) {
                        for (Course course : department.courses) {
                            for (Professor professor : course.professors) {
                                for (Lecture lecture : professor.lectures) {
                                    for (Note note : lecture.notes) {
                                        if ((noteBeingAdded.dataBaseRef + "Note " + noteBeingAdded.noteNum + "/").equals(note.dataBaseRef + "Note " + note.noteNum + "/")) {
                                            HomeScreen.userProfile.userUpNotes.add(noteBeingAdded);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    //  }
                    //Need to save note info to user phone
                /*    for(Bitmap bmp : bmapArray){
                        bmp.recycle();
                        bmp = null;
                    }*/
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
                String bms = bm2s(bmapArray.get(0), 0);
                intent.putExtra("numPic", 0);
                intent.putExtra("bmap", bms);
                startActivityForResult(intent, DISPLAY_PHOTO);
            }
        });
        imageSlot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(uploadPage.this, FullScreenPreview.class);
                String bms = bm2s(bmapArray.get(1), 1);
                intent.putExtra("numPic", 1);
                intent.putExtra("bmap", bms);
                startActivityForResult(intent, DISPLAY_PHOTO);
            }
        });
        imageSlot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(uploadPage.this, FullScreenPreview.class);
                String bms = bm2s(bmapArray.get(2), 2);
                intent.putExtra("numPic", 2);
                intent.putExtra("bmap", bms);
                startActivityForResult(intent, DISPLAY_PHOTO);
            }
        });

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(uploadPage.this, PictureGridView.class);
                ArrayList<String> stringarr = new ArrayList<>();
                int picCount;
                for (picCount = 0; picCount < bmapArray.size(); picCount++) {
                    intent.putExtra("picture" + picCount + ".png", bm2s(bmapArray.get(picCount), picCount));
                    // stringarr.add(bm2s(bmapArray.get(i)));
                }
                intent.putExtra("numPics", bmapArray.size());
                // intent.putExtra("bmaps", stringarr);
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
                List<String> lectureDates = new ArrayList<>();
                for(Lecture currLect : lecture){
                    lectureDates.add(currLect.date);
                }
                ArrayAdapter adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lectureDates);
                lecNumSpin.setAdapter(adapter4);
                lecNumSpin.setOnItemSelectedListener(this);
                break;
            case R.id.lecNumSpinner:
                lectNumber = position;
                checkIfValid();
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


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

         //   bitmap.recycle();
            //     bitmap = null;
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String imgString = Base64.encodeToString(byteArray, Base64.DEFAULT);
           // Bitmap smallerBitmap = decodeSampledBitmapFromResource(byteArray, 1280, 960);


            bmapArray.add(bitmap);
            numPictures++;
            tempBmpArray.add(storeBitmap(wPixel, hPixel, bitmap));
            String imageSlot = "R.id.imageSlot" + numPictures;
            if (numPictures == 1) {
                imageSlot1.setImageBitmap(tempBmpArray.get(0));
                imageSlot1.setVisibility(View.VISIBLE);
                xOne.setVisibility(View.VISIBLE);
            }
            else if (numPictures == 2) {
                imageSlot2.setImageBitmap(tempBmpArray.get(1));
                imageSlot2.setVisibility(View.VISIBLE);
                xTwo.setVisibility(View.VISIBLE);
            }
            else if (numPictures == 3) {
                imageSlot3.setImageBitmap(tempBmpArray.get(2));
                imageSlot3.setVisibility(View.VISIBLE);
                xThree.setVisibility(View.VISIBLE);
            }
            else if (numPictures > 3){
                int morePictures = numPictures - 3;
                moreButton.setVisibility(View.VISIBLE);
                moreButton.setText(morePictures + " more - click here to view all notes");
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
                    int numToDelete = data.getIntExtra("picToDelete", -1);
                    deletePicture(numToDelete);
                }
            }
        }

        else if (requestCode == GRID_VIEW && resultCode == RESULT_OK && data != null) {
            int deleted = data.getIntExtra("deleted", -1);
            if (deleted != -1) {
                deletePicture(deleted);
            }
        }

    }

    /* Convert any bitmap into a string in order to send it across intents
     * because the limit for any intent file is only 1MB
     */
    private String bm2s( Bitmap bmap, int where) {

        String bmapString = "picture" + where + ".png";
        try {
            FileOutputStream fos = this.openFileOutput(bmapString, Context.MODE_PRIVATE);
            bmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            // bmap.recycle();
        }
        catch (Exception e) {
            e.printStackTrace();
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

                int toRemove = whichX;
                if (whichX > 99) {
                    toRemove = whichX - 100;
                }
                // If size is one, there is only one picture and we can delete it
                if (bmapArray.size() == 1) {
                    imageSlot1.setImageBitmap(null);
                    imageSlot1.setVisibility(View.INVISIBLE);
                    xOne.setVisibility(View.INVISIBLE);
                }
                // If size is two
                if (bmapArray.size() == 2) {
                    if (whichX == 0 || whichX == 100) {
                        imageSlot1.setImageBitmap(bmapArray.get(1));
                    }
                    imageSlot2.setImageBitmap(null);
                    imageSlot2.setVisibility(View.INVISIBLE);
                    xTwo.setVisibility(View.INVISIBLE);
                }
                // if size is three
                if (bmapArray.size() == 3) {
                    if (whichX == 0 || whichX == 100) {
                        imageSlot1.setImageBitmap(bmapArray.get(1));
                        imageSlot2.setImageBitmap(bmapArray.get(2));
                    } else if (whichX == 1 || whichX == 101) {
                        imageSlot2.setImageBitmap(bmapArray.get(2));
                    }
                    imageSlot3.setImageBitmap(null);
                    imageSlot3.setVisibility(View.INVISIBLE);
                    xThree.setVisibility(View.INVISIBLE);
                }

                // otherwise the size is more than 3
                // a whichX of >= 100 means that the delete call came from the full screen page
                if (bmapArray.size() > 3){
                    if (whichX == 0 || whichX == 100) {
                        imageSlot1.setImageBitmap(bmapArray.get(1));
                        imageSlot2.setImageBitmap(bmapArray.get(2));
                        imageSlot3.setImageBitmap(bmapArray.get(3));
                    } else if (whichX == 1 || whichX == 101) {
                        imageSlot2.setImageBitmap(bmapArray.get(2));
                        imageSlot3.setImageBitmap(bmapArray.get(3));
                    } else if (whichX == 2 || whichX == 102) {
                        imageSlot3.setImageBitmap(bmapArray.get(3));
                    }
                }

                tempBmpArray.remove(toRemove);
                bmapArray.remove(toRemove);
                numPictures--;
                if (bmapArray.size() > 3) {
                    moreButton.setVisibility(View.VISIBLE);
                    int morePictures = numPictures - 3;
                    moreButton.setText(morePictures + " more - click here to view all notes");
                }
                else {
                    moreButton.setVisibility(View.INVISIBLE);
                }
                // TODO delete this after done debugging and delete works correctly

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

    public void checkIfValid(){
        if(HomeScreen.depart.size() != 0) {
            if(HomeScreen.depart.get(departNumber).courses.size() != 0) {
                if(HomeScreen.depart.get(departNumber).courses.get(courseNumber).professors.size() != 0) {
                    if (HomeScreen.depart.get(departNumber).courses.get(courseNumber).professors.get(profNumber).lectures.size() != 0) {
                        currentLecture = HomeScreen.depart.get(departNumber).courses.get(courseNumber).professors.get(profNumber).lectures.get(lectNumber);
                    }
                }
            }
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(byte[] data,int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public Bitmap bitmapForFirstThumbnail(int reqWidth, int reqHeight, String currBitmap) {
        //get number of pictures
        Bitmap bmp;
        String base64Image = currBitmap;
        byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
        bmp = decodeSampledBitmapFromResource(imageAsBytes,reqWidth ,reqHeight);
        return bmp;
    }

    public Bitmap storeBitmap(int reqWidth, int reqHeight, Bitmap bmp){
        ByteArrayOutputStream bYte = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, bYte);
        byte[] byteArray = bYte.toByteArray();
        String result = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return bitmapForFirstThumbnail(reqWidth, reqHeight, result);
    }
}
