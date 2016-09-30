package com.mirhoseini.fyber.util;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class ItemSpaceDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public ItemSpaceDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.bottom = space;

        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            // Add top margin only for the two first items to avoid double space at the beginning of the list
            if (parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view) == 1)
                outRect.top = space;

            if (parent.getChildLayoutPosition(view) % 2 == 1)
                outRect.right = space;
        } else {
            // Add top margin only for the first item to avoid double space at the beginning of the list
            if (parent.getChildLayoutPosition(view) == 0)
                outRect.top = space;

            outRect.right = space;
        }
    }
}
