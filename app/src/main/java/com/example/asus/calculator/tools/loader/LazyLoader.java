package com.example.asus.calculator.tools.loader;

import android.widget.AbsListView;


public abstract class LazyLoader implements AbsListView.OnScrollListener {
    private static final int DEFAULT_THRESHOLD = 4;

    private boolean isLoading;
    private int previousTotal;
    private int threshold = DEFAULT_THRESHOLD;

    private int currentScrollState;

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.currentScrollState = scrollState;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (isLoading) {
            if (totalItemCount > previousTotal) {
                isLoading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!isLoading && (visibleItemCount == (totalItemCount - firstVisibleItem)
                /*&& this.currentScrollState == SCROLL_STATE_IDLE*/)) {
            isLoading = true;
            loadMore();
        }
    }

    public abstract void loadMore();
}
