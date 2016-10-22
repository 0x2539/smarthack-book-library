package com.example.alexbuicescu.smartlibraryandroid.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alexbuicescu.smartlibraryandroid.R;
import com.example.alexbuicescu.smartlibraryandroid.managers.BooksManager;
import com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus.LoanDateMessage;
import com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus.LoanedTogetherWithMessage;
import com.example.alexbuicescu.smartlibraryandroid.rest.RestClient;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.MainBooksResponse;
import com.example.alexbuicescu.smartlibraryandroid.views.BlurBuilder;
import com.example.alexbuicescu.smartlibraryandroid.views.OtherBooksAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.greenrobot.eventbus.Subscribe;

public class BookDetailsActivity extends BaseActivity {

    private static final String TAG = BookDetailsActivity.class.getSimpleName();
    public static final String KEY_BOOK_ID = "KEY_BOOK_ID";

    private MainBooksResponse book;

    private RecyclerView recommendedBooksRecyclerView;
    private OtherBooksAdapter recommendedBooksAdapter;

    private TextView descriptionTextView;
    private AppCompatImageView coverImageView;
    private AppCompatImageView coverImageViewBlurred;
    private Button borrowButton;
    private Button alreadyBorrowedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        if (!getExtras()) {
            finish();
            return;
        }
        initLayout();
        loadInfo();
    }

    private boolean getExtras() {
        Intent intent = getIntent();
        if (intent != null) {
            long bookId = intent.getLongExtra(KEY_BOOK_ID, -1);
            if (bookId == -1) {
                return false;
            }
            for (MainBooksResponse tempBook : BooksManager.getInstance().getMainBooksResponses()) {
                if (tempBook.getBookId() == bookId) {
                    book = tempBook;
                    return true;
                }
            }
        }
        return false;
    }

    private void loadInfo() {
        BooksManager.getInstance().setRecommendedBooks(null);
        RestClient.getInstance(BookDetailsActivity.this).LOANED_TOGETHER_WITH_CALL(book.getBookId());
        RestClient.getInstance(BookDetailsActivity.this).LOAN_DATE_CALL(book.getBookId());
    }

    private void initLayout() {
        initToolbar();

        recommendedBooksAdapter = new OtherBooksAdapter();
        recommendedBooksRecyclerView = (RecyclerView) findViewById(R.id.activity_book_details_other_books_recyclerview);
        recommendedBooksRecyclerView.setAdapter(recommendedBooksAdapter);

        descriptionTextView = (TextView) findViewById(R.id.activity_book_details_description_textview);
        descriptionTextView.setText(book.getBook().getDescription());
        borrowButton = (Button) findViewById(R.id.activity_book_details_borrow_button);
        borrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        alreadyBorrowedButton = (Button) findViewById(R.id.activity_book_details_already_borrowed_button);

        coverImageView = (AppCompatImageView) findViewById(R.id.activity_book_details_cover_imageview);
        coverImageViewBlurred = (AppCompatImageView) findViewById(R.id.activity_book_details_blurred_cover_imageview);
//        ImageLoader.getInstance().displayImage(book.getBook().getCoverUrl(), holder.coverImageView);
        ImageLoader.getInstance().loadImage(book.getBook().getCoverUrl(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                // Do whatever you want with Bitmap
                coverImageViewBlurred.setImageBitmap(BlurBuilder.blur(BookDetailsActivity.this, loadedImage));
                coverImageView.setImageBitmap(loadedImage);
            }
        });
    }

    private void initToolbar() {
        try {
            ((AppCompatActivity) BookDetailsActivity.this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) BookDetailsActivity.this).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity) BookDetailsActivity.this).getSupportActionBar().setTitle(book.getBook().getTitle());
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_details, menu);

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
    public void onEvent(LoanedTogetherWithMessage message) {
        if (message.isSuccess()) {
            recommendedBooksAdapter.setNewItems(BooksManager.getInstance().getRecommendedBooks());
        }
    }

    @Subscribe
    public void onEvent(LoanDateMessage message) {
        if (message.isSuccess()) {
            if (message.getDate() != null && !message.getDate().equals("")) {
                borrowButton.setVisibility(View.GONE);
                alreadyBorrowedButton.setVisibility(View.VISIBLE);
            }
        }
        borrowButton.setVisibility(View.VISIBLE);
        alreadyBorrowedButton.setVisibility(View.GONE);
    }
}
