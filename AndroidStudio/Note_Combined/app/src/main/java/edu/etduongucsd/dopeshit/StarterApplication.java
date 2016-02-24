package edu.etduongucsd.dopeshit;

import com.firebase.client.Firebase;

/**
 * Created on 2/24/2016.
 */
public class StarterApplication extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
