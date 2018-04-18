package com.example.kaczordonald.lista4;

import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KaczorDonald on 2017-04-24.
 */

public class RemoveButtonListener implements View.OnClickListener {
    List<Record> rcs;
    int position;
    ArrayAdapter aa;
    public RemoveButtonListener(List<Record> rcs, int position, ArrayAdapter aa){
        this.rcs = rcs;
        this.position = position;
        this.aa = aa;
    }
    @Override
    public void onClick(View v) {
        rcs.remove(position);
        aa.notifyDataSetChanged();

    }
}
