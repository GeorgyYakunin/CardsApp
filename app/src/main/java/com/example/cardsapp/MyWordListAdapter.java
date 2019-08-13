package com.example.cardsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class MyWordListAdapter extends RecyclerView.Adapter<MyWordListAdapter.MyViewHolder> {

    private DBHelper mDBHelper;
    private ArrayList<WordListItem> mWordList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvWordName;
        private TextView tvWordTranslate;

        public MyViewHolder(View v) {
            super(v);
            tvWordName = (TextView) v.findViewById(R.id.tvWordName);
            tvWordTranslate = (TextView) v.findViewById(R.id.tvWordTranslate);
        }
    }

    public MyWordListAdapter(DBHelper mDBHelper, int categoryId, Context context) {
        this.mDBHelper = mDBHelper;
        mWordList = mDBHelper.getWordList(categoryId);
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
        holder.tvWordName.setText(mWordList.get(position).getWordName());
        holder.tvWordTranslate.setText(mWordList.get(position).getWordTranslate());
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }
}