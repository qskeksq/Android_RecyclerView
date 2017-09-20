package com.example.administrator.recyclerview.decorateRecycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

public class DecorateRecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decorate_and_animate_recycler);

        //
        RecyclerView decorate = (RecyclerView) findViewById(R.id.decorateRecycler);

        //
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add((i+1)+"");
        }

        //
        DecorateRecyclerAdapter adapter = new DecorateRecyclerAdapter(data);
        decorate.addItemDecoration(new SimpleItemDecoration(this));
        decorate.setAdapter(adapter);
        decorate.setLayoutManager(new LinearLayoutManager(this));
    }
}
