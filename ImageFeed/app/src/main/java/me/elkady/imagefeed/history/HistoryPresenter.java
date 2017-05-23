package me.elkady.imagefeed.history;

import java.util.List;

import me.elkady.imagefeed.R;
import me.elkady.imagefeed.data.HistoryRepository;
import me.elkady.imagefeed.models.SearchTerm;

public class HistoryPresenter implements HistoryContract.Presenter {
    private HistoryContract.View mView;
    private final HistoryRepository mHistoryRepository;

    public HistoryPresenter(HistoryRepository historyRepository) {
        this.mHistoryRepository = historyRepository;
    }

    @Override
    public void loadHistory() {
        mHistoryRepository.loadHistory(new HistoryRepository.OnHistoryReady() {
            @Override
            public void onHistoryReady(List<SearchTerm> searchTerms) {
                if(mView != null) {
                    mView.displayHistoryItems(searchTerms);
                }
            }

            @Override
            public void onError(Throwable t) {
                if(mView != null) {
                    mView.showErrorMessage(R.string.an_error_happen);
                }
            }
        });
    }

    @Override
    public void attachView(HistoryContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
