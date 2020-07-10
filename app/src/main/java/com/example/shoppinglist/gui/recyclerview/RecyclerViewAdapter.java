package com.example.shoppinglist.gui.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;
import com.example.shoppinglist.model.Item;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    // region 1. Decl and Init

    private ArrayList<Item> itemList;

    // endregion

    // region 2. Constructor

    public RecyclerViewAdapter(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }


    // endregion

    // region 3. Implemented methods

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item currentItem = itemList.get(position);

        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.txtName.setText(currentItem.getTextName());
        holder.txtDescription.setText(currentItem.getTextDescription());
        holder.txtNumber.setText(String.valueOf(currentItem.getNumberOfItems()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // endregion

}
