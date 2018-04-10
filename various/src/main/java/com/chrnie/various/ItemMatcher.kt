package com.chrnie.various

import android.support.v7.widget.RecyclerView

interface ItemMatcher {

    fun requestViewType(date: Any): Int

    fun requestViewHolderBinder(viewType: Int): ViewHolderBinder<Any, RecyclerView.ViewHolder>

    interface Factory {
        fun create(itemList: List<Item<*, *>>): ItemMatcher
    }
}