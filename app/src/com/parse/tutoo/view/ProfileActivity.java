package com.parse.tutoo.view;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.tutoo.R;
import com.parse.tutoo.model.Post;
import com.parse.tutoo.util.Dispatcher;


public class ProfileActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ParseUser curUser = ParseUser.getCurrentUser();
        TextView nameTV = (TextView) findViewById(R.id.textView2);
        nameTV.setTextSize(30);
        nameTV.setText(curUser.getString("name"));

        TextView emailTV = (TextView) findViewById(R.id.textView3);
        emailTV.setText(curUser.getString("email"));

        TextView locationTV = (TextView) findViewById(R.id.textView);
        locationTV.setText("Lives in Waterloo, Canada");

        TextView skills = (TextView) findViewById(R.id.skillsets);
        skills.setText("algorithms, math138, cs245, algebra");

        RatingBar rating = (RatingBar) findViewById(R.id.rating);
        rating.setRating(5);
        // Replace this with number of skills later
        int size = 1; // total number of TextViews to add

      /*  TextView[] tv = new TextView[size];
        TextView temp;

        for (int i = 0; i < size; i++)
        {
            temp = new TextView(this);
            // Replace this with actual skills later
            temp.setText("algorithms, math138, cs245, algebra");
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
            linearLayout.addView(temp);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,10,20,10);
            temp.setTextSize(30);
            temp.setLayoutParams(params);
            //temp.setBackgroundColor(Color.parseColor("CCFFCC"));
            tv[i] = temp;
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        switch (id) {
            case R.id.action_newpost:
                Dispatcher.openNewPost(getApplicationContext(), this);
                return true;
            case R.id.action_search:
                Dispatcher.openSearch(getApplicationContext(),this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
