package com.example.finalproject;

import android.provider.BaseColumns;

public class FavoriteArticleContract {

    private FavoriteArticleContract() {
    }

    public static class ArticleEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite_articles";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_LINK = "link";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_TITLE + " TEXT," +
                        COLUMN_DESCRIPTION + " TEXT," +
                        COLUMN_DATE + " TEXT," +
                        COLUMN_LINK + " TEXT)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}

