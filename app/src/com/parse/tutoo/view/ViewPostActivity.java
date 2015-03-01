package com.parse.tutoo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.parse.tutoo.R;

/**
 * Created by hilary on 25/02/2015.
 */
public class ViewPostActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_post);

        Intent intent = getIntent();

        TextView textView = (TextView) findViewById(R.id.viewpost1);
        textView.setText(intent.getStringExtra("post_id"));
    }
}
