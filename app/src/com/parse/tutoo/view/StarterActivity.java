package com.parse.tutoo.view;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;
import com.parse.tutoo.R;

public class StarterActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("begin");
        // Check if there is current user info
        if (ParseUser.getCurrentUser() != null) {
            // Start main activity
            startActivity(new Intent(this, MainActivity.class));
        } else {
            // Start login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

}
