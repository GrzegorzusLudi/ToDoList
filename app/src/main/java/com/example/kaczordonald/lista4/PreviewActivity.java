package com.example.kaczordonald.lista4;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.list;

/**
 * Created by KaczorDonald on 2017-04-26.
 */

public class PreviewActivity extends AppCompatActivity {
    private int pos;
    private boolean edit = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_show);

        Intent intent = getIntent();
        String src = intent.getStringExtra("desc");
        pos = intent.getIntExtra("pos",-1);
        int lgth = intent.getIntExtra("lgth",-1);
        TextView tv = (TextView)findViewById(R.id.showDesc);
        TextView dsc = (TextView)findViewById(R.id.dsc);
        tv.setText(src);
        dsc.setText("Position on list: "+Integer.toString(pos+1)+" of "+Integer.toString(lgth));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        goBack();
    }
    public void edit(View v){
        edit = true;
        goBack();
    }
    public void goBack(View v) {
        goBack();
    }
    public void goBack() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("edit",edit);
        returnIntent.putExtra("position",pos);
        setResult(Activity.RESULT_OK,returnIntent);
        this.finish();
    }
}

