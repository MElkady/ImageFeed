package me.elkady.imagefeed.history;

import java.util.List;

import me.elkady.imagefeed.BaseContract;
import me.elkady.imagefeed.models.SearchTerm;

/**
 * Created by MAK on 5/22/17.
 */

public interface HistoryContract {
    interface Presenter extends BaseContract.BasePresenter {
        void loadHistory();
    }

    interface View extends BaseContract.BaseView<Presenter> {
        void displayHistoryItems(List<SearchTerm> searchTerms);
    }
}
