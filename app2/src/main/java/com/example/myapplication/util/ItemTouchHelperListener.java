package com.example.myapplication.util;

/**
 * Created by Administrator on 2017-09-20.
 */
public interface ItemTouchHelperListener {

    /**
     * 아이템 이동시 Callback 에서 리스너의 onItemMove 호출
     * @param fromPosition
     * @param toPosition
     */
    boolean onItemMove(int fromPosition, int toPosition);

    /**
     * 아이템 삭제시 Callback 에서 리스너의 onItemMove 호출
     * @param position
     */
    void onItemRemove(int position);

}
