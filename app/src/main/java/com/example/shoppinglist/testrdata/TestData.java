package com.example.shoppinglist.testrdata;

import com.example.shoppinglist.R;
import com.example.shoppinglist.model.Item;

import java.util.ArrayList;

public class TestData {

    // region 0. Constants
    private static final int MAX_ITEM = 20;
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

            testList.add(item);
        }

        return testList;
    }

    // endregion
}
