package com.example.kaczordonald.lista4;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_RETAINED_FRAGMENT = "ListFragment";
    final String xmlFile = "userData";
    Configuration config;
    ArrayList<Record> list;
    NoteAdapter adapter;
    ListView lv;
    EditText et;
    RetainedFragment mRetainedFragment;
    FragmentManager fragmentManager;
    ListFragment frag;
    int auxsel = -1;
    boolean create = false;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        config = getResources().getConfiguration();

        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_horizontal);
        } else {
            setContentView(R.layout.activity_main);
        }
        fragmentManager = getFragmentManager();

        frag = (ListFragment)fragmentManager.findFragmentByTag(TAG_RETAINED_FRAGMENT);
        if(frag==null) {
            create = true;
            frag = new ListFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.listfragment, frag,TAG_RETAINED_FRAGMENT);
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();

        } else {
            list = frag.getData();
            auxsel = frag.getSel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean zacz = false;
        et = (EditText) findViewById(R.id.descc);
        if(list==null) {
            boolean b = readList(list);
            if(!b)
                list = new ArrayList<Record>();
        }
        lv = (ListView)findViewById(R.id.lista);
        adapter = new NoteAdapter(this, R.layout.list_element, list,this);
        if(config.orientation == Configuration.ORIENTATION_LANDSCAPE){
            tv = (TextView)findViewById(R.id.showDesc);
        }
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                adapter.setSelected(-1,parent);
                adapter.rcs.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        tv.setText(list.get(position).longv);
                        if(adapter.getSelected()!=position){
                            adapter.setSelected(position,parent);
                        }
                    } else {
                        if(adapter.getSelected()!=position){
                            adapter.setSelected(position,parent);
                        } else {
                            Intent intent = new Intent(MainActivity.this, PreviewActivity.class);
                            intent.putExtra("desc", list.get(position).longv);
                            intent.putExtra("pos", position);
                            intent.putExtra("lgth", list.size());
                            startActivityForResult(intent, position);
                        }
                    }
                }
            });

        lv.setAdapter(adapter);
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    addRecord();
                }
                return false;
            }
        });
        if(auxsel!=-1) {
            adapter.setSelected(auxsel, lv);
            if(config.orientation == Configuration.ORIENTATION_LANDSCAPE)
                tv.setText(list.get(auxsel).longv);
        }
    }

    private boolean readList(ArrayList<Record> list) {
        try {
            FileInputStream iss= getApplicationContext().openFileInput(xmlFile);
            InputStream is = iss;
            iss.close();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            Toast.makeText(this, "TOŁST", Toast.LENGTH_SHORT).show();

            NodeList nList = doc.getElementsByTagName("string");

            for (int i = 0; i < nList.getLength(); i++) {

                Node node = nList.item(i);
                list.add(new Record(node.getNodeValue()));
            }
            return true;
        } catch(Exception e){
            return false;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        frag.setData(list);
        frag.setSel(adapter.getSelected());
    }
    public void addRecord(View v){
        addRecord();
    }
    public void addRecord(){
        String s = et.getText().toString();
        if(!s.matches("")) {
            Log.d("d1dd",(String)("."+s+"."));
            adapter.rcs.add(new Record(s));
            adapter.notifyDataSetChanged();
            et.setText("");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == Activity.RESULT_OK){
            int result=data.getIntExtra("position",-1);
            boolean edit=data.getBooleanExtra("edit",false);
            String ete = data.getStringExtra("editedtext");
            if(result!=-1) {
                if(edit){
                    edit();
                } else {
                    if(ete!=null) {
                        adapter.rcs.remove(result);
                        adapter.rcs.add(result,new Record(ete));
                        adapter.notifyDataSetChanged();
                    }
                    Toast.makeText(this, Integer.toString(result), Toast.LENGTH_SHORT).show();
                    adapter.setSelected(result, lv);
                    if (config.orientation == Configuration.ORIENTATION_LANDSCAPE)
                        tv.setText(list.get(result).longv);
                }
            }
        }
        if (resultCode == Activity.RESULT_CANCELED) {
        }
    }

    public void help(View view) {

    }

    public void save(View view) {
        try {
            FileOutputStream fileos= getApplicationContext().openFileOutput(xmlFile, Context.MODE_PRIVATE);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            //xmlSerializer.startTag(null, "list");
            for(int i = 0;i<adapter.rcs.size();i++) {
                xmlSerializer.startTag(null, "string");
                xmlSerializer.text(adapter.rcs.get(i).longv);
                xmlSerializer.endTag(null, "string");
            }
            //xmlSerializer.endTag(null, "list");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite = writer.toString();
            fileos.write(dataWrite.getBytes());
            fileos.close();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void moveBottom(View view) {
        int s = adapter.getSelected();
        if(s!=-1){
            while(s<adapter.rcs.size()-1) {
                Collections.swap(adapter.rcs, s, s + 1);
                s++;
            }
            adapter.setSelected(adapter.rcs.size()-1,lv);
            adapter.notifyDataSetChanged();
        }
    }

    public void moveTop(View view) {
        int s = adapter.getSelected();
        if(s!=-1){
            while(s>0) {
                Collections.swap(adapter.rcs, s, s - 1);
                s--;
            }
            adapter.setSelected(0,lv);
            adapter.notifyDataSetChanged();
        }
    }

    public void moveDown(View view) {
        int s = adapter.getSelected();
        if(s!=-1 && s<adapter.rcs.size()-1){
            Collections.swap(adapter.rcs,s,s+1);
            adapter.setSelected(s+1,lv);
            adapter.notifyDataSetChanged();
        }
    }

    public void moveUp(View view) {
        int s = adapter.getSelected();
        if(s!=-1 && s>0){
            Collections.swap(adapter.rcs,s,s-1);
            adapter.setSelected(s-1,lv);
            adapter.notifyDataSetChanged();
        }
    }
    public void goBack(View v){

    }
    public void edit(View v){
        edit();
    }
    public void edit(){
        int position = adapter.getSelected();
        if(position!=-1) {
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            intent.putExtra("desc", list.get(position).longv);
            intent.putExtra("pos", position);
            startActivityForResult(intent, position);
        }
    }


}
