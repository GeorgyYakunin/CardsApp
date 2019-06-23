package com.example.cardsapp;

public class WordCategory {
    private int categoryId;
    private String categoryName;

    public  WordCategory (int categoryId, String categoryName){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
