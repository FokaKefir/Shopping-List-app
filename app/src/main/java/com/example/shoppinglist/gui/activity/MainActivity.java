package com.example.shoppinglist.gui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    // endregion

    // region 1. Decl. and Init.
    private MainActivityListener listener;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private FloatingActionButton btnAddItem;

    private ArrayList<Item> testList;

    // endregion

    // region 2. Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_main);

        this.listener = new MainActivityListener(this);

        this.recyclerView = findViewById(R.id.recyclerView);
        this.btnAddItem = findViewById(R.id.floating_action_button);

        this.btnAddItem.setOnClickListener(this.listener);

        buildRecyclerView();

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


    public void editItemAtPosition(int position) {
        // TODO edit item at position

        Toast.makeText(this, "Item changed", Toast.LENGTH_SHORT).show();
    }

    public RecyclerViewAdapter.OnItemListener getListener() {
        return this.listener;
    }

    // endregion
}