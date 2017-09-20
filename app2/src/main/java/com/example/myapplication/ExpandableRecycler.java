package com.example.myapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;

import com.example.myapplication.util.CustomItemAnimator;
import com.example.myapplication.util.ItemTouchHelperCallback;

/**
 * Created by Administrator on 2017-09-21.
 */

public class ExpandableRecycler extends RecyclerView {

    public ExpandableRecycler(Context context) {
        super(context);
        setItemAnimator(new CustomItemAnimator());
    }

    public ExpandableRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setItemAnimator(new CustomItemAnimator());
    }

    public ExpandableRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setItemAnimator(new CustomItemAnimator());
    }

    /**
     * 어댑터를 세팅하면서 Swipe, Drag&Drop 헬퍼를 설정해준다
     * @param adapter
     */
    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelperCallback((ExpandableAdapter)adapter));
        helper.attachToRecyclerView(this);

    }
}
