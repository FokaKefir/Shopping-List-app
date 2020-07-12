package com.example.shoppinglist.gui.recyclerview;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, GestureDetector.OnGestureListener {

    // region 1. Decl and Init

    public ImageView imageView;
    public TextView txtName;
    public TextView txtDescription;
    public TextView txtNumber;
    public TextView txtDate;

    private RecyclerViewAdapter.OnItemListener onItemListener;
    private GestureDetector gestureDetector;
    private ItemTouchHelper itemTouchHelper;

    // endregion

    // region 2. Constructor

    public ViewHolder(@NonNull View itemView, RecyclerViewAdapter.OnItemListener onItemListener) {
        super(itemView);

        this.imageView = itemView.findViewById(R.id.imageView);
        this.txtName = itemView.findViewById(R.id.txtName);
        this.txtDescription = itemView.findViewById(R.id.txtDescription);
        this.txtNumber = itemView.findViewById(R.id.txtNumber);
        this.txtDate = itemView.findViewById(R.id.txtDate);

        this.onItemListener = onItemListener;
        this.gestureDetector = new GestureDetector(itemView.getContext(), this);
        this.itemTouchHelper = null;

        itemView.setOnTouchListener(this);
    }

    // endregion

    // region 3. Implemented methods

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        this.onItemListener.onItemClick(getAdapterPosition());
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        this.itemTouchHelper.startDrag(this);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return true;
    }

    // endregion

    // region 4. Getters and Setters

    public ItemTouchHelper getItemTouchHelper() {
        return itemTouchHelper;
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    // endregion
}
