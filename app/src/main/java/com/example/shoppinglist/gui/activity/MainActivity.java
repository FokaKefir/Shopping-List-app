package com.example.shoppinglist.gui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shoppinglist.R;
import com.example.shoppinglist.gui.itemtouchhelper.MyItemTouchHelper;
import com.example.shoppinglist.gui.recyclerview.RecyclerViewAdapter;
import com.example.shoppinglist.logic.listener.MainActivityListener;
import com.example.shoppinglist.model.Item;
import com.example.shoppinglist.testrdata.TestData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // region 0. Constants

    public static final String EXTRA_TEXT = "item";
    private static final int REQUEST_CODE = 1;
    private static final int NO_POSITION = -1;


    // endregion

    // region 1. Decl. and Init.

    private MainActivityListener listener;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private FloatingActionButton btnAddItem;

    private ArrayList<Item> testList;

    private int position;

    // endregion

    // region 2. Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.main_activity);

        this.listener = new MainActivityListener(this);

        this.recyclerView = findViewById(R.id.recyclerView);
        this.btnAddItem = findViewById(R.id.fab_done);

        this.btnAddItem.setOnClickListener(this.listener);

        buildRecyclerView();

        this.position = NO_POSITION;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            Item item  = data.getParcelableExtra(ItemActivity.EXTRA_RESULT_TEXT);

            if (this.position == NO_POSITION) {
                this.testList.add(item);
                this.adapter.notifyItemInserted(this.testList.size() - 1);
            } else {
                this.testList.remove(this.position);
                this.testList.add(this.position, item);
                this.adapter.notifyItemChanged(this.position);
            }

        }
    }

    // endregion

    // region 3. Other methods

    private void buildRecyclerView(){
        this.testList = TestData.getTestItems();

        this.recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.adapter = new RecyclerViewAdapter(this.testList, this);

        ItemTouchHelper.Callback callback = new MyItemTouchHelper(this.adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);

        this.adapter.setItemTouchHelper(itemTouchHelper);

        itemTouchHelper.attachToRecyclerView(this.recyclerView);

        this.recyclerView.setLayoutManager(this.layoutManager);
        this.recyclerView.setAdapter(this.adapter);

    }

    public void createNewItem() {
        this.position = NO_POSITION;

        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra(EXTRA_TEXT, new Item());

        startActivityForResult(intent, REQUEST_CODE);

    }

    public void editItemAtPosition(int position) {
        this.position = position;

        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra(EXTRA_TEXT, this.testList.get(position));

        startActivityForResult(intent, REQUEST_CODE);
    }

    // endregion

    // region 4. Getters and Setters

    public MainActivityListener getListener() {
        return this.listener;
    }

    // endregion

}