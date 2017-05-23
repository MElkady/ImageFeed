package me.elkady.imagefeed.history;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.elkady.imagefeed.R;

public class HistoryActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        HistoryFragment historyFragment = (HistoryFragment) getSupportFragmentManager().findFragmentById(R.id.contentArea);
        if(historyFragment == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contentArea, HistoryFragment.getInstance()).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
