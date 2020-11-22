package com.example.lab1

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MyDecoration(private val space: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.top = space
        outRect.bottom = space
    }

}