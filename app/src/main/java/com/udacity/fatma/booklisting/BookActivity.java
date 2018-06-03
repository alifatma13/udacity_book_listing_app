package com.udacity.fatma.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>, View.OnClickListener {

    private static final String LOG_TAG = BookActivity.class.getName();
    private static final String listSize = "10";
    /**
     * Constant value for the book loader ID.
     */
    private static final int BOOK_LOADER_ID = 1;
    private String bookName;
    private ListView bookListView;
    /**
     * URL for Books data from the Google Book API
     */
    private String REQUEST_URL;
    /**
     * Adapter for the list of books
     */
    private BookAdapter mAdapter;

    /**
     * View that is displayed when the list is empty
     */
    private TextView mEmptyStateView;

    private ImageView search;
    private EditText searchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
        search = findViewById(R.id.search_icon);
        searchBox = findViewById(R.id.search);
        search.setOnClickListener(this);
        //searchBox.getText();

        // Find a reference to the {@link ListView} in the layout
        bookListView = findViewById(R.id.list);

        mEmptyStateView = findViewById(R.id.empty_view);

        bookListView.setEmptyView(mEmptyStateView);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        // Get details on the currently active default data network
        if (connMgr.getActiveNetworkInfo() != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch the book's name from the search box
            if (!searchBox.getText().toString().equals("")) {
                // give message t user regarding the search
                mEmptyStateView.setText(R.string.book_search);
                // fetch the book name
                bookName = searchBox.getText().toString();
                //update the URL
                updateRequestURL(bookName);
                // Reload the loader on orientation change
                getLoaderManager().initLoader(BOOK_LOADER_ID, null, this);
            } else {
                // display a quote on first time search
                View loadingIndicator = findViewById(R.id.loading_indicator);
                loadingIndicator.setVisibility(View.GONE);
                mEmptyStateView.setText(R.string.new_search);
            }

        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mEmptyStateView.setText(R.string.no_internet_connection);
        }
    }


    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.VISIBLE);
        return new BookLoader(this, REQUEST_URL);
    }


    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No Books found."
        mEmptyStateView.setText(R.string.book_search);

        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            updateUi(books);
        } else {
            // Set empty state text to display "No Books found."
            mEmptyStateView.setText(R.string.no_books);

            // Clear the adapter of previous data
            mAdapter.clear();

        }


    }

    private void updateUi(List<Book> books) {
        mAdapter.addAll(books);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.search_icon:
                // destory the previous search result present in the loader
                getLoaderManager().destroyLoader(BOOK_LOADER_ID);

                // fetch the book's name from the search box
                bookName = searchBox.getText().toString();

                //Udate the book name in the query string
                updateRequestURL(bookName);

                // Get a reference to the ConnectivityManager to check state of network connectivity
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                // Get details on the currently active default data network
                NetworkInfo networkInfo = null;
                if (connMgr.getActiveNetworkInfo() != null) {
                    networkInfo = connMgr.getActiveNetworkInfo();
                }
                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {
                    // Get a reference to the LoaderManager, in order to interact with loaders.
                    LoaderManager loaderManager = getLoaderManager();
                    // Initialize the loader.
                    loaderManager.initLoader(BOOK_LOADER_ID, null, this);

                } else {
                    // Otherwise, display error
                    // Hiding loading indicator so error message will be visible
                    View loadingIndicator = findViewById(R.id.loading_indicator);
                    loadingIndicator.setVisibility(View.GONE);

                    // Update empty state with no connection error message
                    mEmptyStateView.setText(R.string.no_internet_connection);
                }
                break;
        }
    }

    private void updateRequestURL(String bookName) {
        // create the query string for the API call
        REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=" + bookName + "&maxResults=" + listSize;
    }

    // In order to change the background according to screen orientation
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getLoaderManager().initLoader(BOOK_LOADER_ID, null, this);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getLoaderManager().initLoader(BOOK_LOADER_ID, null, this);
        }
    }
}
