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

        Cursor c = db.query(dbHelper.CATEGORY_TABLE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                String categoryName = c.getString(c.getColumnIndex(dbHelper.CATEGORY_NAME));
                int categoryId = c.getInt(c.getColumnIndex(dbHelper.CATEGORY_ID));
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

                cv.put(dbHelper.CATEGORY_NAME, name);

                db.insert(dbHelper.CATEGORY_TABLE_NAME, null, cv);
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
        final EditText newWordCategoryId = (EditText) addWordDialogView.findViewById(R.id.newWordCategoryId);
        newWordDialog = new AlertDialog.Builder(MainActivity.this);
        newWordDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                String name = newWordName.getText().toString();
                String translate = newWordTranslate.getText().toString();
                String categoryId = newWordCategoryId.getText().toString();

                cv.put(dbHelper.WORD_NAME, name);
                cv.put(dbHelper.WORD_TRANSLATE, translate);
                cv.put(dbHelper.WORD_CATEGORY_ID, categoryId);

                db.insert(dbHelper.WORD_TABLE_NAME, null, cv);
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
                Cursor c = db.query(dbHelper.CATEGORY_TABLE_NAME, null, null, null, null, null, null);
                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex(dbHelper.CATEGORY_ID);
                    int nameColIndex = c.getColumnIndex(dbHelper.CATEGORY_NAME);

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
            case R.id.item3:
                SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                Cursor c2 = db2.query(dbHelper.WORD_TABLE_NAME, null, null, null, null, null, null);
                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c2.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c2.getColumnIndex(dbHelper.WORD_ID);
                    int nameColIndex = c2.getColumnIndex(dbHelper.WORD_NAME);
                    int translateColIndex = c2.getColumnIndex(dbHelper.WORD_TRANSLATE);
                    int categoryColIndex = c2.getColumnIndex(dbHelper.WORD_CATEGORY_ID);

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c2.getInt(idColIndex)
                                        + ", name = " + c2.getString(nameColIndex)
                                        + ", translate = " + c2.getString(translateColIndex)
                                        + " , category = " + c2.getString(categoryColIndex)

                        );
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c2.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c2.close();
                dbHelper.close();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

    //Data Base
class DBHelper extends SQLiteOpenHelper {
    public static final String CATEGORY_TABLE_NAME = "list_of_categories";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_ID = "category_id";

    public static final String WORD_TABLE_NAME = "list_of_words";
    public static final String WORD_NAME = "word_name";
    public static final String WORD_TRANSLATE = "word_translate";
    public static final String WORD_CATEGORY_ID = "word_category_id";
    public static final String WORD_ID = "word_id";

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

}
