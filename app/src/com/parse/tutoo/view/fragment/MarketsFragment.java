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
import com.parse.tutoo.model.Market;
import com.parse.tutoo.view.ListPostActivity;

import java.util.ArrayList;
import java.util.List;

public class MarketsFragment extends Fragment {
    private List<Market> markets;

    private String[] initData() {
        List<String> lists = new ArrayList<>();
        for (Market element : Market.values()) {
            lists.add(element.toString());
        }
        String[] array = new String[lists.size()];
        lists.toArray(array); // fill the array

        return array;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.market_list, container, false);

        final ListView listview = (ListView) rootView.findViewById(R.id.market_list);
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
                String market = (String)parent.getItemAtPosition(position);

                intent.putExtra("flag", "market");
                intent.putExtra("market", market);
                //i.putExtra("name_of_extra", myObject);
                //intent.putExtra("post_id", String.valueOf(position));
                startActivity(intent);
            }
        });
        return rootView;
    }
}
