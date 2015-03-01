package com.parse.tutoo.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.tutoo.R;
import com.parse.tutoo.model.Post;
import com.parse.tutoo.model.ViewHolder;

import java.util.List;

/**
 * Created by hilary on 25/02/2015.
 */
public class MenuListAdapter extends BaseAdapter {
    private List<Post> posts;
    private Context context = null;

    public MenuListAdapter(List<Post> posts, Context context)
    {
        super();
        this.posts = posts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView ==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.main_list_view_item, null);
            viewHolder = new ViewHolder();
            viewHolder.firstLine = (TextView) convertView.findViewById(R.id.firstLine);
            viewHolder.secondLine = (TextView) convertView.findViewById(R.id.secondLine);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Post post = posts.get(position);
        viewHolder.firstLine.setText(post.getTitle());
        viewHolder.secondLine.setText(post.getDescription());
        viewHolder.secondLine.setEllipsize(TextUtils.TruncateAt.END);

        return convertView;
    }
}
