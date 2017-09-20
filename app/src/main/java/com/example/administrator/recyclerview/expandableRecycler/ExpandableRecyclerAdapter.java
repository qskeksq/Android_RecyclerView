package com.example.administrator.recyclerview.expandableRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.recyclerview.R;
import com.example.administrator.recyclerview.expandableRecycler.domain.ChildItem;
import com.example.administrator.recyclerview.expandableRecycler.domain.Item;
import com.example.administrator.recyclerview.expandableRecycler.domain.ParentItem;
import com.example.administrator.recyclerview.expandableRecycler.util.ItemTouchHelperListener;

import java.util.ArrayList;
import java.util.List;

/**
 * ExpandableRecyclerAdapter 구현
 */

public class ExpandableRecyclerAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperListener {

    private final int PARENT_ITEM_VIEW = 0;
    private final int CHILD_ITEM_VIEW = 1;

    private ArrayList<Item> visibleItems = new ArrayList<>();

    public ExpandableRecyclerAdapter() {
        char alphabet = 'A';
        for(int i = 0; i < 10; i++){
            Item item1 = new ParentItem((char)(alphabet+i)+"", PARENT_ITEM_VIEW);
            Item item2 = new ChildItem((char)(alphabet+i)+"-1", CHILD_ITEM_VIEW);
            Item item3 = new ChildItem((char)(alphabet+i)+"-2", CHILD_ITEM_VIEW);
            Item item4 = new ChildItem((char)(alphabet+i)+"-3", CHILD_ITEM_VIEW);

            visibleItems.add(item1);
            visibleItems.add(item2);
            visibleItems.add(item3);
            visibleItems.add(item4);
        }
    }

    /**
     * 뷰 타입 리턴
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return visibleItems.get(position).viewType;
    }

    /**
     * 뷰타입에 따라 홀더 생성&리턴
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RecyclerView.ViewHolder holder = null;
        if(viewType == PARENT_ITEM_VIEW){
            View view = inflater.inflate(R.layout.item_expandable, parent, false);
            holder = new ParentItemVH(view);
        } else if(viewType == CHILD_ITEM_VIEW){
            View view = inflater.inflate(R.layout.item_sub_expandable, parent, false);
            holder = new ChildItemVH(view);
        }
        return holder;
    }


    /**
     * 데이터 바인딩
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ParentItemVH){
            ParentItemVH parentItemVH = (ParentItemVH) holder;
            parentItemVH.name.setText(visibleItems.get(position).name);
            parentItemVH.arrow.setTag(holder);

            // arrow 버튼 클릭시 자식 뷰 펼치거나 숨김
            parentItemVH.arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 홀더에서 바로 꺼내면 final 로 변하기 때문에 태그를 붙여준다.
                    int holderPosition = ((RecyclerView.ViewHolder) v.getTag()).getAdapterPosition();
                    Log.d("불린값", ((ParentItem)visibleItems.get(holderPosition)).isChildViewExpanded+"");
                    if(((ParentItem)visibleItems.get(holderPosition)).isChildViewExpanded){
                        collapseChildView(holderPosition);
                    } else {
                        expandChildView(holderPosition);
                    }
                }
            });

            // 뷰를 옮기기 위해 부모 뷰를 롱클릭한다면 자식 뷰를 일단 collapse 해준다.
            parentItemVH.itemView.setTag(holder);
            if(parentItemVH.getItemViewType() == PARENT_ITEM_VIEW){
                parentItemVH.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int holderPosition = ((RecyclerView.ViewHolder)v.getTag()).getAdapterPosition();
                        if(((ParentItem)visibleItems.get(holderPosition)).isChildViewExpanded){
                            collapseChildView(holderPosition);
                        }
                        return true;
                    }
                });
            }
            // 자식 뷰인 경우는 일단 데이터만 세팅해준다.
        } else if(holder instanceof ChildItemVH){
            ((ChildItemVH) holder).name.setText(visibleItems.get(position).name);
        }
    }

    /**
     * 자식 뷰 숨기기
     */
    private void collapseChildView(int parentPosition) {
        Log.d("parentPosition", parentPosition+"");
        ParentItem parentItem = (ParentItem) visibleItems.get(parentPosition);
        parentItem.isChildViewExpanded = false;

        List<ChildItem> invisibleChildItem = parentItem.childItems;

        int childViewCount = childViewCount(parentPosition);
        for (int i = 0; i < childViewCount; i++) {
            invisibleChildItem.add((ChildItem) visibleItems.get(parentPosition+1));
            visibleItems.remove(parentPosition+1);
        }
        notifyItemRangeRemoved(parentPosition+1, childViewCount);
    }

