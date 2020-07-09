package com.example.shoppinglist.logic.listener;

import android.view.View;
import android.widget.Toast;

import com.example.shoppinglist.R;
import com.example.shoppinglist.gui.activity.MainActivity;

public class MainActivityListener implements View.OnClickListener {

    // region 0. Constants

    // endregion

    // region 1. Delc and Init

    private MainActivity activity;

    // endregion

    // region 2. Constructor

    public MainActivityListener(MainActivity activity) {
        this.activity = activity;
    }

    // endregion

    // region 3. Implemented methods

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.floating_action_button:
                Toast.makeText(this.activity, "ADD", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // endregion

}
