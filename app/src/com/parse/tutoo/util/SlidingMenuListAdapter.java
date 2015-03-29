package com.parse.tutoo.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.tutoo.R;

import java.util.List;

/**
 * Created by hilary on 25/02/2015.
 */
public class SlidingMenuListAdapter extends BaseAdapter {
    private class ViewHolder
    {
        public TextView menuTitle;
    }

    private Context context = null;
    private List<String> menu;

    public SlidingMenuListAdapter(List<String> menu, Context context)
    {
        super();
        this.context = context;
        this.menu = menu;
    }

    @Override
    public int getCount() {
        if (menu == null) {
            return 0;
        }
        return menu.size();
    }

    @Override
    public Object getItem(int position) {
        if (getCount() == 0) {
            return null;
        }
        return menu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // position is the element's id to use
        // convertView is either null -> create a new view for this element!
        //                or not null -> re-use this given view for element!
        // parent is the listview all the elements are in
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.sliding_menu_item, null);
            viewHolder = new ViewHolder();
            viewHolder.menuTitle = (TextView) convertView.findViewById(R.id.sliding_menu_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String menuName = menu.get(position);
        viewHolder.menuTitle.setText(menuName);

        return convertView;
    }
}
