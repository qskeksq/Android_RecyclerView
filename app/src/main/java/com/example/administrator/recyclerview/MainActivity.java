package com.example.administrator.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.recyclerview.expandableRecycler.ExpandableRecyclerAdapter;
import com.example.administrator.recyclerview.expandableRecycler.ExpandableRecyclerView;
import com.example.administrator.recyclerview.expandableRecycler.util.CustomLayoutManager;
import com.example.administrator.recyclerview.expandableRecycler.util.ItemDecoration;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_recycler);

        ExpandableRecyclerView recyclerView = (ExpandableRecyclerView) findViewById(R.id.expandableRecycler);

        ExpandableRecyclerAdapter adapter = new ExpandableRecyclerAdapter();

        recyclerView.addItemDecoration(new ItemDecoration(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new CustomLayoutManager(this));
    }
}
