package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myapplication.util.CustomLayouManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandableRecycler recyclerView = (ExpandableRecycler) findViewById(R.id.expandable);

        ExpandableAdapter adapter = new ExpandableAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new CustomLayouManager(this));

    }
}
