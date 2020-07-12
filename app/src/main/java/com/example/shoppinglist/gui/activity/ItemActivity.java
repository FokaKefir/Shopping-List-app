package com.example.shoppinglist.gui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppinglist.R;
import com.example.shoppinglist.model.Item;
import com.example.shoppinglist.model.MyDate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;


public class ItemActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    // region 0. Constants

    public static final String EXTRA_RESULT_TEXT = "result";

    // endregion

    // region 1. Decl and Init

    private EditText txtName;
    private EditText txtDescription;
    private EditText txtNumbersOfItems;
    private TextView txtDate;

    private FloatingActionButton btnDone;
    private Button btnDate;

    private MyDate date;

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
        this.txtDate = findViewById(R.id.txt_date);
        this.btnDone = findViewById(R.id.fab_done);
        this.btnDate = findViewById(R.id.btn_date);

        this.date = new MyDate();

        Intent intent = getIntent();
        Item item = intent.getParcelableExtra(MainActivity.EXTRA_TEXT);

        if (item != null && !item.isDefault()){
            this.txtName.setText(item.getTextName());
            this.txtDescription.setText(item.getTextDescription());
            this.txtNumbersOfItems.setText(String.valueOf(item.getNumberOfItems()));
            this.date = item.getDate();
            this.txtDate.setText(this.date.toString());
        }

        this.btnDone.setOnClickListener(this);
        this.btnDate.setOnClickListener(this);

    }

    // endregion

    // region 4. Other methods

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    // endregion

    // region 5. Listener

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_done) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_RESULT_TEXT, new Item(
                    R.drawable.ic_box_blue,
                    this.txtName.getText().toString(),
                    this.txtDescription.getText().toString(),
                    Integer.parseInt(this.txtNumbersOfItems.getText().toString()),
                    this.date
            ));

            setResult(RESULT_OK, resultIntent);
            finish();
        } else if (view.getId() == R.id.btn_date) {
            showDatePickerDialog();
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        this.date = new MyDate(year, month, dayOfMonth);
        this.txtDate.setText(this.date.toString());
    }

    // endregion
}