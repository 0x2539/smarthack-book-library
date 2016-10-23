package com.example.alexbuicescu.smartlibraryandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alexbuicescu.smartlibraryandroid.R;
import com.example.alexbuicescu.smartlibraryandroid.managers.BooksManager;
import com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus.BorrowedMessage;
import com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus.RecommendedMessage;
import com.example.alexbuicescu.smartlibraryandroid.rest.RestClient;
import com.example.alexbuicescu.smartlibraryandroid.views.BooksListAdapter;

import org.greenrobot.eventbus.Subscribe;

public class RecommendedActivity extends BaseActivity {

    private static final String TAG = RecommendedActivity.class.getSimpleName();
    private ListView booksListView;
    private BooksListAdapter booksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended);
        initLayout();
        loadBooks();
    }

    private void loadBooks() {
        RestClient.getInstance(RecommendedActivity.this).RECOMMENDED_CALL();
    }

    private void initLayout() {
        initToolbar();

        booksAdapter = new BooksListAdapter(RecommendedActivity.this);
        booksListView = (ListView) findViewById(R.id.activity_recommended_listview);
        booksListView.setAdapter(booksAdapter);
        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RecommendedActivity.this, BookDetailsActivity.class);
                intent.putExtra(BookDetailsActivity.KEY_BOOK_ID, booksAdapter.getCurrentItems().get(position).getBookId());
                RecommendedActivity.this.startActivity(intent);
            }
        });
    }

    private void initToolbar() {
        try {
            ((AppCompatActivity) RecommendedActivity.this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) RecommendedActivity.this).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity) RecommendedActivity.this).getSupportActionBar().setTitle("Books You May Like");
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recommended, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEvent(RecommendedMessage message) {
        if (message.isSuccess()) {
            booksAdapter.setCurrentItems(BooksManager.getInstance().getRecommendedBooks());
        }
    }
}
