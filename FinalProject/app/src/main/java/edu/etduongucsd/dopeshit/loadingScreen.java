package edu.etduongucsd.dopeshit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import android.os.Handler;
/**
 * Created on 3/9/2016.
 */
public class loadingScreen extends Activity{

    private static int LOADING_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(loadingScreen.this, HomeScreen.class));
                finish();
            }
        }, LOADING_TIME);


    }
}
