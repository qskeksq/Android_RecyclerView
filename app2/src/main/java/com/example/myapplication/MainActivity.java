package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.util.CustomLayouManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ExpandableRecycler recyclerView = (ExpandableRecycler) findViewById(R.id.expandable);

        final ExpandableAdapter adapter = new ExpandableAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new CustomLayouManager(this));

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addData();
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeData();
            }
        });

    }
}
