package team14.project110;

import java.lang.Override;
import java.util.Scanner;

/**
 * Created on 2/25/2016.
 */
public class Login {
    /* text input on login page
    EditText loginInput = (EditText) findViewById(R.id.editLogin);
    loginInput.setOnEditorActionListener (new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction (TextView textView, int i, KeyEvent keyEvent) {
            boolean handled = false;
            if (i == EditorInfor.IME_ACTION_NEXT) {
                // show toast for input
                String inputText = textView.getText().toString();
                Toast.makeText(MainActivity.this, "Your login is:  "
                        + inputText, Toast.LENGTH_SHORT).show();
            }
            return handled;
        }
    });
    */
    // used to read profiles from txt
    private WordScanner wordScanner;
    // used to write new profiles to txt
    private FileWriter fileWriter;
    // file name to write and read from
    public static final String path = "profiles.txt";
    //Scanner scanner = new Scanner(System.in);  	// change to word scanner
//    String loginName;
    String name;
    String email;
    list<UserProfile> users;
    list<Course> courses;
    /*Global*/ UserProfile currUser;

    public Login (List<Course> c) {
        courses = c;
    


    wordScanner = new wordScanner (this, courses);
    wordScanner.parseProfile (path);
}
    users = wordScanner.profiles

    public boolean getNames(String loginName) {

        for (int i = 0; i < loginName.length(); i++) {
            if (loginName.charAt(i) ==‘@’){
                email = loginName.substring(i);
                name = loginName.substring(0, i);
            }
        }

        if (!email.equals(“@ucsd.edu”)){
            return false;
        }


        for (UserProfile p : users) {
            if (p.name.equals(name)) {
                currUser = p;
                return true;
                //start the app under current user
            }
        }
        // create a new profile
        UserProfile newUser = new UserProfile(name);
        users.add(newUser);

		currUser = newUser;

        // adds the new profile to the txt
        fileWriter = new FileWriter(this);
        fileWriter.writeProfile(newUser, false);

        return true;
    }




    //add to text file
   //start the front end under new user


}
