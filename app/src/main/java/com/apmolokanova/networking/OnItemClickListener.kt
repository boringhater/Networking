package com.apmolokanova.networking

import android.view.View

interface OnItemClickListener<T> {
    fun onItemClicked(item: T, view: View)
}