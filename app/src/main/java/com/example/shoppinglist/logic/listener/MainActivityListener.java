package com.example.shoppinglist.logic.listener;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;
import com.example.shoppinglist.gui.activity.MainActivity;

public class MainActivityListener extends ItemTouchHelper.SimpleCallback implements View.OnClickListener {

    // region 0. Constants

    // endregion

    // region 1. Delc and Init

    private MainActivity activity;

    // endregion

    // region 2. Constructor

    public MainActivityListener(MainActivity activity) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.activity = activity;
    }

    // endregion

    // region 3. Implemented methods

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.floating_action_button:
                Toast.makeText(this.activity, "ADD", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (direction == ItemTouchHelper.LEFT){
            this.activity.removeItemAtPosition(viewHolder.getAdapterPosition());
        } else {
            this.activity.editItemAtPosition(viewHolder.getAdapterPosition());
        }
    }

    // endregion

}
