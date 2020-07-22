package com.rahuldevelops.philomathapp;

import android.support.annotation.NonNull;
import android.view.MenuItem;

public interface RecyclerViewItemListener {
    boolean onNavigationItemSelected(@NonNull MenuItem item);

    void onItemClicked(int position);

    void onItemLongClicked(int position);
}
