package me.elkady.imagefeed.history;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.elkady.imagefeed.R;
import me.elkady.imagefeed.data.HistoryRepositoryImpl;
import me.elkady.imagefeed.models.SearchTerm;


public class HistoryFragment extends Fragment implements HistoryContract.View {
    private List<SearchTerm> mSearchTerms;
    private HistoryContract.Presenter mPresenter;

    @BindView(R.id.rv_history) RecyclerView mRecyclerView;

    public static HistoryFragment getInstance() {
        return new HistoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        // Setting up presenter
        mPresenter = new HistoryPresenter(new HistoryRepositoryImpl());
        mPresenter.attachView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadHistory();
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);

        // Setting up recycler view
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        return view;
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
    public void displayHistoryItems(List<SearchTerm> searchTerms) {
        this.mSearchTerms = searchTerms;
        adapter.notifyDataSetChanged();
    }

    class SearchTermViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView)
        TextView mTextTerm;

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
