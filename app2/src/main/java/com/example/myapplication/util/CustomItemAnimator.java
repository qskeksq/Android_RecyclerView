package com.example.myapplication.util;

import android.animation.ObjectAnimator;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by Administrator on 2017-09-21.
 */

public class CustomItemAnimator extends DefaultItemAnimator {

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        View view = holder.itemView;
        ObjectAnimator removeAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f);
        removeAnimator.setDuration(800);
        removeAnimator.setInterpolator(new AccelerateInterpolator());
        removeAnimator.start();
        return true;
    }

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        View view = holder.itemView;
        ObjectAnimator removeAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, view.getWidth());
        removeAnimator.setDuration(300);
        removeAnimator.setInterpolator(new AccelerateInterpolator());
        removeAnimator.start();
        return true;
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        return super.animateMove(holder, fromX, fromY, toX, toY);
    }
}
