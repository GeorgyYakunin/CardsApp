package com.example.cardsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class MyWordListAdapter extends RecyclerView.Adapter<MyWordListAdapter.MyViewHolder> {

    private DBHelper dbHelper;
    ArrayList<WordListItem> wordList;
    private static Context context;
    private int categoryId;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvWordName;
        TextView tvWordTranslate;

        public MyViewHolder(View v) {
            super(v);
            tvWordName = (TextView) v.findViewById(R.id.tvWordName);
            tvWordTranslate = (TextView) v.findViewById(R.id.tvWordTranslate);
        }
    }

    public MyWordListAdapter(DBHelper dbHelper, int categoryId, Context context) {
        this.dbHelper = dbHelper;
        this.categoryId = categoryId;
        wordList = dbHelper.getWordList(categoryId);
        this.context = context;
    }

    @Override
    public MyWordListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_card, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvWordName.setText(wordList.get(position).getWordName());
        holder.tvWordTranslate.setText(wordList.get(position).getWordTranslate());
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }
}