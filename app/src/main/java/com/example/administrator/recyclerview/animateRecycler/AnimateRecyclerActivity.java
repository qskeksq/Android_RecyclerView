package com.example.administrator.recyclerview.animateRecycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

public class AnimateRecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate_recycler);

        //
        RecyclerView animate = (RecyclerView) findViewById(R.id.animateRecylcer);

        //
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add((i+1)+"");
        }

        //
        AnimateRecyclerAdapter adapter = new AnimateRecyclerAdapter(data);
        animate.setAdapter(adapter);
        animate.setLayoutManager(new LinearLayoutManager(this));
    }
}
