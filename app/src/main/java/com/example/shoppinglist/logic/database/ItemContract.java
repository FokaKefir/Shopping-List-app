package com.example.shoppinglist.logic.database;

import android.provider.BaseColumns;

public class ItemContract {

    private ItemContract() {}

    public static final class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "itemList";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_IMAGE_RESOURCE = "imageResource";
        public static final String COLUMN_NUMBER_OF_ITEMS = "numberOfItems";
        public static final String COLUMN_DATE_YEAR = "year";
        public static final String COLUMN_DATE_MONTH = "month";
        public static final String COLUMN_DATE_DAY = "day";
        public static final String COLUMN_POSITION = "positionInList";
    }

}
