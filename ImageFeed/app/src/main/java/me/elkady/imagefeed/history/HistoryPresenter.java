package me.elkady.imagefeed.history;

import java.util.List;

import me.elkady.imagefeed.R;
import me.elkady.imagefeed.data.HistoryRepository;
import me.elkady.imagefeed.data.HistoryRepositoryImpl;
import me.elkady.imagefeed.models.SearchTerm;

/**
 * Created by MAK on 5/22/17.
 */

public class HistoryPresenter implements HistoryContract.Presenter {
    private final HistoryContract.View mView;
    private final HistoryRepository mHistoryRepository;

    public HistoryPresenter(HistoryContract.View view, HistoryRepository historyRepository) {
        this.mView = view;
        this.mHistoryRepository = historyRepository;
    }

    @Override
    public void loadHistory() {
        mHistoryRepository.loadHistory(new HistoryRepository.OnHistoryReady() {
            @Override
            public void onHistoryReady(List<SearchTerm> searchTerms) {
                mView.displayHistoryItems(searchTerms);
            }

            @Override
            public void onError(Throwable t) {
                mView.showErrorMessage(R.string.an_error_happen);
            }
        });
    }
}
