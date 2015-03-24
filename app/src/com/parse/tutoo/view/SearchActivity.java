package com.parse.tutoo.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.tutoo.R;
import com.parse.tutoo.model.Post;
import com.parse.tutoo.model.Reply;
import com.parse.tutoo.util.PostListAdapter;
import com.parse.tutoo.util.TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SearchActivity extends ActionBarActivity {

    ListView listView;
    ArrayList<Post> posts = new ArrayList<>();
    Context context;
    PostListAdapter listAdapter;
    SearchManager searchManager;
    SearchView searchView;

    private void searchBySkill(String query) {
        //Get Data
        ParseQuery resultQuery = new ParseQuery("Post");
        resultQuery.whereContains("skills", query.toLowerCase());
        posts.clear(); // Clear previous search results
        List<Post> results ;
        try {
            results = resultQuery.find();
            for (int i = 0; i < results.size(); i++) {
                Post post = (Post) results.get(i);
                posts.add(post);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        listView = (ListView) findViewById(R.id.list);

        listAdapter = new PostListAdapter(posts, this);
        listView.setAdapter(listAdapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(context, ViewPostActivity.class);
                Post post = posts.get(position);
                String postId = post.getPostId();
                intent.putExtra("post_id", postId);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        // Get the SearchView and set the searchable configuration
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        if (MenuItemCompat.getActionView(searchItem) == null) { System.out.println("null!");}
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        searchView.requestFocus();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = getApplicationContext();
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        // Get the intent, verify the action and get the query
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchBySkill(query);
        }
    }
}
