package edu.etduongucsd.dopeshit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileNotFoundException;

/**
 * Created by Collin on 3/6/2016.
 */
public class FullScreenPreview extends AppCompatActivity {

    ImageView IMG;
    Matrix oldMatrix;
    Matrix newMatrix;
    PointF startPt;
    PointF midPt;
    float startingDist = 1f;
    static final int NORMAL = 0;
    static final int DRAGGING = 1;
    static final int ZOOMING = 2;
    int mode = NORMAL;
    Context context;

    Button backButton;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.full_image_layout);

        // Initialize matrices and points for the pinch and zoom
        oldMatrix = new Matrix();
        newMatrix = new Matrix();
        startPt= new PointF();
        midPt = new PointF();
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

        // Make sure that our delete button is only visible when we are initially
        // uploading, because we have a dedicated delete button after we have uploaded
        // a picture
        if (intent.getBooleanExtra("nodelete", false) == true) {
            deleteButton.setVisibility(View.INVISIBLE);
        }
        backButton = (Button) findViewById(R.id.backButton);
        IMG = (ImageView) findViewById(R.id.insertPicture);

        // Resize our bitmap to the size of the screen (can be resized using
        // pinch and zoom, though)
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int newWidth = size.x;
        double oldWidth = (double) bmap.getWidth();
        double oldHeight = (double) bmap.getHeight();
        double scaleFactor = newWidth / oldWidth;
        if (scaleFactor < 1) {
            scaleFactor = oldWidth / newWidth;
        }
        int newHeight = (int) (scaleFactor * oldHeight);
        Bitmap bmapResized = Bitmap.createScaledBitmap(bmap, newWidth, newHeight, false);
        IMG.setImageBitmap(bmapResized);

        // Create an on touch listener so we can change the picture's orientation by
        // pinching, zooming, and dragging around
        IMG.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ImageView view = (ImageView) v;
                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    // Case when we are moving the image around with one finger
                    case MotionEvent.ACTION_DOWN:
                        newMatrix.set(oldMatrix);
                        startPt.set(event.getX(), event.getY());
                        mode = DRAGGING;
                        break;

                    // Case for when we are zooming in with two finger pinch
                    case MotionEvent.ACTION_POINTER_DOWN:
                        startingDist = findNewSpace(event);
                        if (startingDist > 10f) {
                            newMatrix.set(oldMatrix);
                            findMidPt(midPt, event);
                            mode = ZOOMING;
                        }
                        break;

                    case MotionEvent.ACTION_UP:

                    // Case to reset
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NORMAL;
                        break;

                    // Any time we move the image around, we have to reset the matrix
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAGGING) {
                            oldMatrix.set(newMatrix);
                            oldMatrix.postTranslate(event.getX() - startPt.x, event.getY() - startPt.y);
                        } else if (mode == ZOOMING) {
                            float newDist = findNewSpace(event);
                            if (newDist > 10f) {
                                oldMatrix.set(newMatrix);
                                float scale = newDist / startingDist;
                                oldMatrix.postScale(scale, scale, midPt.x, midPt.y);
                            }
                        }
                        break;
                }

                // Make sure that we have the correct view of the right matrix when we've finished
                view.setImageMatrix(oldMatrix);
                return true;
            }

            // Private helper method to find the new space of the float
            private float findNewSpace(MotionEvent event) {
                float x = event.getX(0) - event.getX(1);
                float y = event.getY(0) - event.getY(1);
                return (float) Math.sqrt(x * x + y * y);
            }

            // Find the midpoint of two floats
            private void findMidPt(PointF pt, MotionEvent event) {
                float x = event.getX(0) + event.getX(1);
                float y = event.getY(0) + event.getY(1);
                pt.set(x/2, y/2);
            }
        });

        // On click listener for our delete button, which is only visible when
        // uploading a picture for the first time
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

        // The back button listener which will return the user to the previous screen
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });

        // google api bs
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "FullScreenPreview Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://edu.etduongucsd.dopeshit/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "FullScreenPreview Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://edu.etduongucsd.dopeshit/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

}
