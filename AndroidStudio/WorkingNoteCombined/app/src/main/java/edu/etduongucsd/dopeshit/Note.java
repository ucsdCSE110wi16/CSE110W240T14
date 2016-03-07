package edu.etduongucsd.dopeshit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by steven on 2/10/2016.
 */
//Note object
public class Note {
    static int upvote;
    static int flag;       //Flags a set of notes has received
    int noteNum;
    public Lecture parentLecture;
    String dataBaseRef;
    ArrayList<Bitmap> pictureNote;
    ArrayList<String> pictureString = new ArrayList<String>();

    public Note(){};

    //Notes constructor
    public Note(ArrayList<Bitmap> bmp, int numberOfNotes, String parentFireBaseRef) {
        upvote = 0;
        flag = 0;
        pictureNote = bmp;
        noteNum = numberOfNotes;
        dataBaseRef = parentFireBaseRef;
    }

    public Note(int numberOfNotes, String parentFireBaseRef){
        upvote = 0;
        flag = 0;
        pictureNote = new ArrayList<Bitmap>();
        noteNum = numberOfNotes;
        dataBaseRef = parentFireBaseRef;
    }


    public int getFlag(){
        return flag;
    }

    public int getUpvote() {return upvote;}

    @Override
    public String toString(){
        return "Note "+noteNum;
    }

    public ArrayList<Bitmap> getNoteImage(){
        return pictureNote;
    }

    public int getNoteNum(){
        return noteNum;
    }

    public void addNoteToFirebase(){
        Firebase ref = new Firebase(dataBaseRef);
        int arraySize = pictureNote.size();
        ref.child("Note "+noteNum).setValue(0);
        //ref.child("Note " + noteNum).child("NumOfPics").setValue(arraySize);
        for(int i = 0; i < arraySize; i++){
            String stringImage = storeBitmap(pictureNote.get(i));
            ref.child("Note " + noteNum).child("Pic "+(i+1)).setValue(stringImage);
            pictureString.add(stringImage);
        }
    }

    public static final Comparator<Note> ASC_NOTES = new Comparator<Note>() {
        @Override
        public int compare(Note lhs, Note rhs) {

            int lhsVotes = lhs.getUpvote();
            int rhsVotes = rhs.getUpvote();

            if(lhsVotes != rhsVotes) {
                return lhsVotes - rhsVotes;
            }
            else {
                return lhs.noteNum - rhs.noteNum;
            }
        }
    };

    public void incUpvote() {
        upvote++;
    }

    public void incFlag() {
        flag++;
    }

    public String storeBitmap(Bitmap bmp){
        ByteArrayOutputStream bYte = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, bYte);
        byte[] byteArray = bYte.toByteArray();
        String result = Base64.encodeToString(byteArray, Base64.DEFAULT);
        System.out.println("Stored image with length: " + byteArray.length);
        return result;
    }

    public ArrayList<Bitmap> convertToNoteBitmap(int reqWidth, int reqHeight) {
        //get number of pictures
        System.out.println("Number of pictures: ");
        for(int i = 0; i < pictureString.size(); i++) {
            String base64Image = pictureString.get(i);
            byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
            pictureNote.add(decodeSampledBitmapFromResource(imageAsBytes,reqWidth ,reqHeight));
        }
        return pictureNote;
    }

    //Toggle Flag
    public void incrFlag() {
        if( flag == 1 ) {
            flag = 0;
        }
        else {
            flag = 1;
        }
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

    public Bitmap decodeSampledBitmapFromResource(byte[] data,int reqWidth, int reqHeight) {

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
}
