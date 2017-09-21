package com.example.myapplication.util;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Administrator on 2017-09-20.
 */

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    ItemTouchHelperListener listener;

    public ItemTouchHelperCallback(ItemTouchHelperListener listener) {
        this.listener = listener;
    }

    /**
     * 어떤 움직임을 포착할 것인지 설정한다
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;

        return makeMovementFlags(dragFlag, swipeFlag);
    }

    /**
     * 움직임이 포착되면 fromPosition, toPosition 값을 getAdapterPosition 으로 넘겨준다.
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return listener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    /**
     * 좌, 우로 지울 경우 현재 position 값을 넘겨준다
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onItemRemove(viewHolder.getAdapterPosition());
    }



}