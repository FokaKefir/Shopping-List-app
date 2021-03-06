package com.example.shoppinglist.gui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppinglist.R;
import com.example.shoppinglist.gui.spinner.SpinnerAdapter;
import com.example.shoppinglist.model.Item;
import com.example.shoppinglist.model.MyDate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;


public class ItemActivity extends AppCompatActivity
        implements View.OnClickListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener, TextWatcher {

    // region 0. Constants

    public static final String EXTRA_RESULT_TEXT = "result";

    // endregion

    // region 1. Decl and Init

    private EditText txtName;
    private EditText txtDescription;
    private EditText txtNumberOfItems;
    private TextView txtDate;

    private FloatingActionButton btnDone;

    private Spinner spinner;

    private MyDate date;
    private int imageResource;
    private ArrayList<Integer> spinnerList;

    // endregion

    // region 2. Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.item_activity);

        this.setTitle("Item editor");

        this.txtName = findViewById(R.id.txt_name);
        this.txtDescription = findViewById(R.id.txt_description);
        this.txtNumberOfItems = findViewById(R.id.txt_number_of_items);
        this.txtDate = findViewById(R.id.txt_date);
        this.btnDone = findViewById(R.id.fab_done);
        this.spinner = findViewById(R.id.color_spinner);

        this.date = new MyDate();
        this.imageResource = R.drawable.ic_box_blue;

        this.txtName.addTextChangedListener(this);
        this.txtNumberOfItems.addTextChangedListener(this);
        this.btnDone.setOnClickListener(this);
        this.txtDate.setOnClickListener(this);
        this.spinner.setOnItemSelectedListener(this);

        buildSpinner();
        readIntent();

    }

    // endregion

    // region 3. Other methods

    private void readIntent(){
        Intent intent = getIntent();
        Item item = intent.getParcelableExtra(MainActivity.EXTRA_TEXT);

        if (item != null && !item.isDefault()){
            this.imageResource = item.getImageResource();
            this.txtName.setText(item.getTextName());
            this.txtDescription.setText(item.getTextDescription());
            this.txtNumberOfItems.setText(String.valueOf(item.getNumberOfItems()));
            this.date = item.getDate();
            if (!this.date.isDefault()) {
                this.txtDate.setText(this.date.toString());
            } else {
                this.txtDate.setText(R.string.str_date);
            }

            this.spinner.setSelection(this.spinnerList.indexOf(this.imageResource));

            this.btnDone.show();
        } else {
            this.btnDone.hide();
        }
    }

    private void buildSpinner() {
        this.spinnerList = new ArrayList<>();
        this.spinnerList.add(R.drawable.ic_box_blue);
        this.spinnerList.add(R.drawable.ic_box_green);
        this.spinnerList.add(R.drawable.ic_box_orange);
        this.spinnerList.add(R.drawable.ic_box_red);
        this.spinnerList.add(R.drawable.ic_box_yellow);

        SpinnerAdapter adapter = new SpinnerAdapter(this, this.spinnerList);

        this.spinner.setAdapter(adapter);
    }

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

    private void sendResult(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_RESULT_TEXT, new Item(
                this.imageResource,
                this.txtName.getText().toString(),
                this.txtDescription.getText().toString(),
                Integer.parseInt(this.txtNumberOfItems.getText().toString()),
                this.date
        ));

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    // endregion

    // region 4. Implemented methods

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_done:
                sendResult();
                break;
            case R.id.txt_date:
                showDatePickerDialog();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        this.date = new MyDate(year, month+1, dayOfMonth);
        this.txtDate.setText(this.date.toString());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.imageResource = (Integer) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        String strName = this.txtName.getText().toString().trim();
        String strNumberOfItems = this.txtNumberOfItems.getText().toString().trim();

        if (!strName.isEmpty() && !strNumberOfItems.isEmpty() && Integer.parseInt(strNumberOfItems) > 0) {
            this.btnDone.show();
        } else {
            this.btnDone.hide();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    // endregion
}