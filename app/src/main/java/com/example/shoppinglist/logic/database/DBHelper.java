package com.example.shoppinglist.logic.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import com.example.shoppinglist.logic.database.ItemContract.*;

public class DBHelper extends SQLiteOpenHelper {

    // region 0. Constants

    public static final String DATABASE_NAME = "itemlist.db";
    public static final int DATABASE_VERSION = 1;

    // endregion

    // region 1. Constructor

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // endregion

    // region 2. Implemented methods

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ITEMLIST_TABLE = "CREATE TABLE " +
                ItemEntry.TABLE_NAME + " (" +
                ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ItemEntry.COLUMN_DESCRIPTION + " TEXT, " +
                ItemEntry.COLUMN_IMAGE_RESOURCE + " INTEGER NOT NULL, " +
                ItemEntry.COLUMN_NUMBER_OF_ITEMS + " INTEGER NOT NULL, " +
                ItemEntry.COLUMN_DATE_YEAR + " INTEGER NOT NULL, " +
                ItemEntry.COLUMN_DATE_MONTH + " INTEGER NOT NULL, " +
                ItemEntry.COLUMN_DATE_DAY + " INTEGER NOT NULL, " +
                ItemEntry.COLUMN_POSITION + " INTEGER NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_ITEMLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ItemEntry.TABLE_NAME);
        onCreate(db);
    }

    // endregion
}
