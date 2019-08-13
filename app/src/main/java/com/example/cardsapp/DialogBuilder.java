package com.example.cardsapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class DialogBuilder {

    private View mWordDialogView;
    private View mCategoryDialogView;
    private View mDeleteCategoryDialogView;
    private AlertDialog.Builder mCategoryDialog;
    private AlertDialog.Builder mDeleteCategoryDialog;
    private AlertDialog.Builder mWordDialog;
    private DBHelper mDBHelper;
    private LayoutInflater mInflater;
//    private MyCategoryListAdapter mCategoryListAdapter;
//    private RecyclerView mCategoryListRecycler;


    public DialogBuilder(Context context, RecyclerView.Adapter mCategoryListAdapter, RecyclerView mCategoryListRecycler){
        mDBHelper = new DBHelper(context);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mCategoryListAdapter = (MyCategoryListAdapter) mCategoryListAdapter;
        this.mCategoryListRecycler = mCategoryListRecycler;
        createCategoryDialogWindow(context);
        createNewWordDialogWindow(context);
        createDeleteDialogWindow(context);
    }

    private void createCategoryDialogWindow(Context context){
        mCategoryDialogView = mInflater.inflate(R.layout.dialog_new_category, null);
        final EditText newCategoryName = (EditText) mCategoryDialogView.findViewById(R.id.newCategoryName);
        mCategoryDialog = new AlertDialog.Builder(context);
        mCategoryDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //insert Data in base
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = mDBHelper.getReadableDatabase();
                String name = newCategoryName.getText().toString();
                cv.put(mDBHelper.CATEGORY_NAME, name);
                db.insert(mDBHelper.CATEGORY_TABLE_NAME, null, cv);
                mDBHelper.close();

                //notify the adapter of the change
                mCategoryListAdapter.updateData(name);
                mCategoryListAdapter.notifyItemInserted(0);
                mCategoryListRecycler.scrollToPosition(0);

            }
        });
        mCategoryDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

    }

    private void createNewWordDialogWindow(Context context) {
        mWordDialogView = mInflater.inflate(R.layout.dialog_new_word, null);
        mWordDialog = new AlertDialog.Builder(context);

    }

    private void createDeleteDialogWindow(Context context) {
        mDeleteCategoryDialogView = mInflater.inflate(R.layout.dialog_delete_category, null);
        mDeleteCategoryDialog = new AlertDialog.Builder(context);

    }

    public void showAddCategoryDialog() {
                //Fix window recall error
                if(mCategoryDialogView.getParent() != null) {
                    ((ViewGroup) mCategoryDialogView.getParent()).removeView(mCategoryDialogView);
                }
                //Call dialog window
                mCategoryDialog.setView(mCategoryDialogView);
                mCategoryDialog.show();
    }

    public void showDeleteCategoryDialog(int categoryID){
        final int ID = categoryID;

        Log.d("TEST", "showDeleteCategoryDialog");
        //Fix window recall error
        if(mDeleteCategoryDialogView.getParent() != null) {
            ((ViewGroup) mDeleteCategoryDialogView.getParent()).removeView(mDeleteCategoryDialogView);
        }
        //Call dialog window
        mDeleteCategoryDialog.setView(mDeleteCategoryDialogView);

        mDeleteCategoryDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDBHelper.deleteCategoryFromDB(ID);
            }
        });
        mDeleteCategoryDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mDeleteCategoryDialog.show();

    }

}

//    public void showAddWordDialog(int categoryId) {
//        final int categoryID = categoryId;
//
//        //Fix window recall error
//        if(mWordDialogView.getParent() != null) {
//            ((ViewGroup) mWordDialogView.getParent()).removeView(mWordDialogView);
//        }
//        //Call dialog window
//        mWordDialog.setView(mWordDialogView);
//
//        final EditText newWordName = (EditText) mWordDialogView.findViewById(R.id.newWordName);
//        final EditText newWordTranslate = (EditText) mWordDialogView.findViewById(R.id.newWordTranslate);
//        mWordDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ContentValues cv = new ContentValues();
//                SQLiteDatabase db = mDBHelper.getReadableDatabase();
//                String name = newWordName.getText().toString();
//                String translate = newWordTranslate.getText().toString();
//                String id = String.valueOf(categoryID);
//                cv.put(mDBHelper.WORD_NAME, name);
//                cv.put(mDBHelper.WORD_TRANSLATE, translate);
//                cv.put(mDBHelper.WORD_CATEGORY_ID, id);
//                db.insert(mDBHelper.WORD_TABLE_NAME, null, cv);
//                mDBHelper.close();
//            }
//        });
//        mWordDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        mWordDialog.show();
//    }

