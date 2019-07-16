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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private mDialogBuilder dialogBuilder;
//    private DBHelper dbHelper;
    ArrayList<WordCategory> categoryList;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategoryItem;
        Button addWord;
        Button startTraining;

        public MyViewHolder(View v) {
            super(v);
            tvCategoryItem = (TextView) v.findViewById(R.id.tvWordItem);
            addWord = (Button) v.findViewById(R.id.addWord);
            startTraining = (Button) v.findViewById(R.id.startTraining);
        }
    }

    public MyAdapter(DBHelper dbHelper, mDialogBuilder dialogBuilder, Context context) {
//        this.dbHelper = dbHelper;
        this.dialogBuilder = dialogBuilder;
        categoryList = dbHelper.getCategoryList();
        this.context = context;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int p = position;
        holder.tvCategoryItem.setText(categoryList.get(position).getCategoryName());
        holder.addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Старая реализация добавления слова - вызов диалогового окна
                dialogBuilder.showAddWordDialog(categoryList.get(p).getCategoryId());
                //Новая реализация (пока что в комменте)
                //Вызов активити для добавления одного или нескольких слов сразу
//                Intent intent = new Intent(context, AddWordsActivity.class);
//                context.startActivity(intent);
            }
        });
        holder.startTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TrainingActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}