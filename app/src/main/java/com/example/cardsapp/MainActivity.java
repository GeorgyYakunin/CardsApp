package com.example.cardsapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    AlertDialog.Builder newCategoryDialog, newWordDialog;
    Button addNewCategory;
    View addCategoryDialogView, addWordDialogView;
    DBHelper dbHelper;
    final String LOG_TAG = "myLogs";
    ArrayList<WordCategory> testArray = new ArrayList<>();

    private RecyclerView rvCategories;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query("listOfCategories", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                String categoryName = c.getString(c.getColumnIndex("nameOfCategory"));
                int categoryId = c.getInt(c.getColumnIndex("id"));
                testArray.add(new WordCategory(categoryId, categoryName));
            }while(c.moveToNext());
        }else {
            Log.d(LOG_TAG, "0 rows");
        }
        c.close();
        dbHelper.close();
        addNewCategory = findViewById(R.id.addNewCategory);
        addNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fix window recall error
                if(addCategoryDialogView.getParent() != null) {
                    ((ViewGroup)addCategoryDialogView.getParent()).removeView(addCategoryDialogView);
                }
                //Call dialog window
                newCategoryDialog.setView(addCategoryDialogView);
                newCategoryDialog.show();
            }
        });

        //Create category Dialog window
        addCategoryDialogView = getLayoutInflater().inflate(R.layout.dialog_new_category, null);
        final EditText newCategoryName = (EditText) addCategoryDialogView.findViewById(R.id.newCategoryName);
        newCategoryDialog = new AlertDialog.Builder(MainActivity.this);
        newCategoryDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ContentValues cv = new ContentValues();
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                String name = newCategoryName.getText().toString();

                cv.put("nameOfCategory", name);

                db.insert("listOfCategories", null, cv);
                dbHelper.close();
            }
        });
        newCategoryDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        //Create word Dialog window
        addWordDialogView = getLayoutInflater().inflate(R.layout.dialog_new_word, null);
        final EditText newWordName = (EditText) addWordDialogView.findViewById(R.id.newWordName);
        final EditText newWordTranslate = (EditText) addWordDialogView.findViewById(R.id.newWordTranslate);
        newWordDialog = new AlertDialog.Builder(MainActivity.this);
        newWordDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                String name = newWordName.getText().toString();
                String translate = newWordTranslate.getText().toString();

                cv.put("nameOfWord", name);
                cv.put("translateOfWord", translate);

                db.insert("listOfWords", null, cv);
                dbHelper.close();

            }
        });
        newWordDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        //recyclerView var
        rvCategories = (RecyclerView) findViewById(R.id.rvWords);
        rvCategories.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        rvCategories.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(testArray, newWordDialog, addWordDialogView);
        rvCategories.setAdapter(mAdapter);


    }
    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.item2:
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor c = db.query("listOfCategories", null, null, null, null, null, null);
                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("nameOfCategory");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", name = " + c.getString(nameColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();
                dbHelper.close();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

    //Data Base
class DBHelper extends SQLiteOpenHelper {
    public static final String CATEGORY_TABLE_NAME = "listOfCategories";
    public static final String COLUMN_CATEGORY_NAME = "nameOfCategory";

    public static final String WORD_TABLE_NAME = "listOfWords";
    public static final String COLUMN_WORD_NAME = "nameOfWord";
    public static final String COLUMN_WORD_TRANSLATE = "translateOfWord";
    public static final String COLUMN_WORD_CATEGORY_ID = "categoryOfWord";

    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, CATEGORY_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Row to create table
        String SQL_CREATE_CATEGORY_LISTS_TABLE = "create table " + CATEGORY_TABLE_NAME + " ("
                + "id integer primary key autoincrement, "
                + COLUMN_CATEGORY_NAME
                + ");";

        String SQL_CREATE_WORD_LIST_TABLE = "create table " + WORD_TABLE_NAME + " ("
                + "id integer primary key autoincrement, "
                + COLUMN_WORD_NAME + ", "
                + COLUMN_WORD_TRANSLATE + ", "
                + "integer " + COLUMN_WORD_CATEGORY_ID
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

}
