package com.parse.tutoo.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.tutoo.R;
import com.parse.tutoo.model.Category;
import com.parse.tutoo.view.ListPostActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hilary on 27/02/2015.
 */
public class CategoryListFragment extends Fragment {
    //private Context context = getApplicationContext();
    private List<Category> categories;

    private String[] initData() {
        List<String> lists = new ArrayList<>();
        for (Category element : Category.values()) {
            lists.add(element.toString());
        }
        String[] array = new String[lists.size()];
        lists.toArray(array); // fill the array

        return array;
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);
        setupView();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.category_list, container, false);

        final ListView listview = (ListView) rootView.findViewById(R.id.category_list);
        String[] values = initData();

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this.getActivity(),
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        // ListView Item Click Listener
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(getActivity(), ListPostActivity.class);
                String category = (String)parent.getItemAtPosition(position);
                intent.putExtra("flag", "category");
                intent.putExtra("category", category);
                //i.putExtra("name_of_extra", myObject);
                //intent.putExtra("post_id", String.valueOf(position));
                startActivity(intent);
            }
        });
        return rootView;
    }

}