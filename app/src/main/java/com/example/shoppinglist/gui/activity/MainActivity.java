package com.example.shoppinglist.gui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.example.shoppinglist.R;
import com.example.shoppinglist.gui.itemtouchhelper.MyItemTouchHelper;
import com.example.shoppinglist.gui.recyclerview.RecyclerViewAdapter;
import com.example.shoppinglist.gui.service.NotificationService;
import com.example.shoppinglist.logic.database.DBHelper;
import com.example.shoppinglist.logic.database.ItemContract;
import com.example.shoppinglist.logic.database.ItemContract.*;
import com.example.shoppinglist.logic.listener.MainActivityListener;
import com.example.shoppinglist.model.Item;
import com.example.shoppinglist.model.MyDate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    // region 0. Constants

    public static final String EXTRA_TEXT = "item";
    private static final int REQUEST_CODE = 1;
    private static final int NO_POSITION = -1;

    // endregion

    // region 1. Decl. and Init.

    private SQLiteDatabase database;

    private MainActivityListener listener;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private FloatingActionButton btnAddItem;

    private int position;

    // endregion

    // region 2. Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.main_activity);

        DBHelper dbHelper = new DBHelper(this);
        this.database = dbHelper.getWritableDatabase();

        this.listener = new MainActivityListener(this);

        this.recyclerView = findViewById(R.id.recyclerView);
        this.btnAddItem = findViewById(R.id.fab_done);

        this.btnAddItem.setOnClickListener(this.listener);

        buildRecyclerView();

        this.position = NO_POSITION;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.adapter.swapCursor(getAllItems());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            Item item  = data.getParcelableExtra(ItemActivity.EXTRA_RESULT_TEXT);

            if (this.position == NO_POSITION) {
                addItem(item);
            } else {
                editItem(item, this.position);
            }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        startService(
                new Intent(this, NotificationService.class)
        );
    }

    // endregion

    // region 3. Recycler View methods

    private void buildRecyclerView(){
        this.recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.adapter = new RecyclerViewAdapter(this);

        ItemTouchHelper.Callback callback = new MyItemTouchHelper(this.adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);

        this.adapter.setItemTouchHelper(itemTouchHelper);

        itemTouchHelper.attachToRecyclerView(this.recyclerView);

        this.recyclerView.setLayoutManager(this.layoutManager);
        this.recyclerView.setAdapter(this.adapter);

    }

    // endregion

    // region 4. New activity methods

    public void createNewItem() {
        this.position = NO_POSITION;

        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra(EXTRA_TEXT, new Item());

        startActivityForResult(intent, REQUEST_CODE);

    }

    public void editItemAtPosition(int position) {
        this.position = position;

        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra(EXTRA_TEXT, getItem(position));

        startActivityForResult(intent, REQUEST_CODE);
    }

    // endregion

    // region 5. Database methods

    public void refreshItemAtPosition(int actuallyPosition, int newPosition){
        Item item = getItem(actuallyPosition);

        this.database.delete(ItemEntry.TABLE_NAME,
                ItemEntry.COLUMN_POSITION + "=" + actuallyPosition, null);

        ContentValues cv = getCV(item, newPosition);

        this.database.insert(ItemEntry.TABLE_NAME, null, cv);
    }

    public void addItem(Item item) {
        this.adapter.swapCursor(getAllItems());

        int position = this.adapter.getItemCount();

        ContentValues cv = getCV(item, position);

        this.database.insert(ItemEntry.TABLE_NAME, null, cv);
        this.adapter.swapCursor(getAllItems());
        this.adapter.notifyItemInserted(position);
    }

    public void editItem(Item item, int position) {
        this.adapter.swapCursor(getAllItems());

        this.database.delete(ItemEntry.TABLE_NAME,
                ItemEntry.COLUMN_POSITION + "=" + position, null);

        ContentValues cv = getCV(item, position);

        this.database.insert(ItemEntry.TABLE_NAME, null, cv);

        this.adapter.swapCursor(getAllItems());
        this.adapter.notifyItemChanged(position);
    }

    public void removeItem(int position){
        this.adapter.swapCursor(getAllItems());

        Item item = getItem(position);

        Cursor cursor = getAllItems();
        cursor.moveToFirst();
        do {
            int actuallyPosition = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_POSITION));
            if (actuallyPosition > position) {
                refreshItemAtPosition(actuallyPosition, actuallyPosition - 1);
            }
        } while(cursor.moveToNext());

        this.database.delete(ItemEntry.TABLE_NAME,
                ItemEntry._ID + "=" + item.getId(), null);

        this.adapter.swapCursor(getAllItems());
        this.adapter.notifyItemRemoved(position);
    }

    public void moveItem(int fromPosition, int toPosition){
        Item fromItem = getItem(fromPosition);
        Item toItem = getItem(toPosition);

        this.database.delete(ItemEntry.TABLE_NAME,
                ItemEntry.COLUMN_POSITION + "=" + fromPosition, null);
        this.database.delete(ItemEntry.TABLE_NAME,
                ItemEntry.COLUMN_POSITION + "=" + toPosition, null);

        ContentValues fromCV = getCV(fromItem, toPosition);
        ContentValues toCV = getCV(toItem, fromPosition);

        this.database.insert(ItemEntry.TABLE_NAME, null, fromCV);
        this.database.insert(ItemEntry.TABLE_NAME, null, toCV);

        this.adapter.notifyItemMoved(fromPosition, toPosition);
    }

    // endregion

    // region 6. Getters and Setters

    public MainActivityListener getListener() {
        return this.listener;
    }

    public Cursor getAllItems() {
        return this.database.query(
                ItemEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ItemEntry.COLUMN_POSITION + " ASC"
        );
    }

    public Item getItem(int position){
        Cursor cursor = getAllItems();

        if (!cursor.moveToPosition(position)) {
            return null;
        }

        Item item = new Item(
                cursor.getInt(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_IMAGE_RESOURCE)),
                cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_NUMBER_OF_ITEMS)),
                new MyDate(
                        cursor.getInt(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_DATE_YEAR)),
                        cursor.getInt(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_DATE_MONTH)),
                        cursor.getInt(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_DATE_DAY))
                )
        );
        item.setId(cursor.getLong(cursor.getColumnIndex(ItemContract.ItemEntry._ID)));

        return item;
    }

    public ContentValues getCV(Item item, int position){
        ContentValues cv = new ContentValues();
        cv.put(ItemEntry.COLUMN_NAME, item.getTextName());
        cv.put(ItemEntry.COLUMN_DESCRIPTION, item.getTextDescription());
        cv.put(ItemEntry.COLUMN_IMAGE_RESOURCE, item.getImageResource());
        cv.put(ItemEntry.COLUMN_NUMBER_OF_ITEMS, item.getNumberOfItems());
        cv.put(ItemEntry.COLUMN_DATE_YEAR, item.getDate().getYear());
        cv.put(ItemEntry.COLUMN_DATE_MONTH, item.getDate().getMonth());
        cv.put(ItemEntry.COLUMN_DATE_DAY, item.getDate().getDay());
        cv.put(ItemEntry.COLUMN_POSITION, position);

        return cv;
    }

    // endregion

}