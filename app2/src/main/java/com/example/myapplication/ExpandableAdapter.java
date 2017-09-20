package com.example.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.domain.ChildItem;
import com.example.myapplication.domain.Item;
import com.example.myapplication.domain.ParentItem;
import com.example.myapplication.util.ItemTouchHelperListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-09-21.
 */

public class ExpandableAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperListener {

    private final int PARENT_VIEWTYPE = 0;
    private final int CHILD_VIEWTYPE = 1;
    List<Item> visibleItems = new ArrayList<>();
    LayoutInflater inflater;

    public ExpandableAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setData();
    }

    /**
     * Dummy Data
     */
    private void setData(){
        char alphabet = 'A';
        for(int i = 0; i < 10; i++){
            Item item1 = new ParentItem((char)(alphabet+i)+"", PARENT_VIEWTYPE);
            Item item2 = new ChildItem((char)(alphabet+i)+"-1", CHILD_VIEWTYPE);
            Item item3 = new ChildItem((char)(alphabet+i)+"-2", CHILD_VIEWTYPE);
            Item item4 = new ChildItem((char)(alphabet+i)+"-3", CHILD_VIEWTYPE);
            visibleItems.add(item1);
            visibleItems.add(item2);
            visibleItems.add(item3);
            visibleItems.add(item4);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return visibleItems.get(position).getViewType();
    }

    /**
     * 뷰 타입에 맞춰 인플레이션
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == PARENT_VIEWTYPE){
            View view = inflater.inflate(R.layout.item_expandable, parent, false);
            return new ParentItemVH(view);
        } else if(viewType == CHILD_VIEWTYPE){
            View view = inflater.inflate(R.layout.item_sub_expandable, parent, false);
            return new ChildItemVH(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Item item = visibleItems.get(position);
        switch (item.getViewType()){
            case PARENT_VIEWTYPE:
                // holder 타입캐스팅
                final ParentItemVH parentItemVH = (ParentItemVH)holder;
                parentItemVH.name.setText(item.getName());
                parentItemVH.arrow.setTag(holder);
                parentItemVH.arrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 홀더의 어댑터가 가진 위치
                        int holderPosition = ((ParentItemVH)v.getTag()).getAdapterPosition();
                        // 현재 클릭한 arrow를 가진 부모뷰가 expanded
                        if(((ParentItem)visibleItems.get(holderPosition)).isChildViewExpanded){
                            collapseChildView(holderPosition);
                          // 현재 클릭한 arrow를 가진 부모뷰가 collapsed
                        } else {
                            expandChildView(holderPosition);
                        }
                    }
                });
                parentItemVH.itemView.setTag(holder);
                parentItemVH.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        // 홀더의 어댑터가 가진 위치
                        int holderPosition = ((ParentItemVH)v.getTag()).getAdapterPosition();
                        // 롱클릭 할지 자식 뷰를 닫아준다.
                        if(((ParentItem)visibleItems.get(holderPosition)).isChildViewExpanded){
                            collapseChildView(holderPosition);
                        }
                        return true;
                    }
                });
                break;
            case CHILD_VIEWTYPE:
                ((ChildItemVH)holder).name.setText(item.getName());
                break;
        }
    }

    /**
     * 자식 뷰 열기
     * @param parentPosition 넘어오는 position 은 필연적으로 부모 위치가 될 수 밖에 없으므로 예외처리가 필요 없다.
     */
    private void expandChildView(int parentPosition){

        ParentItem item = (ParentItem) visibleItems.get(parentPosition);
        // expand 됬음을 부모 뷰에게 알려준다
        item.isChildViewExpanded = true;
        // collapse 하면서 넣어준 자식 뷰 객체 개수 리턴
        int childCount = item.collapsedChildItem.size();

        for (int i = childCount-1; i >= 0; i--) {
            visibleItems.add(parentPosition+1, item.collapsedChildItem.get(i));
        }
        item.collapsedChildItem.clear();
        notifyItemRangeInserted(parentPosition+1, childCount);
    }

    /**
     * 자식 뷰 닫기
     * @param parentPosition 넘어오는 position 은 필연적으로 부모 위치가 될 수 밖에 없으므로 예외처리가 필요 없다.
     */
    private void collapseChildView(int parentPosition){

        ParentItem item = (ParentItem) visibleItems.get(parentPosition);
        // collapse 됬음을 부모 뷰에게 알려준다
        item.isChildViewExpanded = false;
        // 부모 뷰가 몇 개의 자식 뷰를 가지고 있는 리턴
        int childCount = getChildCount(parentPosition);

        // visibleItems 에서 삭제하고 부모 뷰의 임시 저장소에 저장
        for (int i = 0; i < childCount; i++) {
            ChildItem childItem = (ChildItem) visibleItems.get(parentPosition+1);
            visibleItems.remove(childItem);
            item.collapsedChildItem.add(childItem);
        }
        notifyItemRangeRemoved(parentPosition+1, childCount);
    }

    /**
     * 부모가 자식을 몇 개 가지고 있는지 리턴
     * @return
     */
    private int getChildCount(int parentPosition){
        int count = 0;
        parentPosition++;
        while(true){
            // 부모 뷰가 1개이거나 다음 뷰를 만나면 while 문을 빠져나온다.
            if(visibleItems.size() == parentPosition || visibleItems.get(parentPosition).getViewType() == PARENT_VIEWTYPE){
                break;
            } else {
                count++;
                parentPosition++;
            }
        }
        return count;
    }


    @Override
    public int getItemCount() {
        return visibleItems.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        Item item = visibleItems.get(fromPosition);
        switch (item.getViewType()){
            // 선택된 뷰가 부모 타입
            case PARENT_VIEWTYPE:
                // 부모뷰에서 부모뷰로 가는 경우
                if(visibleItems.get(toPosition).getViewType() == PARENT_VIEWTYPE){
                    // 위로 가는 경우
                    if(fromPosition > toPosition){
                        visibleItems.remove(fromPosition);
                        visibleItems.add(toPosition, item);
                        notifyItemMoved(fromPosition, toPosition);
                    // 아래로 가는 경우
                    } else {
                        // toPosition 부모뷰가 닫힌 상태에서만 옮길 수 있다.
                        int childCount = getChildCount(toPosition);
                        if(childCount == 0){
                            visibleItems.remove(fromPosition);
                            visibleItems.add(toPosition, item);
                            notifyItemMoved(fromPosition, toPosition);
                        }
                    }
                // 부모 뷰에서 자식 뷰로 가는 경우(다른 자식 뷰와 합치고 싶을 때)
                } else {
                    visibleItems.remove(fromPosition);
                    visibleItems.add(toPosition, item);
                    notifyItemMoved(fromPosition, toPosition);
                }
                break;
            case CHILD_VIEWTYPE:
                visibleItems.remove(fromPosition);
                visibleItems.add(toPosition, item);
                notifyItemMoved(fromPosition, toPosition);
                break;
        }
        return true;
    }

    @Override
    public void onItemRemove(int position) {
        switch (visibleItems.get(position).getViewType()){
            // 선택된 뷰가 부모일 경우
            case PARENT_VIEWTYPE:
                // 자식뷰까지 같이 지워준다
                int childCount = getChildCount(position);
                for (int i = 0; i < childCount+1; i++) {
                    visibleItems.remove(position);
                }
                notifyItemRangeRemoved(position, childCount+1);
                break;
            // 선택된 뷰가 자식일 경우
            case CHILD_VIEWTYPE:
                // 그 자리만 삭제한다.
                visibleItems.remove(position);
                notifyItemRemoved(position);
                break;
        }
    }


    /**
     * 홀더 영역
     */
    public class ParentItemVH extends RecyclerView.ViewHolder {

        TextView name;
        ImageButton arrow;

        public ParentItemVH(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.item_name);
            arrow = (ImageButton)itemView.findViewById(R.id.item_arrow);
        }
    }

    public class ChildItemVH extends RecyclerView.ViewHolder {

        TextView name;

        public ChildItemVH(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.subitem_name);
        }
    }

}
