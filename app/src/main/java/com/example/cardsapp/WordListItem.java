package com.example.cardsapp;

public class WordListItem {

    private int mCategoryId;
    private String mWordName;
    private String mWordTranslate;

    public WordListItem(int categoryId, String wordName, String wordTranslate){
        mCategoryId = categoryId;
        mWordName = wordName;
        mWordTranslate = wordTranslate;

    }

    public String getWordName() {
        return mWordName;
    }

    public String getWordTranslate() {
        return mWordTranslate;
    }
}
