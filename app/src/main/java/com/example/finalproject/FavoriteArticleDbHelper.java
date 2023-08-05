package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FavoriteArticleDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FavoriteArticles.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "news_items";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_LINK = "link";

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_LINK + " TEXT" +
            ")";

    public FavoriteArticleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FavoriteArticleContract.ArticleEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FavoriteArticleContract.ArticleEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void addNewsItem(NewsItem newsItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, newsItem.getTitle());
        values.put(COLUMN_DESCRIPTION, newsItem.getDescription());
        values.put(COLUMN_DATE, newsItem.getDate());
        values.put(COLUMN_LINK, newsItem.getLink());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
}

