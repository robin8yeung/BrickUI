package com.seewo.brick.params

interface RecyclerItemData {
    /**
     * 比较两个RecyclerItemData是否为同一个item（如对应同一个id）
     * 如果是同一项，则如果不相等，认为是同一项需要更新内容；否则认为不是同一项
     */
    fun areSameItem(out: Any?): Boolean
}