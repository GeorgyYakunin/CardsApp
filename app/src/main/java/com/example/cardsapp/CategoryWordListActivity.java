package com.example.cardsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class CategoryWordListActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    //КОНСТАНТА CATEGORY_ID = ID категории, которую открыл пользователь

    private RecyclerView rvWords;
    private RecyclerView.Adapter mWordListAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_word_list_);

        Intent intent = getIntent();
        int categoryId = intent.getIntExtra("categoryId", 1);

        dbHelper = new DBHelper(this);


        //recyclerView var
        rvWords = (RecyclerView) findViewById(R.id.rvWordList);
        rvWords.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        rvWords.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mWordListAdapter = new MyWordListAdapter(dbHelper, categoryId, this);
        rvWords.setAdapter(mWordListAdapter);
    }
}
