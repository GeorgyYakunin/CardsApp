package com.example.cardsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class CategoryWordListActivity extends AppCompatActivity {

    private DBHelper mDBHelper;
    private RecyclerView mWordListRecycler;
    private RecyclerView.Adapter mWordListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_word_list_);

        Intent intent = getIntent();
        int categoryId = intent.getIntExtra("categoryId", 0);

        mDBHelper = new DBHelper(this);

        //recyclerView var
        mWordListRecycler = (RecyclerView) findViewById(R.id.rvWordList);
        mWordListRecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mWordListRecycler.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mWordListAdapter = new MyWordListAdapter(mDBHelper, categoryId, this);
        mWordListRecycler.setAdapter(mWordListAdapter);
    }
}
