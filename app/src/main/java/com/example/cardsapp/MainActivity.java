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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    AlertDialog.Builder newCategoryDialog;
    Button btnNewCategory, TestReadDB;
    View mView;
    DBHelper dbHelper;

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        btnNewCategory = (Button) findViewById(R.id.btnNewCategory);
        btnNewCategory.setOnClickListener(this);



        //Create View Dialog window layout
        mView = getLayoutInflater().inflate(R.layout.add_category_window, null);
        final EditText newWordListName = (EditText) mView.findViewById(R.id.newWordListName);
        final EditText newWordListTranslate = (EditText) mView.findViewById(R.id.newWordListTranslate);

        //Add new word list
        newCategoryDialog = new AlertDialog.Builder(MainActivity.this);
        newCategoryDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ContentValues cv = new ContentValues();
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                String newListName = newWordListName.getText().toString();
                String newListTranslate = newWordListTranslate.getText().toString();

                cv.put("listName", newListName);
                cv.put("listTranslate", newListTranslate);

                db.insert("collectionOfCategories", null, cv);
                dbHelper.close();
            }
        });
        
        newCategoryDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        //Button for test read Data Base
        TestReadDB = (Button) findViewById(R.id.TestReadDB);
        TestReadDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor c = db.query("collectionOfCategories", null, null, null, null, null, null);
                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("listName");
                    int translateColIndex = c.getColumnIndex("listTranslate");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", name = " + c.getString(nameColIndex) +
                                        ", translate  = " + c.getString(translateColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();
                dbHelper.close();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNewCategory:
                //Fix window recall error
                if(mView.getParent() != null) {
                    ((ViewGroup)mView.getParent()).removeView(mView);
                }
                //Call dialog window
                newCategoryDialog.setView(mView);
                newCategoryDialog.show();
                break;
        }
    }
}

class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "collectionOfCategories";
    public static final String COLOMN_LIST_NAME = "listName";
    public static final String COLOMN_LIST_TRANSLATE = "listTranslate";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Row to create table
        String SQL_CREATE_WORD_LISTS_TABLE = "create table " + TABLE_NAME + " ("
                + "id integer primary key autoincrement, "
                + "listName text, "
                + "listTranslate text"
                + ");";

        //Call create table
        db.execSQL(SQL_CREATE_WORD_LISTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}



