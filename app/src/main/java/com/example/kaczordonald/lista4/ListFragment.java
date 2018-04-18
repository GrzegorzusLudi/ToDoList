package com.example.kaczordonald.lista4;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by KaczorDonald on 2017-04-19.
 */

public class ListFragment extends Fragment {

    ArrayList<Record> al;
    int sel;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }
    public void setData(ArrayList al){
        this.al = al;
    }
    public void setSel(int sel){
        this.sel = sel;
    }
    public ArrayList getData(){
        return this.al;
    }
    public int getSel(){
        return this.sel;
    }
}
