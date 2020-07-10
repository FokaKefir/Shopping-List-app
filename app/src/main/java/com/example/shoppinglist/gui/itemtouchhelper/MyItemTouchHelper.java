package com.example.shoppinglist.gui.itemtouchhelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;

public class MyItemTouchHelper extends ItemTouchHelper.Callback {

    // region 1. Decl and Init

    private final ItemTouchHelperAdapter adapter;

    // endregion

    // region 2. Constructor

    public MyItemTouchHelper(ItemTouchHelperAdapter adapter) {
        this.adapter = adapter;
    }


    // endregion

    // region 3. Implemented methods


    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(
                ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorWhite)
        );
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            viewHolder.itemView.setBackgroundColor(
                    ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorSilver)
            );
        }
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        this.adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        this.adapter.onItemSwiped(viewHolder.getAdapterPosition());
    }

    // endregion
}
