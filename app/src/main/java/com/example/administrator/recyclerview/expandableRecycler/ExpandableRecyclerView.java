package com.example.administrator.recyclerview.expandableRecycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;

import com.example.administrator.recyclerview.expandableRecycler.util.CustomItemAnimator;
import com.example.administrator.recyclerview.expandableRecycler.util.ItemTouchHelperCallback;

/**
 * Created by Administrator on 2017-09-19.
 */

public class ExpandableRecyclerView extends RecyclerView {


    /**
     * 리사이클러뷰 생성시 자동으로 CustomAnimator 가 적용된다.
     * @param context
     */
    public ExpandableRecyclerView(Context context) {
        super(context);
        setItemAnimator(new CustomItemAnimator());
    }

    public ExpandableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setItemAnimator(new CustomItemAnimator());
    }

    public ExpandableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setItemAnimator(new CustomItemAnimator());
    }

    /**
     * 어댑터 세팅할시 자동으로 Swipe, Drag&Drop 이 적용된다.
     * @param adapter
     */
    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        // 현재 리사이클러뷰에 아이템터치 헬퍼를 달아준다(Swipe, Drag&Drop 을 추가한다)
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelperCallback((ExpandableRecyclerAdapter)adapter));
        helper.attachToRecyclerView(this);
    }




}
