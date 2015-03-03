package com.parse.tutoo.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseQuery;
import com.parse.tutoo.R;
import com.parse.tutoo.model.Category;
import com.parse.tutoo.model.Post;
import com.parse.tutoo.util.Dispatcher;
import com.parse.tutoo.util.MenuListAdapter;
import com.parse.tutoo.util.PostListAdapter;

import java.util.List;
import java.util.Vector;

/**
 * Created by hilary on 25/02/2015.
 */
public class ListPostActivity extends ActionBarActivity {
    private Dispatcher dispatcher = new Dispatcher();
    ListView listView;
    Vector<Post> posts = new Vector<Post>();
    Context context;
    PostListAdapter myAdapter;

    private void initData(String category) {


        if (category.equals(Category.All.toString())) {
            for (Category element : Category.values()) {
                ParseQuery query=new ParseQuery("Post");
                query.whereEqualTo("category", element.toString());
                try {
                    List<Post> postObjects = query.find();
                    for (int i = 0; i < postObjects.size(); i++) {
                        //ParseObject parseObject = postObjects.get(i);
                        //ParsePost parsePost =new Post(parseObject.getObjectId(), parseObject.getString("title"), "test", parseObject.getString("category"));
                        //posts.add(new Post(parseObject.getObjectId(), parseObject.getString("title"), parseObject.getString("desc"), category));
                        posts.add(postObjects.get(i));
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
            List<Post> postObjects = query.find();
            for (int i = 0; i < postObjects.size(); i++) {
                //ParsePost parsePost =new Post(parseObject.getObjectId(), parseObject.getString("title"), "test", parseObject.getString("category"));
                //posts.add(new Post(parseObject.getObjectId(), parseObject.getString("title"), parseObject.getString("desc"), category));
                posts.add(postObjects.get(i));
            }
        }
        catch (  com.parse.ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    /*
    int id = item.getItemId();
        switch (id) {
            case R.id.action_newpost:
                Dispatcher.openNewPost(getApplicationContext(), this);
                return true;
            case R.id.action_search:
                Dispatcher.openSearch(getApplicationContext(),this);
                return true; default:
                return super.onOptionsItemSelected(item);
        }
        */
        int id = item.getItemId();

        switch (id) {
            case R.id.action_newpost:
                dispatcher.openNewPost(getApplicationContext(), this);
                return true;
            case R.id.action_search:
                dispatcher.openSearch(getApplicationContext(),this);
                return true;
            case R.id.action_profile:
                dispatcher.openProfile(getApplicationContext(), this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
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


        listView = (ListView) findViewById(R.id.list);

        //listView.setOnScrollListener(new ListViewListener());
        myAdapter = new PostListAdapter(posts, this);
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
