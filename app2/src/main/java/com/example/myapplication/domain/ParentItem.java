package com.example.myapplication.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-09-20.
 */

public class ParentItem extends Item {

    // 자식 뷰가 보이는지 여부
    public boolean isChildViewExpanded = true;

    // 숨겨진 자식 뷰 객체 임시저장소
    public List<ChildItem> collapsedChildItem = new ArrayList<>();

    public ParentItem(String name, int viewType) {
        this.viewType = viewType;
        this.name = name;
    }


}
