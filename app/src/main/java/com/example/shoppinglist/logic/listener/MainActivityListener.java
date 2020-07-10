package com.example.shoppinglist.logic.listener;

import android.view.View;

import com.example.shoppinglist.R;
import com.example.shoppinglist.gui.activity.MainActivity;
import com.example.shoppinglist.gui.recyclerview.RecyclerViewAdapter;

public class MainActivityListener implements View.OnClickListener, RecyclerViewAdapter.OnItemListener {

    // region 1. Delc and Init

    private MainActivity activity;

    // endregion

    // region 2. Constructor

    public MainActivityListener(MainActivity activity) {
        this.activity = activity;
    }

    // endregion

    // region 3. Implemented methods

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_done){
            this.activity.createNewItem();
        }
    }

    @Override
    public void onItemClick(int position) {
        this.activity.editItemAtPosition(position);
    }

    // endregion

}
