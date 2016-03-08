package edu.etduongucsd.dopeshit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

/**
 * Created on 3/7/2016.
 */
public class StartingPoint extends AppCompatActivity {

    public static UserProfile myProfile;

    Button loginButton;

    EditText emailText;

    String emailInput;

    String checkEmail;

    String checkName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Firebase.setAndroidContext(this);

       // Login login = new Login();

        loginButton = (Button) findViewById(R.id.loginButton);

        emailText = (EditText)findViewById(R.id.email);

        loginButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        //checkEmail();
                    }
                }


        );


    }
}
