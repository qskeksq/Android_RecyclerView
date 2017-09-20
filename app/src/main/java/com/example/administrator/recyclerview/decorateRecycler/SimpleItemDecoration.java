package com.example.administrator.recyclerview.decorateRecycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.recyclerview.R;

/**
 * Created by Administrator on 2017-09-19.
 */

public class SimpleItemDecoration extends RecyclerView.ItemDecoration {

    Drawable drawable;

    public SimpleItemDecoration(Context context) {
        drawable = context.getResources().getDrawable(R.drawable.line);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

//        int leftPadding = parent.getPaddingLeft();
//        int rightPadding = parent.getWidth() - parent.getPaddingRight();
//
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = parent.getChildAt(i);
//
//            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//            int top = child.getBottom() + params.bottomMargin;
//            int bottom = child.getTop() + drawable.getIntrinsicHeight();
//
//            drawable.setBounds(leftPadding, top, rightPadding, bottom);
//            drawable.draw(c);
//        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int leftPadding = parent.getPaddingLeft();
        int rightPadding = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = child.getTop() + drawable.getIntrinsicHeight();

            drawable.setBounds(50, 50, 50, 50);
            drawable.draw(c);
        }
    }

    //    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
//
//        outRect.top = 10;
//    }
}
