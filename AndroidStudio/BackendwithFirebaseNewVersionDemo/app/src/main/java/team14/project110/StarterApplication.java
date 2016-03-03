package team14.project110;

import com.firebase.client.Firebase;

/**
 * Created by 2/20/2016.
 */
public class StarterApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
