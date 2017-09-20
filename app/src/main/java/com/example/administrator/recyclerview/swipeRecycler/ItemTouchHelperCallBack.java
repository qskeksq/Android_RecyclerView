package com.example.administrator.recyclerview.swipeRecycler;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * Created by Administrator on 2017-09-19.
 */

public class ItemTouchHelperCallBack extends ItemTouchHelper.Callback {

    ItemTouchHelperListener listener;

    public ItemTouchHelperCallBack(ItemTouchHelperListener listener) {
        this.listener = listener;
    }

    /**
     * 각 뷰에서 어떤 action 이 가능한지 플래그로 리턴한다.
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;

        return makeMovementFlags(dragFlag, swipeFlag);
    }

    /**
     * 뷰를 드래그 할 때 onMove() 가 호출
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.e("viewHolder", viewHolder.getAdapterPosition()+"");
        Log.e("target", target.getAdapterPosition()+"");
        return listener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    /**
     * 뷰를 좌우로 swipe 할 때 onSwiped 가 호출
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onRemove(viewHolder.getAdapterPosition());
    }

}