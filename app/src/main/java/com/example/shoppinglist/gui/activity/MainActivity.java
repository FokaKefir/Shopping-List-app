package com.example.shoppinglist.gui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.shoppinglist.R;
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
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private FloatingActionButton fabAddItem;

    // endregion

    // region 2. Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_main);

        this.listener = new MainActivityListener(this);

        this.recyclerView = findViewById(R.id.recyclerView);
        this.fabAddItem = findViewById(R.id.floating_action_button);

        buildRecyclerView();

        this.fabAddItem.setOnClickListener(this.listener);

    }

    // endregion

    // region 3. Other methods

    private void buildRecyclerView(){
        ArrayList<Item> testList = TestData.getTestItems();

        this.recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.adapter = new RecyclerViewAdapter(testList);

        this.recyclerView.setLayoutManager(this.layoutManager);
        this.recyclerView.setAdapter(this.adapter);
    }

    // endregion
}