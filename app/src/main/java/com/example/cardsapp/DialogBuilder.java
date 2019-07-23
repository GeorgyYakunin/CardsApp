package com.example.cardsapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class DialogBuilder {

    private View addWordDialogView;
    private View addCategoryDialogView;
    private AlertDialog.Builder newCategoryDialog;
    private AlertDialog.Builder newWordDialog;
    private DBHelper mDBHelper;
    private LayoutInflater inflater;

    public DialogBuilder(Context context){
        mDBHelper = new DBHelper(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        createCategoryDialogWindow(context);
        createNewWordDialogWindow(context);
    }

    private void createCategoryDialogWindow(Context context){
        addCategoryDialogView = inflater.inflate(R.layout.dialog_new_category, null);
        final EditText newCategoryName = (EditText) addCategoryDialogView.findViewById(R.id.newCategoryName);
        newCategoryDialog = new AlertDialog.Builder(context);
        newCategoryDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = mDBHelper.getReadableDatabase();
                String name = newCategoryName.getText().toString();
                cv.put(mDBHelper.CATEGORY_NAME, name);
                db.insert(mDBHelper.CATEGORY_TABLE_NAME, null, cv);
                mDBHelper.close();
            }
        });
        newCategoryDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

    }

    private void createNewWordDialogWindow(Context context) {
        addWordDialogView = inflater.inflate(R.layout.dialog_new_word, null);
        newWordDialog = new AlertDialog.Builder(context);
    }

    public void showAddCategoryDialog() {
                //Fix window recall error
                if(addCategoryDialogView.getParent() != null) {
                    ((ViewGroup)addCategoryDialogView.getParent()).removeView(addCategoryDialogView);
                }
                //Call dialog window
                newCategoryDialog.setView(addCategoryDialogView);
                newCategoryDialog.show();
    }

    public void showAddWordDialog(int categoryId) {
        final int categoryID = categoryId;

        //Fix window recall error
        if(addWordDialogView.getParent() != null) {
            ((ViewGroup)addWordDialogView.getParent()).removeView(addWordDialogView);
        }
        //Call dialog window
        newWordDialog.setView(addWordDialogView);

        final EditText newWordName = (EditText) addWordDialogView.findViewById(R.id.newWordName);
        final EditText newWordTranslate = (EditText) addWordDialogView.findViewById(R.id.newWordTranslate);
        newWordDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = mDBHelper.getReadableDatabase();
                String name = newWordName.getText().toString();
                String translate = newWordTranslate.getText().toString();
                String id = String.valueOf(categoryID);
                cv.put(mDBHelper.WORD_NAME, name);
                cv.put(mDBHelper.WORD_TRANSLATE, translate);
                cv.put(mDBHelper.WORD_CATEGORY_ID, id);
                db.insert(mDBHelper.WORD_TABLE_NAME, null, cv);
                mDBHelper.close();
            }
        });
        newWordDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        newWordDialog.show();
    }
}