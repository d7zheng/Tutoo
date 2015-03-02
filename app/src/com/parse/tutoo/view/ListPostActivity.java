package com.parse.tutoo.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.parse.tutoo.R;
import com.parse.tutoo.model.Category;
import com.parse.tutoo.model.Post;
import com.parse.tutoo.util.Dispatcher;
import com.parse.tutoo.util.MenuListAdapter;

import java.util.Vector;

/**
 * Created by hilary on 25/02/2015.
 */
public class ListPostActivity extends ActionBarActivity {
    ListView listView;
    Vector<Post> posts = new Vector<Post>();
    Context context;
    MenuListAdapter myAdapter;
    Dispatcher dispatcher = new Dispatcher();

    private void initData() {
        for (int i = 0; i < 5; i++) {
            Post post = new Post();
            if (i == 0) {
                post.setAttributes("Looking for Math Tutor", "My name is Jessie, I'm looking for a math tutor for Grade 5 Math", i, Category.MATH);
            } else if (i == 1) {
                post.setAttributes("Seek tutor for 2 hours", "My name is Emily, seeking 2 hours tutor for French",i, Category.OTHERS);
            } else if (i == 2) {
                post.setAttributes("Piano Tutor!", "My name is Danny, I'm looking for a math tutor for Grade 5", i,  Category.MATH);
            } else if (i == 3) {
                post.setAttributes("Seek Help: CS446", "My name is Hilary, uwaterloo software articecture course, looking for tutor for Kitchener-Waterloo area", i, Category.OTHERS);
            } else if (i == 4) {
                post.setAttributes("Seek Help: MATH135", "looking for a math tutor for calculus", i, Category.MATH);
            }
            posts.add(post);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list_view);
        context = getApplicationContext();

        /*ImageButton newPostButton = (ImageButton) findViewById(R.id.newPostButton);
        newPostButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // show list
                Intent intent = new Intent(context, CreatePostActivity.class);
                startActivity(intent);
            }
        });*/

        listView = (ListView) findViewById(R.id.list);

        //listView.setOnScrollListener(new ListViewListener());

        initData();
        myAdapter = new MenuListAdapter(posts, this);
        listView.setAdapter(myAdapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(context, ViewPostActivity.class);
                //i.putExtra("name_of_extra", myObject);
                intent.putExtra("post_id", String.valueOf(position));
                startActivity(intent);
                /*
                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();\
                        */

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_posts, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_newpost:
                dispatcher.openNewPost(getApplicationContext(), this);
                return true;
            case R.id.action_search:
                dispatcher.openSearch(getApplicationContext(),this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
