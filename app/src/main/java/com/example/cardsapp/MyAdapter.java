package com.example.cardsapp;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<WordCategory> categoryFromDB;
    AlertDialog.Builder newWordDialog;
    View addWordDialogView;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategoryItem, categoryId;
        Button addWord, readCategoryWords;


        public MyViewHolder(View v) {
            super(v);
            tvCategoryItem = (TextView) v.findViewById(R.id.tvWordItem);
            categoryId = (TextView) v.findViewById(R.id.categoryId);
            addWord = (Button) v.findViewById(R.id.addWord);
            readCategoryWords = (Button) v.findViewById(R.id.readCategoryWords);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<WordCategory> testArray, AlertDialog.Builder addWordDialog, View addWordDialogView) {
        categoryFromDB = testArray;
        newWordDialog = addWordDialog;
        this.addWordDialogView = addWordDialogView;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final int p = position;

        holder.tvCategoryItem.setText(categoryFromDB.get(position).getCategoryName());
        holder.addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newWordCategoryId = (EditText) addWordDialogView.findViewById(R.id.newWordCategoryId);
                if(addWordDialogView.getParent() != null) {
                    ((ViewGroup)addWordDialogView.getParent()).removeView(addWordDialogView);
                }
                newWordDialog.setView(addWordDialogView);
                newWordDialog.show();
                int categoryId = categoryFromDB.get(p).getCategoryId();
                newWordCategoryId.setText(String.valueOf(categoryId));
            }
        });
        holder.readCategoryWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return categoryFromDB.size();
    }
}