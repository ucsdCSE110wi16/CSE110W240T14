package edu.etduongucsd.dopeshit;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created on 3/7/2016.
 */
public class FormatPrefs {

    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private static final String MY_UNIQUE_PREFERENCE_FILE = "UserPreferenceFile";

    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditorUnique;

    Set<String> mySet;

   // FormatPref(user.name+nameofset);

    public FormatPrefs(Context context) {
        preferenceSettings = PreferenceManager.getDefaultSharedPreferences(context);
        preferenceEditorUnique = preferenceSettings.edit();
        mySet = new HashSet<String>();
        /*for (Course c : course) {
            mySet.add(c.getDataBaseRef() + c.getName() + "/"); // replace with firebase data
        }*/
    }
    public FormatPrefs(List<UserProfile> users) {
        mySet = new HashSet<String>();
        for (UserProfile u : users) {
            mySet.add (u.name);
        }
    }
    public boolean writeSet(String s) {
      //  preferenceEditer.putStringSet(s, mySet);
   //     preferenceEditor.commit();
        return true;
    }

    public void clrSet() {
        mySet = new HashSet<String>();
    }

}
