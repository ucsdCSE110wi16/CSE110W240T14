package edu.etduongucsd.dopeshit;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 3/6/2016.
 */
public class Login extends AppCompatActivity {

    public static UserProfile myProfile;

    Button loginButton;

    EditText emailText;

    String emailInput;

    String checkEmail;

    String checkName;


    //to have unique lists of strings name them user.toString()+nameoflist
    public void checkEmail(){
        emailInput = emailText.getText().toString();


        //Check shared pref list of emails
        //if not found create new one
        myProfile = new UserProfile(emailInput);
        //Set up lists here for UserProfile
        //My Courses
        //My Notes
        //Notes liked
        //Notes flagged
        //myProfile.myCourses = ;
        //myProfile.myFlags = ;
        //myProfile.myUpvotes = ;
        //myProfile.myUploadCourses = ;
        //myProfile.userUpNotes = ;

    }

    public boolean getNames(String loginName) {

        for (int i = 0; i < loginName.length(); i++) {
            if (loginName.charAt(i) =='@'){
                checkEmail = loginName.substring(i);
                checkName = loginName.substring(0, i);
            }
        }

        if (!checkEmail.equals("@ucsd.edu")){
            return false;
        }

//Iterate through comparing name to existing user names, then build courses
       // for (UserProfile p : users) {
          //  if (p.name.equals(checkName)) {
            //    myProfile = p;
              //  return true;
                //start the app under current user
            //}
        //}
        // create a new profile
        UserProfile newUser = new UserProfile(checkName);
        //Add to chared preferences //users.add(newUser);

        myProfile = newUser;

        // adds the new profile to the txt
   //     fileWriter = new FileWriter(this);
     //   fileWriter.writeProfile(newUser, false);

        return true;
    }
}
