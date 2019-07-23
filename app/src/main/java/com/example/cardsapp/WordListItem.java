package com.example.cardsapp;

public class WordListItem {

    private int myCategoryId;
    private String myWordName;
    private String myWordTranslate;

    public WordListItem(int categoryId, String wordName, String wordTranslate){
        myCategoryId = categoryId;
        myWordName = wordName;
        myWordTranslate = wordTranslate;

    }

    public String getWordName() {
        return myWordName;
    }

    public String getWordTranslate() {
        return myWordTranslate;
    }
}
