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

import com.parse.tutoo.R;
import com.parse.tutoo.model.Post;
import com.parse.tutoo.util.PostListAdapter;
import com.parse.tutoo.util.TestData;

import java.util.ArrayList;
import java.util.Vector;

public class SearchActivity extends ActionBarActivity {

    ListView listView;
    ArrayList<Post> posts;
    Context context;
    PostListAdapter listAdapter;

    private void searchBySkill(String query) {
        //Get Data
        // TODO: Change to get data from cloud
        Post[] allposts = TestData.getSearchData();

        System.out.println(query);
        ArrayList<Post> results = new ArrayList<>();
        for (int i = 0; i<allposts.length; i++) {
            if (allposts[i].getDescription().indexOf(query)!=-1) {
                results.add(allposts[i]);
            }
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
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        if (MenuItemCompat.getActionView(searchItem) == null) { System.out.println("null!");}
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list_view);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchBySkill(query);
        }
    }
}
