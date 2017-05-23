package me.elkady.imagefeed.history;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.elkady.imagefeed.R;
import me.elkady.imagefeed.data.HistoryRepositoryImpl;
import me.elkady.imagefeed.models.SearchTerm;

public class HistoryActivity extends AppCompatActivity implements HistoryContract.View {
    private List<SearchTerm> mSearchTerms;
    private HistoryContract.Presenter mPresenter;

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.rv_history) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setting up presenter
        mPresenter = new HistoryPresenter(new HistoryRepositoryImpl());
        mPresenter.attachView(this);

        // Setting up recycler view
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadHistory();
    }

    @Override
    protected void onDestroy() {
        this.mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showErrorMessage(@StringRes int error) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setCancelable(false);
        b.setMessage(error);
        b.setTitle(R.string.error);
        b.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        b.create().show();
    }

    @Override
    public void displayHistoryItems(List<SearchTerm> searchTerms) {
        this.mSearchTerms = searchTerms;
    }

    class SearchTermViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView) TextView mTextTerm;

        SearchTermViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(SearchTerm searchTerm) {
            mTextTerm.setText(searchTerm.getKeyword());
        }
    }

    private RecyclerView.Adapter<SearchTermViewHolder> adapter = new RecyclerView.Adapter<SearchTermViewHolder>() {
        @Override
        public SearchTermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SearchTermViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false));
        }

        @Override
        public void onBindViewHolder(SearchTermViewHolder holder, int position) {
            if(mSearchTerms != null && mSearchTerms.size() > position) {
                holder.bindView(mSearchTerms.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return (mSearchTerms != null)? mSearchTerms.size() : 0;
        }
    };
}
