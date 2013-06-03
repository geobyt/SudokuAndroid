package com.example.sudokuandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Ken on 6/2/13.
 */
public class ChatArrayAdapter extends ArrayAdapter<String> {
    private int layoutResourceId;
    private String[] data;
    private Context context;

    public ChatArrayAdapter(Context context, int layoutResourceId, String[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)this.context).getLayoutInflater();
        View view = inflater.inflate(this.layoutResourceId, parent, false);

        TextView tv = (TextView)view.findViewById(R.id.textView);
        tv.setText(this.data[position]);

        return view;
    }
}
