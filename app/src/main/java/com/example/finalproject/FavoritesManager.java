package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class FavoritesManager {

    private SQLiteDatabase database;

    public FavoritesManager(Context context) {
        FavoriteArticleDbHelper dbHelper = new FavoriteArticleDbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public List<NewsItem> getFavorites() {
        List<NewsItem> favorites = new ArrayList<>();

        String[] projection = {
                FavoriteArticleContract.ArticleEntry.COLUMN_TITLE,
                FavoriteArticleContract.ArticleEntry.COLUMN_DESCRIPTION,
                FavoriteArticleContract.ArticleEntry.COLUMN_DATE,
                FavoriteArticleContract.ArticleEntry.COLUMN_LINK
        };

        Cursor cursor = database.query(
                FavoriteArticleContract.ArticleEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteArticleContract.ArticleEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteArticleContract.ArticleEntry.COLUMN_DESCRIPTION));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteArticleContract.ArticleEntry.COLUMN_DATE));
            String link = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteArticleContract.ArticleEntry.COLUMN_LINK));

            NewsItem newsItem = new NewsItem(title, description, date, link);
            favorites.add(newsItem);
        }

        cursor.close();
        return favorites;
    }

    public void addFavorite(NewsItem newsItem) {
        ContentValues values = new ContentValues();
        values.put(FavoriteArticleContract.ArticleEntry.COLUMN_TITLE, newsItem.getTitle());
        values.put(FavoriteArticleContract.ArticleEntry.COLUMN_DESCRIPTION, newsItem.getDescription());
        values.put(FavoriteArticleContract.ArticleEntry.COLUMN_DATE, newsItem.getDate());
        values.put(FavoriteArticleContract.ArticleEntry.COLUMN_LINK, newsItem.getLink());

        database.insert(FavoriteArticleContract.ArticleEntry.TABLE_NAME, null, values);
    }

    public void removeFavorite(NewsItem newsItem) {
        String selection = FavoriteArticleContract.ArticleEntry.COLUMN_LINK + " LIKE ?";
        String[] selectionArgs = { newsItem.getLink() };
        database.delete(FavoriteArticleContract.ArticleEntry.TABLE_NAME, selection, selectionArgs);
    }
}


