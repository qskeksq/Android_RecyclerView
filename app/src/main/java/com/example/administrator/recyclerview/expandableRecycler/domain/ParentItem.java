package com.example.administrator.recyclerview.expandableRecycler.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-09-20.
 */

public class ParentItem extends Item {

    public boolean isChildViewExpanded = true;

    // 가려질 경우 이곳으로 자식 아이템이 들어와 임시 저장된다.
    public List<ChildItem> childItems = new ArrayList<>();

    public ParentItem(String name, int viewType) {
        this.viewType = viewType;
        this.name = name;
    }
}
