package com.example.kaczordonald.lista4;

import android.os.Bundle;
import android.app.Fragment;

public class RetainedFragment extends Fragment {

    // data object we want to retain
    private String data;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}