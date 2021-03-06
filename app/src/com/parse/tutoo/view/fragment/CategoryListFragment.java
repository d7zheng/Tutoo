package com.parse.tutoo.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.tutoo.R;
import com.parse.tutoo.model.Category;
import com.parse.tutoo.util.CategoryListAdapter;
import com.parse.tutoo.view.BaseActivity;
import com.parse.tutoo.view.ListPostActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * CategoryList Fragment
 * Created by hilary on 27/02/2015.
 */
public class CategoryListFragment extends Fragment {

    private List<String> categories;
    private Context context;

    private void getCategories() {
        categories = new ArrayList();
        for (Category element : Category.values()) {
            categories.add(element.toString());
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.category_list_view);
        View rootView = inflater.inflate(R.layout.category_list_view, container, false);
        context = getActivity().getApplicationContext();

        getCategories();

        CategoryListAdapter adapter = new CategoryListAdapter(categories, getActivity());
        final ListView listview = (ListView) rootView.findViewById(R.id.list_category);
        listview.setAdapter(adapter);

        // ListView Item Click Listener
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            Intent intent = new Intent(context, ListPostActivity.class);
            String category = categories.get(position);
            intent.putExtra("flag", "services");
            intent.putExtra("condition", category);
            startActivity(intent);
            }
        });
        return rootView;
    }
}