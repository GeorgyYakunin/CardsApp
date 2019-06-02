package com.example.cardsapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity{


    AlertDialog.Builder dlgAddCategory;
    Button btnAddCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dlgAddCategory = new AlertDialog.Builder(MainActivity.this);
        dlgAddCategory.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dlgAddCategory.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        btnAddCategory = (Button) findViewById(R.id.btnAddCategory);
        btnAddCategory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                View mView = getLayoutInflater().inflate(R.layout.add_category_window, null);
                dlgAddCategory.setView(mView);
                dlgAddCategory.show();
            }
        });
    }

}


