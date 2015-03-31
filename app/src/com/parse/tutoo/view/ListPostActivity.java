package com.parse.tutoo.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.Date;
import java.util.List;

public class ListPostActivity extends BaseActivity {
    private ListView listView;
    private List<Post> posts = new ArrayList<>();
    private Context context;
    private PostListAdapter postListAdapter;
    private String flag;
    private String condition;
    private Date lastPullTime;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ParseQuery<Post> constructQuery() {
        ParseQuery<Post> query = new ParseQuery("Post");
        if (condition.equals(Category.All.toString())) {
            if (flag.equals("market")) {
                query.whereEqualTo("type", "market");
                query.whereNotEqualTo("closed", true);
            } else if (flag.equals("service")) {
                query.whereEqualTo("type", "services");
                query.whereNotEqualTo("closed", true);
            }
            query.orderByDescending("createdAt");
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
        }
        return query;
    }

    private void initData() {
        if ((posts == null) || (posts.size() == 0)) {
            System.out.println("condition=" + condition);
            System.out.println("flag=" + flag);

            ParseQuery<Post> query = constructQuery();

            query.findInBackground(new FindCallback() {
                @Override
                public void done(List list, ParseException e) {
                    if (e == null) {
                        lastPullTime = new Date(System.currentTimeMillis());
                        posts.addAll(list);
                        //notify data set changed
                        postListAdapter.notifyDataSetChanged();
                        System.out.println("List size="+ posts.size());
                    } else {
                        e.printStackTrace();
                    }
                }
            });
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

        //Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));
        setContentView(R.layout.main_list_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        context = getApplicationContext();

        Intent intent = getIntent();
        // get flag
        flag = intent.getStringExtra("flag");
        System.out.println("FLAG=" + flag);
        condition = intent.getStringExtra("condition");
        listView = (ListView) findViewById(R.id.list);

        postListAdapter = new PostListAdapter(posts, this);
        listView.setAdapter(postListAdapter);

        //listView.setOnScrollListener(new ListViewListener());
        System.out.println("oncreate postsize=" + posts.size());
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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    private void refreshContent(){
        ParseQuery<Post> query = constructQuery();
        query.whereGreaterThanOrEqualTo("createdAt", lastPullTime);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                posts.addAll(posts);
                lastPullTime = new Date(System.currentTimeMillis());
                postListAdapter.notifyDataSetChanged();
                System.out.println("Refreshing");
            }
        });
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        System.out.println("onResume");
        initData();
    }
}
