package com.parse.tutoo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.parse.ParseUser;
import com.parse.tutoo.R;
import com.parse.ui.ParseLoginBuilder;

import java.util.Arrays;

/**
 * Login Activity
 */
public class LoginActivity extends Activity {
    private static final int LOGIN_REQUEST = 0;

    private TextView titleTextView;
    private TextView emailTextView;
    private TextView nameTextView;
    private Button loginOrLogoutButton;

    private ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        titleTextView = (TextView) findViewById(R.id.profile_title);
        emailTextView = (TextView) findViewById(R.id.profile_email);
        nameTextView = (TextView) findViewById(R.id.profile_name);
        loginOrLogoutButton = (Button) findViewById(R.id.login_or_logout_button);
        titleTextView.setText(R.string.profile_title_logged_in);

        loginOrLogoutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    // User clicked to log out.
                    ParseUser.logOut();
                    currentUser = null;
                    showProfileLoggedOut();
                } else {
                    // User clicked to log in.
                    ParseLoginBuilder builder = new ParseLoginBuilder(
                            LoginActivity.this);
                    Intent loginIntent = builder.setParseLoginEnabled(true)
                            .setParseLoginButtonText("Go")
                            .setParseSignupButtonText("Register")
                            .setParseLoginHelpText("Forgot password?")
                            .setParseLoginInvalidCredentialsToastText("You email and/or password is not correct")
                            .setParseLoginEmailAsUsername(true)
                            .setParseSignupSubmitButtonText("Submit registration")
                            .setFacebookLoginEnabled(true)
                            .setFacebookLoginButtonText("Facebook")
                            .setFacebookLoginPermissions(Arrays.asList("public_profile","user_status"))
                            .build();
                    startActivityForResult(loginIntent, LOGIN_REQUEST);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            showProfileLoggedIn();
        } else {
            showProfileDefault();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Intent loggedinIntent = new Intent(LoginActivity.this, StarterActivity.class);
            startActivity(loggedinIntent);
        }
    }

    /**
     * Shows the profile of the given user.
     */
    private void showProfileLoggedIn() {
        titleTextView.setText(R.string.profile_title_logged_in);
        emailTextView.setText(currentUser.getEmail());
        String fullName = currentUser.getString("name");
        if (fullName != null) {
            nameTextView.setText(fullName);
        }
        loginOrLogoutButton.setText(R.string.profile_logout_button_label);
    }

    /**
     * Show a message asking the user to log in, toggle login/logout button text.
     */
    private void showProfileLoggedOut() {
        titleTextView.setText(R.string.profile_title_logged_out);
        emailTextView.setText("");
        nameTextView.setText("");
        loginOrLogoutButton.setText(R.string.profile_login_button_label);
    }

    /**
     * Show a default screen.
     */
    private void showProfileDefault() {
        titleTextView.setText(R.string.profile_title_not_logged_in);
        emailTextView.setText("");
        nameTextView.setText("");
        loginOrLogoutButton.setText(R.string.profile_login_button_label);
    }
}
