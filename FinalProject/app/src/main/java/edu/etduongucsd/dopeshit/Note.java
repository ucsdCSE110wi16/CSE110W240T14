package edu.etduongucsd.dopeshit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by steven on 2/10/2016.
 */
//Note object
public class Note {
    public int upvote;
    public int flag;       //Flags a set of notes has received
    public boolean upvoteBool;
    public boolean flagBool;
    int noteNum;
    public Lecture parentLecture;
    String dataBaseRef;
    ArrayList<Bitmap> pictureNote;
    ArrayList<String> pictureString = new ArrayList<>();
    Boolean removed = false;

    public Note(){};

    //Notes constructor
    public Note(ArrayList<Bitmap> bmp, int numberOfNotes, String parentFireBaseRef) {
        upvote = 0;
        flag = 0;
        upvoteBool = false;
        flagBool = false;
        pictureNote = bmp;
        noteNum = numberOfNotes;
        dataBaseRef = parentFireBaseRef;
        for(int i = 0; i < pictureNote.size(); i++){
            String stringImage = storeBitmap(pictureNote.get(i));
            pictureString.add(stringImage);
        }
        pictureNote = null;
    }

    public Note(int numberOfNotes, String parentFireBaseRef){
        upvote = 0;
        flag = 0;
        upvoteBool = false;
        flagBool = false;
        noteNum = numberOfNotes;
        dataBaseRef = parentFireBaseRef;
    }

    public int getUpvote() {return upvote;}

    @Override
    public String toString(){
        return "Note "+noteNum;
    }

    public void addNoteToFirebase(){
        Firebase ref = new Firebase(dataBaseRef);
        int arraySize = pictureString.size();
        ref.child("Note " + noteNum).setValue(0);
        ref.child("Note " + noteNum).child("Rating").setValue(0);
        ref.child("Note " + noteNum).child("Flags").setValue(0);
        ref.child("Note " + noteNum).child("Removed").setValue(false);
        for(int i = 0; i < arraySize; i++){
            String stringImage = pictureString.get(i);
            ref.child("Note " + noteNum).child("Pic "+(i+1)).setValue(stringImage);
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

    public String storeBitmap(Bitmap bmp){
        ByteArrayOutputStream bYte = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, bYte);
        byte[] byteArray = bYte.toByteArray();
        String result = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return result;
    }

    public ArrayList<Bitmap> convertToNoteBitmap(int reqWidth, int reqHeight) {
        //get number of pictures
        if(pictureNote != null){
            pictureNote = null;
        }
        pictureNote = new ArrayList<Bitmap>();
        for(int i = 0; i < (pictureString.size()); i++) {
            String base64Image = pictureString.get(i);
            byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
            pictureNote.add(decodeSampledBitmapFromResource(imageAsBytes,reqWidth ,reqHeight));
        }
        return pictureNote;
    }

    public Bitmap bitmapForFirstThumbnail(int reqWidth, int reqHeight) {
        //get number of pictures
        Bitmap bmp;
        String base64Image = pictureString.get(0);
        byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
        bmp = decodeSampledBitmapFromResource(imageAsBytes,reqWidth ,reqHeight);
        return bmp;
    }

    //Toggle Flag
    public void toggleFlag() {
        HomeScreen.userProfile.toggleMyFlags(this);

        if( flagBool == true ) {
            flagBool = false;
            decFlag();

            StartingPoint.preferenceEditor.remove((StartingPoint.myProfile.name + "flaggedNotes"));
            StartingPoint.preferenceEditor.commit();
            StartingPoint.preferenceEditor.putStringSet((StartingPoint.myProfile.name + "flaggedNotes"), StartingPoint.flaggedNotes);
            StartingPoint.preferenceEditor.commit();

        }
        else {
            flagBool = true;
            incFlag();
            if( upvoteBool == true && flagBool == true) {
                toggleUpvotes();
            }            //Add note to myFlags list in UserProfile
            HomeScreen.userProfile.myFlags.add(this);
            StartingPoint.flaggedNotes.add(dataBaseRef + "Note " + noteNum + "/");
            StartingPoint.preferenceEditor.remove((StartingPoint.myProfile.name + "flaggedNotes"));
            StartingPoint.preferenceEditor.commit();
            StartingPoint.preferenceEditor.putStringSet((StartingPoint.myProfile.name + "flaggedNotes"), StartingPoint.flaggedNotes);
            StartingPoint.preferenceEditor.commit();
        }
    }

    public void incFlag() {
        flag++;
        Firebase ref = new Firebase(dataBaseRef);
        ref.child("Note " + noteNum).child("Flags").setValue(flag);
    }

    public void decFlag(){
        flag--;
        Firebase ref = new Firebase(dataBaseRef);
        ref.child("Note " + noteNum).child("Flags").setValue(flag);
    }

    public void incUpvote() {
        upvote++;
        Firebase ref = new Firebase(dataBaseRef);
        ref.child("Note " + noteNum).child("Rating").setValue(upvote);
    }

    public void decUpvote(){
        upvote--;
        Firebase ref = new Firebase(dataBaseRef);
        ref.child("Note " + noteNum).child("Rating").setValue(upvote);
    }

    //Toggle Upvotes
    public void toggleUpvotes() {
        HomeScreen.userProfile.toggleMyUpvote(this);

        if( upvoteBool == true ) {
            upvoteBool = false;
            decUpvote();

            StartingPoint.preferenceEditor.remove((StartingPoint.myProfile.name + "likedNotes"));
            StartingPoint.preferenceEditor.commit();
            StartingPoint.preferenceEditor.putStringSet((StartingPoint.myProfile.name + "likedNotes"), StartingPoint.likedNotes);
            StartingPoint.preferenceEditor.commit();
        }
        else {
            upvoteBool = true;
            incUpvote();

            if( flagBool == true && upvoteBool ==true) {
                toggleFlag();
            }
            //Add not to myFlags list in UserProfile
            HomeScreen.userProfile.myUpvotes.add(this);
            StartingPoint.likedNotes.add(dataBaseRef + "Note " + noteNum + "/");
            StartingPoint.preferenceEditor.remove((StartingPoint.myProfile.name + "likedNotes"));
            StartingPoint.preferenceEditor.commit();
            StartingPoint.preferenceEditor.putStringSet((StartingPoint.myProfile.name + "likedNotes"), StartingPoint.likedNotes);
            StartingPoint.preferenceEditor.commit();
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
