package com.example.administrator.recyclerview.swipeRecycler;

/**
 * Created by Administrator on 2017-09-19.
 */

public interface ItemTouchHelperListener {

    boolean onItemMove(int fromPosition, int toPosition);
    void onRemove(int position);

}
