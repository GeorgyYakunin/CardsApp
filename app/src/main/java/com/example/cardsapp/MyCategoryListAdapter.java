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

public class MyCategoryListAdapter extends RecyclerView.Adapter<MyCategoryListAdapter.MyViewHolder> {

    private DialogBuilder mDialogBuilder;
//    private DBHelper dbHelper;
    private ArrayList<CategoryListItem> categoryList;
    private static Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategoryItemName;
        Button addWord;
        Button startTraining;
        int categoryId;

        private void getCategoryId(int categoryId){
            this.categoryId = categoryId;
        }

        public MyViewHolder(View v) {
            super(v);
            tvCategoryItemName = (TextView) v.findViewById(R.id.tvCategoryName);
            addWord = (Button) v.findViewById(R.id.addWord);
            startTraining = (Button) v.findViewById(R.id.startTraining);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CategoryWordListActivity.class);
                    //Log.d("categoryIdTest", String.valueOf(categoryId));
                    intent.putExtra("categoryId", categoryId);
                    context.startActivity(intent);
                }
            });
        }
    }

    public MyCategoryListAdapter(DBHelper mDBHelper, DialogBuilder mDialogBuilder, Context context) {
//        this.dbHelper = dbHelper;
        this.mDialogBuilder = mDialogBuilder;
        categoryList = mDBHelper.getCategoryList();
        this.context = context;
    }

    @Override
    public MyCategoryListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int p = position;
        holder.tvCategoryItemName.setText(categoryList.get(position).getCategoryName());
        holder.getCategoryId(categoryList.get(position).getCategoryId());
        holder.addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Старая реализация добавления слова - вызов диалогового окна
                mDialogBuilder.showAddWordDialog(categoryList.get(p).getCategoryId());
                //Новая реализация (пока что в комменте)
                //Вызов активити для добавления одного или нескольких слов сразу
//                Intent intent = new Intent(context, AddWordsActivity.class);
//                intent.putExtra(Добавить передачу id категории слов);
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