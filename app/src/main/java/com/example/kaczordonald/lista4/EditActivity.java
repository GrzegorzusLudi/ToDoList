package com.example.kaczordonald.lista4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by KaczorDonald on 2017-04-29.
 */

public class EditActivity extends AppCompatActivity {
    private int pos;
    EditText et;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        Intent intent = getIntent();
        String src = intent.getStringExtra("desc");
        pos = intent.getIntExtra("pos",-1);
        et = (EditText)findViewById(R.id.edd);
        et.setText(src);
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                done();
                return false;
            }
        });
    }
    public void done(View v){
        done();
    }
    public void done(){
        String ete = et.getText().toString();
        if(!ete.matches("")) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("editedtext", ete);
            returnIntent.putExtra("position", pos);
            setResult(Activity.RESULT_OK, returnIntent);
            this.finish();
        }
    }
}
