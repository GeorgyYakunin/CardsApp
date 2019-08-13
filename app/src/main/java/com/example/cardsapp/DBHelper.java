package com.example.cardsapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static java.lang.String.valueOf;

public class DBHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "READ_FROM_DB";
    public static final int DATABASE_VERSION = 2;
    public static final String CATEGORY_TABLE_NAME = "list_of_categories";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_ID = "category_id";

    public static final String WORD_TABLE_NAME = "list_of_words";
    public static final String WORD_NAME = "word_name";
    public static final String WORD_TRANSLATE = "word_translate";
    public static final String WORD_CATEGORY_ID = "word_category_id";
    public static final String WORD_ID = "word_id";

    private ArrayList<CategoryListItem> mCategoryList = new ArrayList<>();
    private ArrayList<WordListItem> mWordList = new ArrayList<>();

    public DBHelper(Context context) {
        super(context, CATEGORY_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Rows for create table
        String categoryListTableCreation = "create table "
                + CATEGORY_TABLE_NAME + " ("
                + CATEGORY_ID + " integer primary key autoincrement, "
                + CATEGORY_NAME + " text not null"
                + ");";

        String wordListTableCreation = "create table "
                + WORD_TABLE_NAME + " ("
                + WORD_ID + " integer primary key autoincrement, "
                + WORD_NAME + " text not null, "
                + WORD_TRANSLATE + " text not null, "
                + WORD_CATEGORY_ID + " integer, "
                + "FOREIGN KEY (" + WORD_CATEGORY_ID + ") REFERENCES " + CATEGORY_TABLE_NAME + "(" + CATEGORY_ID + ")"
                + ");";

        //Call creating table
        db.execSQL(categoryListTableCreation);
        db.execSQL(wordListTableCreation);
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

    public ArrayList<CategoryListItem> getCategoryList() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        try {
            c = db.query(CATEGORY_TABLE_NAME, null, null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    String categoryName = c.getString(c.getColumnIndex(CATEGORY_NAME));
                    int categoryId = c.getInt(c.getColumnIndex(CATEGORY_ID));
                    mCategoryList.add(new CategoryListItem(categoryId, categoryName));
                } while (c.moveToNext());
                c.close();
                db.close();
            }
        } finally {

        }
        return mCategoryList;
    }

    public ArrayList<WordListItem> getWordList(int categoryId) {
        String[] columns = new String[]{"word_name", "word_translate"};
        String selection = "word_category_id = " + categoryId;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(WORD_TABLE_NAME, columns, selection, null, null, null, null);;
        try {
            if (c.moveToFirst()) {
                do {
                    String wordName = c.getString(c.getColumnIndex("word_name"));
                    String wordTranslate = c.getString(c.getColumnIndex("word_translate"));
                    mWordList.add(new WordListItem(categoryId, wordName, wordTranslate));
                }while(c.moveToNext());
            }
            else{
                c.close();
                db.close();
            }
        } finally {
            c.close();
            db.close();
        }
        return mWordList;
    }

    public void readCategoryListFromDB() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
        try {
            c = db.query(CATEGORY_TABLE_NAME, null, null, null, null, null, null);
            if (c.moveToFirst()) {
                int idColIndex = c.getColumnIndex(CATEGORY_ID);
                int nameColIndex = c.getColumnIndex(CATEGORY_NAME);

                do {
                    Log.d(LOG_TAG,
                            "ID = " + c.getInt(idColIndex) +
                                    ", name = " + c.getString(nameColIndex));
                } while (c.moveToNext());
                c.close();
                db.close();
            } else {
                Log.d(LOG_TAG, "0 rows");
                c.close();
                db.close();
            }
        } finally {

        }
    }

    public void readWordListFromDB() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
        try {
            c = db.query(WORD_TABLE_NAME, null, null, null, null, null, null);
            if (c.moveToFirst()) {
                int idColIndex = c.getColumnIndex(WORD_ID);
                int nameColIndex = c.getColumnIndex(WORD_NAME);
                int translateColIndex = c.getColumnIndex(WORD_TRANSLATE);
                int categoryColIndex = c.getColumnIndex(WORD_CATEGORY_ID);
                do {
                    Log.d("test change",
                            "ID = " + c.getInt(idColIndex)
                                    + ", name = " + c.getString(nameColIndex)
                                    + ", translate = " + c.getString(translateColIndex)
                                    + " , category = " + c.getString(categoryColIndex)
                    );
                } while (c.moveToNext());
                c.close();
                db.close();
            } else {
                Log.d(LOG_TAG, "0 rows");
                c.close();
                db.close();
            }
        } finally {

        }
    }

    public void deleteCategoryFromDB(int categoryID){
        String l= new String(valueOf(categoryID));
        Log.d("DB info", l);
    }
}