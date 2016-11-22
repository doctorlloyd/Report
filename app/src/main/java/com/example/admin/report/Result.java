package com.example.admin.report;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Doc on 11/13/2016.
 */

public class Result extends Activity {
    private ArrayList<String> listOfStudents = new ArrayList<String>();
    private ListView lvResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_display);
        lvResults = (ListView)findViewById(R.id.list_students);
        listOfStudents = getIntent().getStringArrayListExtra("list");
        //Creating an ArrayAdapter using the string array
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listOfStudents);
        lvResults.setAdapter(adapter);
    }
}
