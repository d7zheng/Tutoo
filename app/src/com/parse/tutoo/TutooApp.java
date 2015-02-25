package com.parse.tutoo;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.tutoo.model.Post;
import com.parse.tutoo.model.UserProfile;

/**
 * TutooApp
 * Global Application States
 */
public class TutooApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(UserProfile.class);
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));

        String fbappId = getString(R.string.facebook_app_id);
        ParseFacebookUtils.initialize(fbappId);
        //ParseObject testObject = new ParseObject("TestObject");
        //testObject.put("foo", "bar");
        //testObject.saveInBackground();
    }

}
