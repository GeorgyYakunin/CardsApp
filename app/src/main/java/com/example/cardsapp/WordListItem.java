package com.example.cardsapp;

public class WordListItem {

    private int categoryId;
    private String wordName;
    private String wordTranslate;

    public WordListItem(int categoryId, String wordName, String wordTranslate){
        this.categoryId = categoryId;
        this.wordName = wordName;
        this.wordTranslate = wordTranslate;

    }

    public String getWordName() {
        return wordName;
    }

    public String getWordTranslate() {
        return wordTranslate;
    }
}
