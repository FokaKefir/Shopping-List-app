package com.example.shoppinglist.gui.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shoppinglist.R;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<Integer> {

    // region 1. Constructor

    public SpinnerAdapter(@NonNull Context context, ArrayList<Integer> list) {
        super(context, 0, list);
    }

    // endregion

    // region 2. Implemented methods

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    // endregion

    // region 3. Other methods

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_row,
                    parent,
                    false
            );
        }

        ImageView imageView = convertView.findViewById(R.id.image_view);

        Integer currentItem = getItem(position);
        if (currentItem != null)
            imageView.setImageResource(currentItem);

        return convertView;
    }

    // endregion
}
