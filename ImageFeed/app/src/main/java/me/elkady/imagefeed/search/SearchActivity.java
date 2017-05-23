package me.elkady.imagefeed.search;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import me.elkady.imagefeed.R;
import me.elkady.imagefeed.data.HistoryRepositoryImpl;
import me.elkady.imagefeed.data.PhotosRepositoryImpl;
import me.elkady.imagefeed.history.HistoryActivity;
import me.elkady.imagefeed.models.PhotoItem;

public class SearchActivity extends AppCompatActivity implements SearchContract.View {
    SearchContract.Presenter mPresenter;
    List<PhotoItem> mPhotoItems;

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.rv_images) RecyclerView mRecyclerView;
    @BindView(R.id.tv_search) TextView mSearchView;
    @BindView(R.id.btn_search) ImageButton mSearchButton;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        // Setting up presenter
        Object oldPresenter = getLastCustomNonConfigurationInstance();
        if(oldPresenter != null && oldPresenter instanceof SearchContract.Presenter) {
            mPresenter = (SearchContract.Presenter) oldPresenter;
            mPresenter.attachView(this);
        } else {
            mPresenter = new SearchPresenter(new PhotosRepositoryImpl(), new HistoryRepositoryImpl());    // TODO someday we should use dependency injection...
            mPresenter.attachView(this);
        }

        // Setting up recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        this.mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mPresenter;
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

    @OnEditorAction(R.id.tv_search)
    public boolean tvSearch(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            performSearch();
            return true;
        }
        return false;
    }

    @OnClick(R.id.btn_search)
    public void btnSearch(View view) {
        performSearch();
    }

    private void performSearch() {
        View v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        if(mSearchView.getText() != null && mSearchView.getText().length() > 0) {
            mPresenter.search(mSearchView.getText().toString());
        } else {
            showErrorMessage(R.string.please_enter_search_query);
        }
    }

    @Override
    public void showPhotos(List<PhotoItem> photoItems) {
        this.mPhotoItems = photoItems;
        this.mAdapter.notifyDataSetChanged();
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
    public void displayLoading() {
        mSearchButton.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mSearchButton.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }


    class PhotoImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image) ImageView mIVImage;
        @BindView(R.id.tv_caption) TextView mTVCaption;
        @BindView(R.id.tv_source) TextView mTVSource;

        PhotoImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindPhotoItem(PhotoItem photoItem) {
            mTVCaption.setText(photoItem.getCaption());
            mTVSource.setText(photoItem.getSource());
            Picasso.with(itemView.getContext()).load(photoItem.getImageUrl()).into(mIVImage);
        }
    }

    private RecyclerView.Adapter<PhotoImageViewHolder> mAdapter = new RecyclerView.Adapter<PhotoImageViewHolder>() {
        @Override
        public PhotoImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PhotoImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false));
        }

        @Override
        public void onBindViewHolder(PhotoImageViewHolder holder, int position) {
            if(mPhotoItems != null && mPhotoItems.size() > position) {
                holder.bindPhotoItem(mPhotoItems.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return (mPhotoItems != null)? mPhotoItems.size() : 0;
        }
    };
}
