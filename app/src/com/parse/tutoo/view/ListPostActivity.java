package com.parse.tutoo.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.tutoo.R;
import com.parse.tutoo.model.Category;
import com.parse.tutoo.model.Post;
import com.parse.tutoo.util.MenuListAdapter;

import java.util.List;
import java.util.Vector;

/**
 * Created by hilary on 25/02/2015.
 */
public class ListPostActivity extends ActionBarActivity {
    ListView listView;
    Vector<Post> posts = new Vector<Post>();
    Context context;
    MenuListAdapter myAdapter;
    /*
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
    */

    private void initData(String category) {


        if (category.equals(Category.All.toString())) {
            for (Category element : Category.values()) {
                ParseQuery query=new ParseQuery("Post");
                query.whereEqualTo("category", element.toString());
                try {
                    List<ParseObject> parseObjects = query.find();
                    for (int i = 0; i < parseObjects.size(); i++) {
                        ParseObject parseObject = parseObjects.get(i);
                        //ParsePost parsePost =new Post(parseObject.getObjectId(), parseObject.getString("title"), "test", parseObject.getString("category"));
                        posts.add(new Post(parseObject.getObjectId(), parseObject.getString("title"), parseObject.getString("desc"), category));
                    }
                }
                catch (  com.parse.ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        ParseQuery query=new ParseQuery("Post");
        query.whereEqualTo("category", category);
        try {
            List<ParseObject> parseObjects = query.find();
            for (int i = 0; i < parseObjects.size(); i++) {
                ParseObject parseObject = parseObjects.get(i);
                //ParsePost parsePost =new Post(parseObject.getObjectId(), parseObject.getString("title"), "test", parseObject.getString("category"));
                posts.add(new Post(parseObject.getObjectId(), parseObject.getString("title"), parseObject.getString("desc"), category));
            }
        }
        catch (  com.parse.ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));
        setContentView(R.layout.main_list_view);
        context = getApplicationContext();

        //ParseObject.registerSubclass(ParsePost.class);
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        initData(category);

        /*
        ImageButton newPostButton = (ImageButton) findViewById(R.id.newPostButton);
        newPostButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // show list
                Intent intent = new Intent(context, CreatePostActivity.class);
                startActivity(intent);
            }
        });
        */


        listView = (ListView) findViewById(R.id.list);

        //listView.setOnScrollListener(new ListViewListener());
        myAdapter = new MenuListAdapter(posts, this);
        listView.setAdapter(myAdapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(context, ViewPostActivity.class);
                //i.putExtra("name_of_extra", myObject);
                Post post = posts.get(position);
                String postId = post.getPostId();
                intent.putExtra("post_id", postId);
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

}
