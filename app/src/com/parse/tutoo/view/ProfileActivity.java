package com.parse.tutoo.view;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.parse.tutoo.R;


public class ProfileActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView nameTV = (TextView) findViewById(R.id.textView2);
        nameTV.setTextSize(30);
        nameTV.setText("Name");

        TextView emailTV = (TextView) findViewById(R.id.textView3);
        emailTV.setTextSize(30);
        emailTV.setText("tutor@tutoo.com");

        TextView locationTV = (TextView) findViewById(R.id.textView);
        locationTV.setTextSize(30);
        locationTV.setText("Canada");



        // Replace this with number of skills later
        int size = 10; // total number of TextViews to add

        TextView[] tv = new TextView[size];
        TextView temp;

        for (int i = 0; i < size; i++)
        {
            temp = new TextView(this);
            // Replace this with actual skills later
            temp.setText("Skill " + i);
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
            linearLayout.addView(temp);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,10,20,10);
            temp.setTextSize(30);
            temp.setLayoutParams(params);
            //temp.setBackgroundColor(Color.parseColor("CCFFCC"));
            tv[i] = temp;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
