package com.example.shoppinglist.testdata;

import com.example.shoppinglist.R;
import com.example.shoppinglist.model.Item;
import com.example.shoppinglist.model.MyDate;

import java.util.ArrayList;

public class TestData {

    // region 0. Constants
    private static final int MAX_ITEM = 15;
    // endregion

    // region 1. Constructor

    public TestData(){
        //Nothing to do
    }

    // endregion

    // region 2. Test data

    public static synchronized ArrayList<Item> getTestItems(){
        ArrayList<Item> testList = new ArrayList<>();

        for (int i = 0; i < MAX_ITEM; i++) {
            Item item = new Item();
            item.setTextName("name" + i);
            item.setTextDescription("description" + i);
            item.setImageResource((i%2 == 0 ? R.drawable.ic_box_red : R.drawable.ic_box_blue));
            item.setNumberOfItems(i);
            item.setDate(new MyDate(2020, 12, i+1));

            testList.add(item);
        }

        return testList;
    }

    // endregion
}
