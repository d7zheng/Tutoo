package com.parse.tutoo.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.tutoo.R;

import java.util.List;

/**
 * Created by Owner on 3/25/2015.
 */
public class CategoryListAdapter extends BaseAdapter {
    private class ViewHolder
    {
        public TextView categoryName;
        public ImageView categoryBackground;
    }

    private Context context = null;
    private List<String> categories;

    public CategoryListAdapter(List<String> categories, Context context)
    {
        super();
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        if (categories == null) {
            return 0;
        }
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.category_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.category_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String category = categories.get(position);
        viewHolder.categoryName.setText(category);
        viewHolder.categoryBackground = (ImageView) convertView.findViewById(R.id.category_background);
        switch(category) {
            case "CS":
                viewHolder.categoryBackground.setBackground(context.getResources().getDrawable(R.drawable.academic_stock));
                break;
            case "Math":
                viewHolder.categoryBackground.setBackground(context.getResources().getDrawable(R.drawable.math_stock));
                break;
            case "Interview":
                viewHolder.categoryBackground.setBackground(context.getResources().getDrawable(R.drawable.interview_stock));
                break;
            default:
                viewHolder.categoryBackground.setBackground(context.getResources().getDrawable(R.drawable.academic_stock));
                break;
        }

        // maybe here's more to do with the view
        return convertView;
    }
}
