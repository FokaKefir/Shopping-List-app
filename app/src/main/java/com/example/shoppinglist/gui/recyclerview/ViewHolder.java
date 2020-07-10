package com.example.shoppinglist.gui.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    // region 1. Decl and Init

    public ImageView imageView;
    public TextView txtName;
    public TextView txtDescription;
    public TextView txtNumber;

    // endregion

    // region 2. Constructor

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        this.imageView = itemView.findViewById(R.id.imageView);
        this.txtName = itemView.findViewById(R.id.txtName);
        this.txtDescription = itemView.findViewById(R.id.txtDescription);
        this.txtNumber = itemView.findViewById(R.id.txtNumber);
    }

    // endregion
}
