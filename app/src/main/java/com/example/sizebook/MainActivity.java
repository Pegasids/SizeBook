package com.example.sizebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * This class is the initial view class of the project. It has a ListView displaying
 * both the total count of records and a list of record.
 * <br> In this class,
 * user interaction and file manipulation is performed.
 * All files are in the forms of "json" files that are stored in Emulator's
 * accessible from Android Device Monitor;
 */
public class MainActivity extends AppCompatActivity {

    private  static final String FILENAME = "records.sav";
    private TextView CountTextView;
    private ListView RecordListView;
    private ArrayAdapter<Records> adapter;
    private ArrayList<Records> recordList;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState
     * This method loads records.sav. And manage data sent by intent from other activities.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadFromFile();

        Records newRecord = (Records) getIntent().getSerializableExtra("newRecord");
        if (newRecord != null) {
            recordList.add(newRecord);
            saveInFile();
        }

        int delPosition = getIntent().getIntExtra("delPosition", -1);
        if (delPosition > -1){
            recordList.remove(delPosition);
            saveInFile();
        }

        int editSelectPosition = getIntent().getIntExtra("editSelectedPosition", -1);
        Records selectedEditRecord = (Records) getIntent().getSerializableExtra("selectedEditRecord");
        if (editSelectPosition > -1 && selectedEditRecord != null){
            recordList.remove(editSelectPosition);
            recordList.add(selectedEditRecord);
            saveInFile();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewRecordActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Called after method onCreate().
     * This method setText CountTextView.
     * Uses an ArrayAdapter to display a list of record - RecordList.
     * And have a setOnItemClickListener for the ViewList.
     */
    @Override
    protected void onStart(){
        super.onStart();

        CountTextView = (TextView)findViewById(R.id.countTextView);
        CountTextView.setText("Total records: " + recordList.size());

        RecordListView = (ListView)findViewById(R.id.RecordListView);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,recordList);
        RecordListView.setAdapter(adapter);
        RecordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(MainActivity.this, RecordDetailActivity.class);
                intent.putExtra("selectedPosition", position);
                intent.putExtra("selectedRecord", recordList.get(position));
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position) + " is selected", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Loads RecordList from file.
     * @exception FileNotFoundException if the  file is not created.
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            recordList = gson.fromJson(in, new TypeToken<ArrayList<Records>>() {
            }.getType());
            fis.close();
        } catch (FileNotFoundException e) {
            recordList = new ArrayList<>();
        } catch (IOException e) {
            throw  new RuntimeException();
        }
    }

    /**
     * Saves RecordList in file in JSON format.
     * @throws FileNotFoundException if folder not exists.
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(recordList,out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}