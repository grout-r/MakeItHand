package com.example.roman.makeithand;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class WritingArrayAdapter extends ArrayAdapter<Writing>
{
    private static class ViewHolder {
        private TextView itemView;
    }

    WritingArrayAdapter(Context context, int textViewResourceId, ArrayList<Writing> items) {
        super(context, textViewResourceId, items);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView.findViewById(android.R.id.text1);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Writing item = getItem(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            viewHolder.itemView.setText(item.title);
        }

        return convertView;
    }

}
