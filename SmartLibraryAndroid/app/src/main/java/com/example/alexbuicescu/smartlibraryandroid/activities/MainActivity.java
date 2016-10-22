package com.example.alexbuicescu.smartlibraryandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.alexbuicescu.smartlibraryandroid.R;
import com.example.alexbuicescu.smartlibraryandroid.managers.BooksManager;
import com.example.alexbuicescu.smartlibraryandroid.pojos.NavigationDrawer;
import com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus.MainBooksMessage;
import com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus.SearchMessage;
import com.example.alexbuicescu.smartlibraryandroid.rest.RestClient;
import com.example.alexbuicescu.smartlibraryandroid.views.BooksListAdapter;
import com.example.alexbuicescu.smartlibraryandroid.views.SmoothActionBarDrawerToggle;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText searchEditText;
    private ListView booksListView;
    private BooksListAdapter booksAdapter;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private SmoothActionBarDrawerToggle navigationToggle;
    private AppCompatImageView searchImageView;
    private RelativeLayout searchRelativeLayout;
    private AppCompatImageView searchBackButton;
//    private MaterialSearchView searchView;

    public static final int NAVDRAWER_LAUNCH_DELAY = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
        loadBooks();
    }

    private void loadBooks() {
        RestClient.getInstance(MainActivity.this).BOOKS_CALL();
    }

    private void initLayout() {
        initToolbar();
        initNavigationView();

        searchRelativeLayout = (RelativeLayout) findViewById(R.id.activity_main_search_relativelayout);
        searchBackButton = (AppCompatImageView) findViewById(R.id.activity_main_back_button_search_button);
        searchBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSearch();
            }
        });
        searchImageView = (AppCompatImageView) findViewById(R.id.activity_main_search_imageview);
        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearch();
//                RestClient.getInstance(MainActivity.this).SEARCH_CALL(searchEditText.getText().toString());
            }
        });
        searchEditText = (EditText) findViewById(R.id.activity_main_search_edittext);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchQuery = searchEditText.getText().toString();
                if (searchQuery.length() >= 3) {
                    RestClient.getInstance(MainActivity.this).SEARCH_CALL(searchQuery);
                }
                else if (searchQuery.length() == 0) {
                    booksAdapter.setCurrentItems(BooksManager.getInstance().getMainBooksResponses());
                }
            }
        });
        booksAdapter = new BooksListAdapter(MainActivity.this);
        booksListView = (ListView) findViewById(R.id.activity_main_all_books_listview);
        booksListView.setAdapter(booksAdapter);
    }

    private void hideSearch() {
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha_full_to_none);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                searchRelativeLayout.setVisibility(View.GONE);
                searchEditText.setText("");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        searchRelativeLayout.startAnimation(animation);
        booksAdapter.setCurrentItems(BooksManager.getInstance().getMainBooksResponses());
    }

    private void showSearch() {
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha_none_to_full);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                searchEditText.requestFocus();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        searchRelativeLayout.startAnimation(animation);
        searchRelativeLayout.setVisibility(View.VISIBLE);
    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.activity_main_navigationview);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                drawerLayout.closeDrawers();
                if (menuItem.getItemId() == R.id.navigation_drawer_menu_main_action) {
                    return false;
                }

                navigationButtonClicked(menuItem.getItemId());
                return false;
            }
        });
        navigationToggle = new SmoothActionBarDrawerToggle(
                (AppCompatActivity) MainActivity.this,
                drawerLayout,
                R.string.drawer_main_screen,
                R.string.drawer_borrowed
        );
        navigationToggle.syncState();
        navigationToggle.setDrawerIndicatorEnabled(true);
    }
    
    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        // enable ActionBar app icon to behave as action to toggle nav drawer

        try {
            ((AppCompatActivity) MainActivity.this).setSupportActionBar(toolbar);
            ((AppCompatActivity) MainActivity.this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) MainActivity.this).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity) MainActivity.this).getSupportActionBar().setTitle("");
        }
        catch (Exception e) {

        }
    }

    private boolean navigationButtonClicked(final int position) {

        if (NavigationDrawer.navigationDrawerItemsActivitiesMap.get(position) != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AppCompatActivity activityToOpen = NavigationDrawer.navigationDrawerItemsActivitiesMap.get(position);
                    Intent intent = new Intent(MainActivity.this, activityToOpen.getClass());
                    MainActivity.this.startActivity(intent);
                }
            }, NAVDRAWER_LAUNCH_DELAY);
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (navigationToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEvent(MainBooksMessage message) {
        Log.i(TAG, "onEvent MainBooksMessage: " + message.isSuccess());
        if (message.isSuccess()) {
            booksAdapter.setCurrentItems(BooksManager.getInstance().getMainBooksResponses());
            Log.i(TAG, "onEvent: " + BooksManager.getInstance().getMainBooksResponses().size());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
//            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//            if (matches != null && matches.size() > 0) {
//                String searchWrd = matches.get(0);
//                if (!TextUtils.isEmpty(searchWrd)) {
//                    searchView.setQuery(searchWrd, false);
//                }
//            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe
    public void onEvent(SearchMessage message) {
        if (message.isSuccess()) {
            booksAdapter.setCurrentItems(BooksManager.getInstance().getSearchedResultBooks());
        }
    }
}
