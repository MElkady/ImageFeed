package me.elkady.imagefeed.search;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import me.elkady.imagefeed.models.PhotoItem;
import me.elkady.imagefeed.models.SearchTerm;

public class SearchFragment extends Fragment implements SearchContract.View {
    public static final String ARG_SEARCH_TERM = "argSearchTerm";
    private SearchContract.Presenter mPresenter;
    private List<PhotoItem> mPhotoItems;


    @BindView(R.id.rv_images) RecyclerView mRecyclerView;
    @BindView(R.id.tv_search) TextView mSearchView;
    @BindView(R.id.btn_search) ImageButton mSearchButton;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.ll_noImages) LinearLayout mNoImagesLayout;

    public static SearchFragment getInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        // Setting up presenter
        mPresenter = new SearchPresenter(new PhotosRepositoryImpl(), new HistoryRepositoryImpl());    // TODO someday we should use dependency injection...
        mPresenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        // Setting up recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

        if(this.getArguments() != null && this.getArguments().containsKey(ARG_SEARCH_TERM)) {
            SearchTerm searchTerm = (SearchTerm) this.getArguments().getSerializable(ARG_SEARCH_TERM);
            mSearchView.setText(searchTerm.getKeyword());
            mPresenter.search(searchTerm.getKeyword());
        }

        return view;
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
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
        View v = getActivity().getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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
        mPhotoItems = photoItems;
        if(photoItems != null && photoItems.size() > 0) {
            mAdapter.notifyDataSetChanged();
            mRecyclerView.setVisibility(View.VISIBLE);
            mNoImagesLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErrorMessage(@StringRes int error) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
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
        @BindView(R.id.iv_image)
        ImageView mIVImage;
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

    private final RecyclerView.Adapter<PhotoImageViewHolder> mAdapter = new RecyclerView.Adapter<PhotoImageViewHolder>() {
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
