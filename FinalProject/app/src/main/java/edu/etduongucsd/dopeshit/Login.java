package edu.etduongucsd.dopeshit;

import android.widget.Toast;
import android.app.Activity;


/**
 * Created on 3/6/2016.
 */
public class Login extends Activity{

    String emailInput;

    String checkEmail;

    String checkName;

    public Login (){
    }

    public boolean checkName(String loginName) {

        for (int i = 0; i < loginName.length(); i++) {
            if (loginName.charAt(i) == '@') {
                checkEmail = loginName.substring(i);
                checkName = loginName.substring(0, i);
            }
        }

        if (checkEmail == null || checkName == null) {
            return false;
        }

        if (!checkEmail.equals("@ucsd.edu")) {
            return false;
        }


        if (StartingPoint.users != null) {
            for (String c : StartingPoint.users) {

                if (c.equals(checkName)) {
                    System.out.println("Found existing user");
                    StartingPoint.myProfile = new UserProfile(c);
                    StartingPoint.preferenceEditor.remove("lastUser");
                    StartingPoint.preferenceEditor.commit();
                    StartingPoint.preferenceEditor.putString("lastUser", checkName+"@ucsd.edu");
                    StartingPoint.preferenceEditor.commit();
                    return true;
                }

            }
        }

        // create a new profile
        System.out.println("Creating new user");
        StartingPoint.myProfile = new UserProfile(checkName);
        StartingPoint.users.add(checkName);
        StartingPoint.preferenceEditor.putStringSet("Users", StartingPoint.users);
        StartingPoint.preferenceEditor.commit();

        StartingPoint.preferenceEditor.remove("lastUser");
        StartingPoint.preferenceEditor.commit();
        StartingPoint.preferenceEditor.putString("lastUser", checkName+"@ucsd.edu");
        StartingPoint.preferenceEditor.commit();

        return true;
    }
}
