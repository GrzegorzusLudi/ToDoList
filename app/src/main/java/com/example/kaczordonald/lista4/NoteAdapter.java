package com.example.kaczordonald.lista4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KaczorDonald on 2017-04-21.
 */

public class NoteAdapter extends ArrayAdapter<Record> {
    MainActivity ma;
    Context ctx;
    int layoutResourceId;
    int selectedItem;
    public List<Record> rcs;
    int num = 0;
    public NoteAdapter(@NonNull Context context, @LayoutRes int resource, List<Record> v, MainActivity ma) {
        super(context, resource, v);
        this.layoutResourceId = resource;
        this.ctx = context;
        this.ma = ma;
        this.rcs = v;
        selectedItem = -1;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        RecordHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)ctx).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            row.setTag(holder);
            TextView tv = (TextView)row.findViewById(R.id.desc);
            tv.setText(rcs.get(position).shortv);
        } else {
            TextView tv = (TextView)row.findViewById(R.id.desc);
            tv.setText(rcs.get(position).shortv);
        }
        if(row!=null && selectedItem==position)
            row.setBackgroundColor(Color.LTGRAY);
        else
            row.setBackgroundColor(Color.WHITE);
        return row;
    }
    static class RecordHolder {
        TextView tv;
    }
    public void setSelected(int pos,AdapterView<?> parent){
        if(selectedItem!=-1) {
            View v = parent.getChildAt(selectedItem);
            if(v!=null)
                v.setBackgroundColor(Color.WHITE);
        }
        if(pos!=-1) {
            View v = parent.getChildAt(pos);
            if(v!=null)
                v.setBackgroundColor(Color.LTGRAY);
        }
        selectedItem = pos;
    }
    public int getSelected(){
        return selectedItem;
    }

}
