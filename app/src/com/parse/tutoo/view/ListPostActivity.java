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

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.tutoo.R;
import com.parse.tutoo.model.Category;
import com.parse.tutoo.model.Post;
import com.parse.tutoo.util.Dispatcher;
import com.parse.tutoo.util.PostListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ListPostActivity extends ActionBarActivity {
    private ListView listView;
    //private Vector<Post> posts = new Vector<Post>();
    private List<Post> posts = new ArrayList<>();
    private Context context;
    private PostListAdapter postListAdapter;
    private String flag;
    private String condition;

    private void initData() {
        posts.clear();
        String tableName = "Post";
        ParseQuery query = new ParseQuery(tableName);
        System.out.println("condition=" + condition);
        System.out.println("flag=" + flag);
        if (condition.equals(Category.All.toString())) {
            if (flag.equals("market")) {
                query.whereEqualTo("type", "market");
                query.whereNotEqualTo("closed", true);
            } else if (flag.equals("service")) {
                query.whereEqualTo("type", "services");
                query.whereNotEqualTo("closed", true);
            }
            query.orderByDescending("createdAt");

            //List<Post> postObjects = query.find();
            query.findInBackground(new FindCallback() {
                @Override
                public void done(List list, ParseException e) {
                    if (list != null) {
                        posts = list;
                    }
                    else {
                        System.out.println(e.getMessage());
                    }
                }
            });
        } else {
            if (flag.equals("market")) {
                query.whereEqualTo("market", condition);
                query.whereEqualTo("type", "market");
                query.whereNotEqualTo("closed", true);
            } else if (flag.equals("services")) {
                query.whereEqualTo("category", condition);
                query.whereEqualTo("type", "services");
                query.whereNotEqualTo("closed", true);
            }
            query.orderByDescending("createdAt");
            try {
                List<Post> postObjects = query.find();
                for (int i = 0; i < postObjects.size(); i++) {
                    posts.add(postObjects.get(i));
                }
            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }
        }
        //notify data set changed
        postListAdapter.notifyDataSetChanged();
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
        flag = intent.getStringExtra("flag");
        System.out.println("FLAG=" + flag);
        condition = intent.getStringExtra("condition");
        listView = (ListView) findViewById(R.id.list);

        //listView.setOnScrollListener(new ListViewListener());
        System.out.println("oncreate postsize=" + posts.size());
        postListAdapter = new PostListAdapter(posts, this);
        listView.setAdapter(postListAdapter);

        initData();
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
            }

        });

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        System.out.println("onResume");
        initData();
    }
}
