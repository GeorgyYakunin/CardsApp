package com.example.cardsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button mCreateNewCategoryButton;
    private DBHelper mDBHelper;
    private DialogBuilder mDialogBuilder;
    private RecyclerView mCategoryListRecycler;
    private RecyclerView.Adapter mCategoryListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDBHelper = new DBHelper(this);

        mCategoryListRecycler = (RecyclerView) findViewById(R.id.rvCategoriesList);
//        mCategoryListRecycler.setHasFixedSize(false);

        mLayoutManager = new LinearLayoutManager(this);
        mCategoryListRecycler.setLayoutManager(mLayoutManager);
        mCategoryListAdapter = new MyCategoryListAdapter(this, mDBHelper);


        mDialogBuilder = new DialogBuilder(this, mCategoryListAdapter, mCategoryListRecycler);
        mCategoryListRecycler.setAdapter(mCategoryListAdapter);

        mCreateNewCategoryButton = findViewById(R.id.addNewCategory);
        mCreateNewCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogBuilder.showAddCategoryDialog();
            }
        });

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
            case R.id.mMenuItem2:
                mDBHelper.readCategoryListFromDB();
            case R.id.mMenuItem3:
                mDBHelper.readWordListFromDB();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

