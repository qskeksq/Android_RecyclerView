package com.example.administrator.recyclerview.swipeRecycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.administrator.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Swipe, Drag&Drop 구현한 RecyclerView
 */
public class SwipeRecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_recycler);

        //
        RecyclerView swipe = (RecyclerView) findViewById(R.id.swipeRecycler);

        //
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add((i+1)+"");
        }

        //
        SwipeRecyclerAdapter adapter = new SwipeRecyclerAdapter(data);
        swipe.setAdapter(adapter);

        //
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelperCallBack(adapter));
        helper.attachToRecyclerView(swipe);

        //
        swipe.setLayoutManager(new LinearLayoutManager(this));

    }
}
