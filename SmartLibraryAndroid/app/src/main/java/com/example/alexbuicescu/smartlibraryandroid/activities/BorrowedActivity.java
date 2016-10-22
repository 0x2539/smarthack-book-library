package com.example.alexbuicescu.smartlibraryandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.alexbuicescu.smartlibraryandroid.R;
import com.example.alexbuicescu.smartlibraryandroid.managers.BooksManager;
import com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus.BorrowedMessage;
import com.example.alexbuicescu.smartlibraryandroid.rest.RestClient;
import com.example.alexbuicescu.smartlibraryandroid.views.BooksListAdapter;

import org.greenrobot.eventbus.Subscribe;

public class BorrowedActivity extends BaseActivity {

    private static final String TAG = BorrowedActivity.class.getSimpleName();
    private ListView booksListView;
    private BooksListAdapter booksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed);
        initLayout();
        loadBooks();
    }

    private void loadBooks() {
        RestClient.getInstance(BorrowedActivity.this).BORROWED_CALL();
    }

    private void initLayout() {
        initToolbar();

        booksAdapter = new BooksListAdapter(BorrowedActivity.this);
        booksListView = (ListView) findViewById(R.id.activity_borrowed_listview);
        booksListView.setAdapter(booksAdapter);
    }

    private void initToolbar() {
        try {
            ((AppCompatActivity) BorrowedActivity.this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) BorrowedActivity.this).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity) BorrowedActivity.this).getSupportActionBar().setTitle("");
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_borrowed, menu);

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
    public void onEvent(BorrowedMessage message) {
        if (message.isSuccess()) {
            booksAdapter.setCurrentItems(BooksManager.getInstance().getBorrowedBooks());
        }
    }
}
