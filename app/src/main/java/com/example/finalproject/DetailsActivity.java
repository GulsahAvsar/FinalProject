package com.example.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class DetailsActivity extends AppCompatActivity {

    private NewsItem newsItem;
    private FavoritesManager favoritesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        WebView webView = findViewById(R.id.webView);


        webView.getSettings().setJavaScriptEnabled(true);

        NewsItem newsItem = getIntent().getParcelableExtra("newsItem");

        if (newsItem != null) {

            String link = newsItem.getLink();
            webView.loadUrl(link);


            webView.setWebViewClient(new WebViewClient());
        }
        favoritesManager = new FavoritesManager(this);


        String link = getIntent().getStringExtra("link");
        webView.loadUrl(link);


        webView.setWebViewClient(new WebViewClient());


        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String date = getIntent().getStringExtra("date");



        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewDescription = findViewById(R.id.textViewDescription);
        TextView textViewDate = findViewById(R.id.textViewDate);
        TextView textViewLink = findViewById(R.id.textViewLink);

        textViewTitle.setText(title);
        textViewDescription.setText(description);
        textViewDate.setText(date);
        textViewLink.setText(link);



        addToFavoritesList(newsItem);


        Toast.makeText(this, "Article added to favorites", Toast.LENGTH_SHORT).show();
    }

    public void addToFavorites(View view) {
        favoritesManager.addFavorite(newsItem);
        showSnackbar("Article added to favorites");
    }

    public void removeFromFavorites(View view) {
        favoritesManager.removeFavorite(newsItem);
        showSnackbar("Article removed from favorites");
    }

    private void showSnackbar(String message) {

        Snackbar.make(findViewById(R.id.container), message, Snackbar.LENGTH_SHORT).show();
    }


    private void addToFavoritesList(NewsItem newsItem) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> favoritesSet = preferences.getStringSet("favorites", new HashSet<>());


        Gson gson = new Gson();
        String newsItemJson = gson.toJson(newsItem);


        favoritesSet.add(newsItemJson);


        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet("favorites", favoritesSet);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_help) {
            showHelpDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help")
                .setMessage("Add your instructions here...")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle OK button click if needed
                    }
                })
                .show();
    }
}