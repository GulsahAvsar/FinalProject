package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewNewsHeadlines;
    private ProgressBar progressBarLoading;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listViewNewsHeadlines = findViewById(R.id.listViewNewsHeadlines);
        progressBarLoading = findViewById(R.id.progressBarLoading);


        FetchNewsTask fetchNewsTask = new FetchNewsTask();
        fetchNewsTask.execute();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("BBC News");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MyFragment())
                .commit();

    }

    private class FetchNewsTask extends AsyncTask<Void, Void, List<NewsItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<NewsItem> doInBackground(Void... params) {
            return fetchNewsData();
        }

        @Override
        protected void onPostExecute(List<NewsItem> newsItems) {
            super.onPostExecute(newsItems);
            progressBarLoading.setVisibility(View.GONE);


            NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this, newsItems);
            listViewNewsHeadlines.setAdapter(newsAdapter);


            listViewNewsHeadlines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    NewsItem selectedNewsItem = newsItems.get(position);


                    Toast.makeText(MainActivity.this, selectedNewsItem.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void displayNewsHeadlines(List<NewsItem> newsItems) {


        listViewNewsHeadlines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsItem selectedNewsItem = newsItems.get(position);


                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);


                intent.putExtra("title", selectedNewsItem.getTitle());
                intent.putExtra("description", selectedNewsItem.getDescription());
                intent.putExtra("date", selectedNewsItem.getDate());
                intent.putExtra("link", selectedNewsItem.getLink());


                startActivity(intent);
            }
        });
    }

    private List<NewsItem> fetchNewsData() {
        List<NewsItem> newsItems = new ArrayList<>();

        try {
            URL url = new URL("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlFactoryObject.newPullParser();
            xmlPullParser.setInput(inputStream, null);

            int eventType = xmlPullParser.getEventType();
            String title = null;
            String description = null;
            String date = null;
            String link = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xmlPullParser.getName();
                if (eventType == XmlPullParser.START_TAG) {
                    if (tagName.equalsIgnoreCase("item")) {
                        title = null;
                        description = null;
                        date = null;
                        link = null;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (tagName.equalsIgnoreCase("title")) {
                        title = xmlPullParser.getText();
                    } else if (tagName.equalsIgnoreCase("description")) {
                        description = xmlPullParser.getText();
                    } else if (tagName.equalsIgnoreCase("pubDate")) {
                        date = xmlPullParser.getText();
                    } else if (tagName.equalsIgnoreCase("link")) {
                        link = xmlPullParser.getText();
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (tagName.equalsIgnoreCase("item")) {
                        NewsItem newsItem = new NewsItem(title, description, date, link);
                        newsItems.add(newsItem);
                    }
                }
                eventType = xmlPullParser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newsItems;
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

