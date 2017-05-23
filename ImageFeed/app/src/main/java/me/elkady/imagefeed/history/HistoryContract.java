package me.elkady.imagefeed.history;

import java.util.List;

import me.elkady.imagefeed.BaseContract;
import me.elkady.imagefeed.models.SearchTerm;

/**
 * Created by MAK on 5/22/17.
 */

public interface HistoryContract {
    interface Presenter extends BaseContract.BasePresenter<View> {
        void loadHistory();
        void executeSearch(SearchTerm searchTerm);
    }

    interface View extends BaseContract.BaseView {
        void displayHistoryItems(List<SearchTerm> searchTerms);
        void searchForKeyword(SearchTerm searchTerm);
    }
}
