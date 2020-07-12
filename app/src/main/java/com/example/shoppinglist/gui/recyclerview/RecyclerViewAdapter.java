package com.example.shoppinglist.gui.recyclerview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;
import com.example.shoppinglist.gui.activity.MainActivity;
import com.example.shoppinglist.gui.itemtouchhelper.ItemTouchHelperAdapter;
import com.example.shoppinglist.model.Item;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> implements ItemTouchHelperAdapter {

    // region 1. Decl and Init

    private MainActivity activity;

    private ArrayList<Item> itemList;
    private ItemTouchHelper itemTouchHelper;
    private OnItemListener onItemListener;

    private int deletePosition;

    // endregion

    // region 2. Constructor

    public RecyclerViewAdapter(ArrayList<Item> itemList, MainActivity activity) {
        this.itemList = itemList;
        this.activity = activity;
        this.onItemListener = this.activity.getListener();
    }

    // endregion

    // region 3. Implemented methods

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, this.onItemListener);
        if (viewHolder.getItemTouchHelper() == null) {
            viewHolder.setItemTouchHelper(this.itemTouchHelper);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item currentItem = itemList.get(position);

        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.txtName.setText(currentItem.getTextName());
        holder.txtDescription.setText(currentItem.getTextDescription());
        holder.txtNumber.setText(String.valueOf(currentItem.getNumberOfItems()));
        holder.txtDate.setText(currentItem.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Item fromItem = this.itemList.get(fromPosition);
        this.itemList.remove(fromItem);
        this.itemList.add(toPosition, fromItem);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemSwiped(int position) {
        this.deletePosition = position;

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        itemList.remove(deletePosition);
                        notifyItemRemoved(deletePosition);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        notifyItemChanged(deletePosition);
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    // endregion

    // region 4. Getters and Setters

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    // endregion

    // region 5. Interface

    public interface OnItemListener{
        void onItemClick(int position);
    }

    // endregion

}
