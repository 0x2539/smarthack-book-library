package com.example.alexbuicescu.smartlibraryandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

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

        searchEditText = (EditText) findViewById(R.id.activity_main_search_edittext);
        booksAdapter = new BooksListAdapter(MainActivity.this);
        booksListView = (ListView) findViewById(R.id.activity_main_all_books_listview);
        booksListView.setAdapter(booksAdapter);
//        searchView = (MaterialSearchView) findViewById(R.id.search_view);
//        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                //Do some magic
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                //Do some magic
//                return false;
//            }
//        });
//
//        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
//            @Override
//            public void onSearchViewShown() {
//                //Do some magic
//            }
//
//            @Override
//            public void onSearchViewClosed() {
//                //Do some magic
//            }
//        });
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

        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_search) {
            RestClient.getInstance(MainActivity.this).SEARCH_CALL(searchEditText.getText().toString());
        }

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
