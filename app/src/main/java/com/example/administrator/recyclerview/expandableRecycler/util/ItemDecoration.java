package com.example.administrator.recyclerview.expandableRecycler.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.administrator.recyclerview.R;
import com.example.administrator.recyclerview.expandableRecycler.ExpandableRecyclerAdapter;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017-09-20.
 */

public class ItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable parentDivider;
    private Drawable subDivider;


    public ItemDecoration(Context context) {
        parentDivider = context.getResources().getDrawable(R.drawable.parent_divider);
        subDivider = context.getResources().getDrawable(R.drawable.child_divider);
    }

    // canvas에 적합한 decoration을 draw
    // item view가 그려지기전에 decoration이 먼저 그려지며, view밑에 나타난다.
    @Override
    public void onDraw(Canvas c, RecyclerView recyclerView, RecyclerView.State state) {

        super.onDraw(c, recyclerView, state);
        int left = recyclerView.getPaddingLeft();
        int right = recyclerView.getWidth() - recyclerView.getPaddingRight();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(child);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            if(viewHolder instanceof ExpandableRecyclerAdapter.ParentItemVH){
                int bottom = top + parentDivider.getIntrinsicHeight();
                parentDivider.setBounds(left, top, right, bottom);
//                parentDivider.draw(c);
            }else{
                int bottom = top + subDivider.getIntrinsicHeight();
                subDivider.setBounds(left+20, top, right-20, bottom);
//                subDivider.draw(c);
            }
        }
    }


    // (이름이 왜 getItemOffset일까. setPixelToRect 같은 느낌인데..)
    // outRect를 인자로 보내면, outRect에 값들을 넣어줌. (call by reference)
    // outRect의 각 자리는 각 item의 padding의 정도를 정해줘야함.
    // default는 다 0
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        Log.i(TAG, "getItemOffsets");

        RecyclerView.ViewHolder viewHolder = (parent.getChildViewHolder(view));

        if(viewHolder instanceof ExpandableRecyclerAdapter.ParentItemVH){
            outRect.set(0, 1, 0, 1);
        }else{
            outRect.set(40, 1, 1, 1);
        }

    }

}
