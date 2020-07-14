package com.example.shoppinglist.gui.recyclerview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;
import com.example.shoppinglist.gui.activity.MainActivity;
import com.example.shoppinglist.gui.itemtouchhelper.ItemTouchHelperAdapter;
import com.example.shoppinglist.logic.database.ItemContract;
import com.example.shoppinglist.model.Item;
import com.example.shoppinglist.model.MyDate;


public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> implements ItemTouchHelperAdapter {

    // region 1. Decl and Init

    private MainActivity activity;

    private ItemTouchHelper itemTouchHelper;
    private OnItemListener onItemListener;
    private Cursor cursor;

    private int deletePosition;

    // endregion

    // region 2. Constructor

    public RecyclerViewAdapter(MainActivity activity) {
        this.activity = activity;
        this.onItemListener = this.activity.getListener();
        this.cursor = this.activity.getAllItems();
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
        Item currentItem = getItem(position);

        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.txtName.setText(currentItem.getTextName());
        holder.txtDescription.setText(currentItem.getTextDescription());
        holder.txtNumber.setText(String.valueOf(currentItem.getNumberOfItems()));
        holder.txtDate.setText(currentItem.getDate().toString());

        if (new MyDate(holder.txtDate.getText().toString()).isDefault()){
            holder.txtDate.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return this.cursor.getCount();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        this.activity.moveItem(fromPosition, toPosition);
    }

    @Override
    public void onItemSwiped(int position) {
        this.deletePosition = position;

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        activity.removeItem(deletePosition);
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

    public Item getItem(int position){
        if (!this.cursor.moveToPosition(position)) {
            return null;
        }

        Item item =  new Item(
                this.cursor.getInt(this.cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_IMAGE_RESOURCE)),
                this.cursor.getString(this.cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_NAME)),
                this.cursor.getString(this.cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_DESCRIPTION)),
                this.cursor.getInt(this.cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_NUMBER_OF_ITEMS)),
                new MyDate(
                        this.cursor.getInt(this.cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_DATE_YEAR)),
                        this.cursor.getInt(this.cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_DATE_MONTH)),
                        this.cursor.getInt(this.cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_DATE_DAY))
                )
        );
        item.setId(this.cursor.getLong(this.cursor.getColumnIndex(ItemContract.ItemEntry._ID)));

        return item;
    }

    public void swapCursor(Cursor newCursor) {
        if (this.cursor != null) {
            this.cursor.close();
        }

        this.cursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    // endregion

    // region 5. Interface

    public interface OnItemListener{
        void onItemClick(int position);
    }

    // endregion

}
