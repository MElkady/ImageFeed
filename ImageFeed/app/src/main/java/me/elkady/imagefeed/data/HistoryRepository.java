package me.elkady.imagefeed.data;

import java.util.List;

import me.elkady.imagefeed.models.SearchTerm;

/**
 * Created by MAK on 5/22/17.
 */

public interface HistoryRepository {
    void addHistoryItem(SearchTerm searchTerm);

    void loadHistory(OnHistoryReady onHistoryReady);

    interface OnHistoryReady {
        void onHistoryReady(List<SearchTerm> searchTerms);
        void onError(Throwable t);
    }
}
