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
import com.parse.tutoo.model.Market;
import com.parse.tutoo.model.MarketPost;
import com.parse.tutoo.model.Post;
import com.parse.tutoo.util.Dispatcher;
import com.parse.tutoo.util.MarketPostListAdapter;
import com.parse.tutoo.util.PostListAdapter;

import java.util.List;
import java.util.Vector;

/**
 * Created by hilary on 25/02/2015.
 */
public class ListPostActivity extends ActionBarActivity {
    ListView listView;
    Vector<Post> posts = new Vector<Post>();
    Vector<MarketPost> marketPosts = new Vector<MarketPost>();
    Context context;
    PostListAdapter postListAdapter;
    MarketPostListAdapter marketPostListAdapter;

    private void initData(String flag, String input) {
        String tableName = "";
        if (flag.equals("market")) {
            tableName = "MarketPost";
            if (input.equals(Market.All.toString())) {
                ParseQuery query = new ParseQuery(tableName);
                try {
                    List<MarketPost> postObjects = query.find();
                    for (int i = 0; i < postObjects.size(); i++) {
                        marketPosts.add(postObjects.get(i));
                    }
                }
                catch (  com.parse.ParseException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("input=" + input);
                System.out.println("tablename=" + tableName);
                ParseQuery query = new ParseQuery(tableName);
                query.whereEqualTo("market", input);
                try {
                    List<MarketPost> postObjects = query.find();
                    for (int i = 0; i < postObjects.size(); i++) {
                        System.out.println("object=" + postObjects.get(i));
                        System.out.println("here");
                        marketPosts.add(postObjects.get(i));
                    }
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }
            }
        } else if (flag.equals("category")) {
            tableName = "Post";
            if (input.equals(Category.All.toString())) {
                ParseQuery query = new ParseQuery(tableName);
                try {
                    List<Post> postObjects = query.find();
                    for (int i = 0; i < postObjects.size(); i++) {
                        posts.add(postObjects.get(i));
                    }
                }
                catch (  com.parse.ParseException e) {
                    e.printStackTrace();
                }
            } else {
                ParseQuery query = new ParseQuery(tableName);
                query.whereEqualTo("category", input);
                try {
                    List<Post> postObjects = query.find();
                    for (int i = 0; i < postObjects.size(); i++) {
                        posts.add(postObjects.get(i));
                    }
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }
            }
        }
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
                Dispatcher.openNewPost(getApplicationContext(), this);
                return true;
            case R.id.action_search:
                Dispatcher.openSearch(getApplicationContext(),this);
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
        // get flag
        String flag = intent.getStringExtra("flag");
        System.out.println("FLAG=" + flag);
        if (flag.equals("market")) {
            String market = intent.getStringExtra("market");
            initData("market", market);
        } else if (flag.equals("category")) {
            String category = intent.getStringExtra("category");
            initData("category", category);
        }

        listView = (ListView) findViewById(R.id.list);

        //listView.setOnScrollListener(new ListViewListener());

        if (flag.equals("market")) {
            marketPostListAdapter = new MarketPostListAdapter(marketPosts, this);
            listView.setAdapter(marketPostListAdapter);
            // ListView Item Click Listener

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent intent = new Intent(context, ViewMarketPostActivity.class);
                    //i.putExtra("name_of_extra", myObject);
                    MarketPost post = marketPosts.get(position);
                    String postId = post.getPostId();
                    intent.putExtra("post_id", postId);
                    startActivity(intent);
                }

            });

        } else if (flag.equals("category")) {
            postListAdapter = new PostListAdapter(posts, this);
            listView.setAdapter(postListAdapter);
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

}
