package com.parse.tutoo.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.tutoo.R;

import java.util.List;

/**
 * Created by hilary on 25/02/2015.
 */
public class MenuListAdapter<T> extends BaseAdapter {

    private class ViewHolder
    {
        public TextView firstLine;
        public TextView secondLine;
        public TextView thirdLine;
        public TextView distance;
    }

    private List<T> objs;
    private Context context = null;

    public MenuListAdapter(List<T> objs, Context context)
    {
        super();
        this.objs = objs;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (objs == null) {
            return 0;
        }
        return objs.size();
    }

    @Override
    public Object getItem(int position) {
        return objs.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView ==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.main_list_view_item, null);
            viewHolder = new ViewHolder();
            viewHolder.firstLine = (TextView) convertView.findViewById(R.id.firstLine);
            viewHolder.secondLine = (TextView) convertView.findViewById(R.id.secondLine);
            viewHolder.thirdLine = (TextView) convertView.findViewById(R.id.thirdLine);
            viewHolder.distance = (TextView) convertView.findViewById(R.id.distance);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        T obj = objs.get(position);
        viewHolder.firstLine.setText(this.getFirstLine(obj));
        viewHolder.secondLine.setText(this.getSecondLine(obj));
        viewHolder.secondLine.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.thirdLine.setText(this.getThirdLine(obj));
        viewHolder.distance.setText(this.getDistance(obj));
        return convertView;
    }

    public String getFirstLine(T obj) {
        return "";
    }

    public String getSecondLine(T obj) {
        return "";
    }

    public String getThirdLine(T obj) {
        return "";
    }

    public String getDistance(T obj) {
        return "";
    }
}
