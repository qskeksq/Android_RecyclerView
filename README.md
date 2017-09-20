# RecyclerView

## 필수 개념

- Adapter
  - view, viewHoler생성
  - item과 viewHolder를 bind
  - RecyclerView에게 changes를 notify
  - 아이템 클릭등의 interaction 핸들링
  - viewTypes 분기
  - onFailedToRecyclerView를 통해 Recycler복원
- ViewHolder
    - 홀더의 가장 중요한 존재 이유인 '재사용'을 잘 파악해야 한다. 홀더는 '틀', '형식'을 재활용한다. 따라서 중간에
    홀더의 속성을 바꿔버리면 바꿔진 속성이 계속 재활용 되게 되어있다. 그렇게 바뀐 틀이 Scrapped Pool 에서 언제 재활용될지
    장담할 수 없기 때문에 반드시 '데이터'를 중심으로 홀더를 변형시키고, 다시 원상복귀 시켜줘야 한다.
- LayoutManager
    - 모든 자식 뷰를 위치시키는 기능을 한다. 자식 뷰의 크기와 개수를 파악하고 어떻게 보여줄지 결정한다
    - LinearLayoutManager – 수평/수직의 스크롤 리스트
    - GridLayoutManager – 그리드 리스트
    - StaggeredGridLayoutManage
    - 스크롤도 담당한다 : scrollToPosition()
    - setOrientation()
- ItemDecorator
    - 자식 뷰의 틀을 미리 정하거나 장식해 주는 역할을 한다. 즉, 자식 뷰 내부에서 그리지 않고
    재활용 하는 틀을 갖다가 바꿔준다. 밑줄을 그어주거나 패딩값을 설정하는 등의 설정을 할 수 있다.
    - 각 아이템 항목별로 오프셋을 추가하거나 아이템을 꾸미는 작업을 하게 된다. 예를 들어 스크롤 시 콘텐츠의 내용에 따라 View의 높이가 달라져 레이아웃의 위치를 이동해야 하는 작업하는 경우 여기에서 처리
    - getItemOffsets함수를 통해 Item의 영역을 늘릴 수 있다. LayoutManager에서 getItemOffsets()의 호출을 통해 아이템의 레이아웃의 크기를 측정 하기 때문에 getItemOffsets()에서 작업하면된다
    - onDraw함수를 통해서 아이템이 그려지기 전에 먼저 그릴 수 있고
    - onDrawOver함수를 통해서 아이템 위에 덮어서 그릴 수 있다.
    - viewHolder가 필요할때 recyclerView.getChildViewHolder(view)함수를 통해서 가져온다

- ItemAnimator
    - 추가, 삭제, 정렬 할 때 아래의 함수를 호출해주면 ItemAnimator를 통해 애니메이션을 적용해 줄 수 있다.
    - notifyItemChanged(position)
    - notifyItemRemoved(position), notifyItemRangeRemoved(from, to)
    - notifyItemInserted(position), notifyItemRangeInserted(from, to)
    - notifyItemMoved(position)

## 추가 기능

- Swipe, Drag&Drop
    - ItemTouchHelper
    - ItemTouchHelperListener
    - ItemTouchHelperCallBack
- ExpandableRecyclerView
    - viewType

## Position
- getLayoutPosition()
- getAdapterPosition() : onBindViewHolder 메소드 안에 final int position을 사용하지 말라. (final인 position은 보장하지 않는다. 위치가 바뀌거나 지워질때 등)
-> 해결방법. holder.getAdapterPosition을 이용
- getPosition()

### ExpandableRecyclerView & Swipe, Drag/Drop

#### 터치, Swipe, Drag&Drop

> (1) 터치리스너 인터페이스

```java
public interface ItemTouchHelperListener {
    boolean onItemMove(int fromPosition, int toPosition);
    void onRemove(int position);
}
```

> (2) 터치 콜백

```java
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    ItemTouchHelperListener listener;

    public ItemTouchHelperCallback(ItemTouchHelperListener listener) {
        this.listener = listener;
    }
    // 움직임 설정
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlag, swipeFlag);
    }
    // 위아래로 움직일 경우
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return listener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }
    // 스와이프 할 경우
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onRemove(viewHolder.getAdapterPosition());
    }
}

// 구현
recyclerView.addItemDecoration(new ItemDecoration(this));
```


> (3) Animator 추가

```java
public class CustomItemAnimator extends DefaultItemAnimator {

    // 추가 애니메이션
    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        return super.animateAdd(holder);
    }

    // 삭제 애니메이션
    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        View view = holder.itemView;
        ObjectAnimator removeAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 1.0f, 0.0f);
        removeAnimator.setDuration(100);
        removeAnimator.setInterpolator(new DecelerateInterpolator());
        removeAnimator.start();
        return true;
    }

    // 이동 애니메이션
    @Override
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        return super.animateMove(holder, fromX, fromY, toX, toY);
    }
}
```

#### Custom LayoutManager 구현
  - extends LinearLayoutManager

#### Custom RecyclerView 구현
  - extends RecyclerView

#### Custom Adapter 구현
  - extends extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperListener

> 뷰 타입 설정

- 뷰 타입으로 부모와 자식 객체를 나누기 때문에 중요하다

```java
/**
 * 뷰 타입 리턴
 * @param position
 * @return
 */
@Override
public int getItemViewType(int position) {
    return visibleItems.get(position).viewType;
}
```

> onCreateViewHolder

- 뷰 타입에 따라 따로 설정

```java
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
```

> onBindViewHolder

- 데이터 바인딩

```java
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
```

> collpaseChildView

```java
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
```

> expandChildView

```java
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
```

> 부모에게 자식 뷰의 개수 리턴

```java
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
```

> 뷰 이동

```java
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
```
> 뷰 삭제

```java
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
```

> 자식 뷰 선택시 부모 뷰 위치 리턴

```java
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
```
