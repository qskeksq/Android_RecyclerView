package com.example.administrator.recyclerview.swipeRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.recyclerview.R;

import java.util.List;

/**
 * Created by Administrator on 2017-09-19.
 */

public class SwipeRecyclerAdapter
        extends RecyclerView.Adapter<SwipeRecyclerAdapter.ViewHolder> implements ItemTouchHelperListener{

    List<String> data;

    public SwipeRecyclerAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public SwipeRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SwipeRecyclerAdapter.ViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 아이템이 움직이는 경우 호출된다.
     * @param fromPosition  움직이기 원하는 아이템
     * @param toPosition    목표 지점
     * @return
     */
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        if(fromPosition<0 || fromPosition >= data.size() || toPosition<0 || toPosition>= data.size()){
            return false;
        }

        String temp = data.get(fromPosition);
        data.remove(fromPosition);
        data.set(toPosition, temp);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    /**
     * 아이템을 제거하는 경우 호출된다.
     * @param position
     */
    @Override
    public void onRemove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
