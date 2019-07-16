package com.example.cardsapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String CATEGORY_TABLE_NAME = "list_of_categories";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_ID = "category_id";

    public static final String WORD_TABLE_NAME = "list_of_words";
    public static final String WORD_NAME = "word_name";
    public static final String WORD_TRANSLATE = "word_translate";
    public static final String WORD_CATEGORY_ID = "word_category_id";
    public static final String WORD_ID = "word_id";

    ArrayList<WordCategory> categoryList = new ArrayList<WordCategory>();

    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, CATEGORY_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Row to create table
        String SQL_CREATE_CATEGORY_LISTS_TABLE = "create table "
                + CATEGORY_TABLE_NAME + " ( "
                + CATEGORY_ID + " integer primary key autoincrement, "
                + CATEGORY_NAME + " text not null"
                + ");";

        String SQL_CREATE_WORD_LIST_TABLE = "create table " + WORD_TABLE_NAME + " ("
                + WORD_ID + " integer primary key autoincrement, "
                + WORD_NAME + " text not null, "
                + WORD_TRANSLATE + " text not null, "
                + WORD_CATEGORY_ID + " integer,"
                + " FOREIGN KEY (" + WORD_CATEGORY_ID + ") REFERENCES " + CATEGORY_TABLE_NAME + "(" + CATEGORY_ID + ")"
                + ");";

        //Call create table
        db.execSQL(SQL_CREATE_CATEGORY_LISTS_TABLE);
        db.execSQL(SQL_CREATE_WORD_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WORD_TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WORD_TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<WordCategory> getCategoryList() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(CATEGORY_TABLE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                String categoryName = c.getString(c.getColumnIndex(CATEGORY_NAME));
                int categoryId = c.getInt(c.getColumnIndex(CATEGORY_ID));
                categoryList.add(new WordCategory(categoryId, categoryName));
            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return categoryList;
    }


}