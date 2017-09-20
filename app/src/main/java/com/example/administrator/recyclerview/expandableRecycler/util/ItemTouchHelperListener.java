package com.example.administrator.recyclerview.expandableRecycler.util;

/**
 * Created by Administrator on 2017-09-20.
 */

public interface ItemTouchHelperListener {

    boolean onItemMove(int fromPosition, int toPosition);
    void onRemove(int position);

}
