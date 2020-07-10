package com.example.shoppinglist.gui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoppinglist.R;
import com.example.shoppinglist.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ItemActivity extends AppCompatActivity implements View.OnClickListener {

    // region 0. Constants

    public static final String EXTRA_RESULT_TEXT = "result";

    // endregion

    // region 1. Decl and Init

    private EditText txtName;
    private EditText txtDescription;
    private EditText txtNumbersOfItems;

    private FloatingActionButton btnDone;

    // endregion

    // region 3. Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.item_activity);

        this.setTitle("Item editor");

        this.txtName = findViewById(R.id.txt_name);
        this.txtDescription = findViewById(R.id.txt_description);
        this.txtNumbersOfItems = findViewById(R.id.txt_number_of_items);
        this.btnDone = findViewById(R.id.fab_done);

        Intent intent = getIntent();
        Item item = intent.getParcelableExtra(MainActivity.EXTRA_TEXT);

        if (!item.isDefault()){
            this.txtName.setText(item.getTextName());
            this.txtDescription.setText(item.getTextDescription());
            this.txtNumbersOfItems.setText(String.valueOf(item.getNumberOfItems()));
        }

        this.btnDone.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_done) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_RESULT_TEXT, new Item(
                    R.drawable.ic_box_blue,
                    this.txtName.getText().toString(),
                    this.txtDescription.getText().toString(),
                    Integer.parseInt(this.txtNumbersOfItems.getText().toString())
            ));

            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    // endregion

    // region 4. Listener

    // endregion
}