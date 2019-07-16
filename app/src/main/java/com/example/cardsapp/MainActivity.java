package com.example.cardsapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Button addNewCategory;
    private DBHelper dbHelper;
    private mDialogBuilder dialogBuilder;

    private final String LOG_TAG = "myLogs";

    private RecyclerView rvCategories;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = MainActivity.this;
        dialogBuilder = new mDialogBuilder(context);
        dbHelper = new DBHelper(this);

        addNewCategory = findViewById(R.id.addNewCategory);
        addNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.showAddCategoryDialog();
            }
        });

        //recyclerView var
        rvCategories = (RecyclerView) findViewById(R.id.rvWords);
        rvCategories.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        rvCategories.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(dbHelper, dialogBuilder, this);
        rvCategories.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        switch (menuItemId) {
            case R.id.item2:
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor c = db.query(dbHelper.CATEGORY_TABLE_NAME, null, null, null, null, null, null);
                if (c.moveToFirst()) {
                    int idColIndex = c.getColumnIndex(dbHelper.CATEGORY_ID);
                    int nameColIndex = c.getColumnIndex(dbHelper.CATEGORY_NAME);

                    do {
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", name = " + c.getString(nameColIndex));
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();
                dbHelper.close();
                return true;
            case R.id.item3:
                SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                Cursor c2 = db2.query(dbHelper.WORD_TABLE_NAME, null, null, null, null, null, null);
                if (c2.moveToFirst()) {
                    int idColIndex = c2.getColumnIndex(dbHelper.WORD_ID);
                    int nameColIndex = c2.getColumnIndex(dbHelper.WORD_NAME);
                    int translateColIndex = c2.getColumnIndex(dbHelper.WORD_TRANSLATE);
                    int categoryColIndex = c2.getColumnIndex(dbHelper.WORD_CATEGORY_ID);
                    do {
                        Log.d(LOG_TAG,
                                "ID = " + c2.getInt(idColIndex)
                                        + ", name = " + c2.getString(nameColIndex)
                                        + ", translate = " + c2.getString(translateColIndex)
                                        + " , category = " + c2.getString(categoryColIndex)
                        );
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

