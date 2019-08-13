package com.example.cardsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

public class MyCategoryListAdapter extends RecyclerView.Adapter<MyCategoryListAdapter.MyViewHolder> {

//    private static DialogBuilder mDialogBuilder;
    private static Context context;
    private DBHelper dbHelper;
    private ArrayList<CategoryListItem> innerCategoryList;



//    public  static void setDBuilder(DialogBuilder db) { mDialogBuilder = db;}

    public MyCategoryListAdapter(Context context, DBHelper dbHelper) {
        this.dbHelper = dbHelper;
        this.context = context;
        innerCategoryList = dbHelper.getCategoryList();
        Collections.reverse(innerCategoryList);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCategoryItemName;
        private Button addWord;
        private Button startTraining;
        private Button deleteCategory;
        private int categoryId;

        private void getCategoryId(int categoryId){
            this.categoryId = categoryId;
        }

        public MyViewHolder(View v) {
            super(v);
            tvCategoryItemName = (TextView) v.findViewById(R.id.tvCategoryName);
            addWord = (Button) v.findViewById(R.id.addWord);
            deleteCategory = (Button) v.findViewById(R.id.deleteCategory);
            startTraining = (Button) v.findViewById(R.id.startTraining);

            startTraining.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(context, TrainingActivity.class);
                context.startActivity(intent);
                }
            });

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CategoryWordListActivity.class);
                    intent.putExtra("categoryId", categoryId);
                    context.startActivity(intent);
                }
            });

        }
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
        holder.tvCategoryItemName.setText(innerCategoryList.get(position).getCategoryName());
        holder.getCategoryId(innerCategoryList.get(position).getCategoryId());
        holder.addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Старая реализация добавления слова - вызов диалогового окна
//                mDialogBuilder.showAddWordDialog(mDataHelper.getCategoryItemList().get(p).getCategoryId());
                //Новая реализация
                //Вызов активити для добавления одного или нескольких слов сразу
//                Intent intent = new Intent(context, AddWordsActivity.class);
//                intent.putExtra(Добавить передачу id категории слов);
//                context.startActivity(intent);
            }
        });
        holder.deleteCategory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("TEST", "deleteCategory.setOnClickListener");
                mDialogBuilder.showDeleteCategoryDialog(innerCategoryList.get(p).getCategoryId());
            }
        });
    }
     public void updateData(String name){
         int lastID = 0;
         for (CategoryListItem item : innerCategoryList )  {
             if (item.getCategoryId() > lastID){
                 lastID = item.getCategoryId();
             }
         }
         innerCategoryList.add(0, new CategoryListItem(lastID + 1, name));
         notifyItemInserted(0);
     }

    @Override
    public int getItemCount() {
        return innerCategoryList.size();
    }
}