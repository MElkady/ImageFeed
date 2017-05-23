package me.elkady.imagefeed.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.elkady.imagefeed.R;
import me.elkady.imagefeed.history.HistoryActivity;
import me.elkady.imagefeed.models.SearchTerm;

public class SearchActivity extends AppCompatActivity {
    public static final String ARG_SEARCH_TERM = "argSearchTerm";

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);


        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.contentArea);
        if(searchFragment == null) {
            searchFragment = SearchFragment.getInstance();
        }
        if(getIntent().hasExtra(ARG_SEARCH_TERM)) {
            SearchTerm searchTerm = (SearchTerm) getIntent().getExtras().get(ARG_SEARCH_TERM);
            Bundle bundle = new Bundle();
            bundle.putSerializable(SearchFragment.ARG_SEARCH_TERM, searchTerm);
            searchFragment.setArguments(bundle);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.contentArea, searchFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_item_history) {
            Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