    /**
     * 자식 뷰 펼치기
     */
    private void expandChildView(int parentPosition) {
        Log.d("parentPosition", parentPosition+"");
        ParentItem parentItem = (ParentItem) visibleItems.get(parentPosition);
        parentItem.isChildViewExpanded = true;
        int childViewCount = parentItem.childItems.size();
        Log.d("childViewCount", childViewCount+"");
        for (int i = childViewCount-1; i >= 0; i--) {
            visibleItems.add(parentPosition+1, parentItem.childItems.get(i));
        }
        parentItem.childItems.clear();
        notifyItemRangeInserted(parentPosition+1, childViewCount);
    }

    /**
     * 자식이 몇 개 있는지 체크
     */
    private int childViewCount(int position){

        int count = 0;
        position++;

        while(true){
            // 만약 부모 타입이 나오거나 부모 타임이 하나밖에 없어서 끝까지 왔을 경우 멈춘다.
            if(visibleItems.size() == position || visibleItems.get(position).viewType == PARENT_ITEM_VIEW){
                break;
            } else {
                count++;
                position++;
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

        // 자식 뷰 move
        if(item.viewType == CHILD_ITEM_VIEW){
            if(fromPosition <= 0 || toPosition <= 0){
                return false;
            }
            ChildItem childItem = (ChildItem) item;
            visibleItems.remove(fromPosition);
            visibleItems.add(toPosition, childItem);
            notifyItemMoved(fromPosition, toPosition);
        // 부모 뷰 move
        } else {
            // 부모 뷰 -> 부모 뷰(이 케이스는 부모뷰가 자식 뷰로 들어갈 수 없도록 한다)
            if(visibleItems.get(fromPosition).viewType == visibleItems.get(toPosition).viewType){
                // 위로 올라가는 경우
                if(fromPosition > toPosition){
                    visibleItems.remove(fromPosition);
                    visibleItems.add(toPosition, item);
                    notifyItemMoved(fromPosition, toPosition);
                // 밑으로 가는 경우 자식 뷰가 collapse 되어 있는 경우에만 들어갈 수 있다.
                } else {
                    int parentPosition = getParentPosition(toPosition);
                    int childViewCount = childViewCount(parentPosition);
                    if(childViewCount == 0){
                        visibleItems.remove(fromPosition);
                        visibleItems.add(toPosition, item);
                        notifyItemMoved(fromPosition, toPosition);
                    }
                }
            // 부모 뷰 -> 자식 뷰
            } else {
                // 밑으로 가는 경우 자식 뷰가 collapse 되어 있는 경우에만 들어갈 수 있다.
                int parentPosition = getParentPosition(toPosition);
                int childViewCount = childViewCount(parentPosition);
                if(childViewCount == 0){
                    visibleItems.remove(fromPosition);
                    visibleItems.add(toPosition, item);
                    notifyItemMoved(fromPosition, toPosition);
                }
            }
        }
        return false;
    }

    @Override
    public void onRemove(int position) {
       int viewType = visibleItems.get(position).viewType;
        switch (viewType){
            case PARENT_ITEM_VIEW:
                int childViewCount = childViewCount(position);
                for (int i = 0; i <= childViewCount; i++) {
                    visibleItems.remove(position);
                }
                notifyItemRangeRemoved(position, childViewCount+1);

                break;
            case CHILD_ITEM_VIEW:
                visibleItems.remove(position);
                notifyItemRemoved(position);
                break;
        }
    }

    /**
     * 부모 뷰로 찾아가기
     */
    public int getParentPosition(int position){
        while(true){
            if(visibleItems.get(position).viewType == PARENT_ITEM_VIEW){
                break;
            } else {
                position--;
            }
        }
        return position;
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
