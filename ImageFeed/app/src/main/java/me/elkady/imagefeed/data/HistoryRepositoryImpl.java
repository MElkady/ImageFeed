package me.elkady.imagefeed.data;

import android.os.AsyncTask;

import java.util.List;

import me.elkady.imagefeed.ImageFeedApp;
import me.elkady.imagefeed.data.db.HistoryDBSource;
import me.elkady.imagefeed.models.SearchTerm;

/**
 * Created by MAK on 5/22/17.
 */

public class HistoryRepositoryImpl implements HistoryRepository {
    public HistoryRepositoryImpl() {
    }

    public void addHistoryItem(final SearchTerm searchTerm) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                HistoryDBSource.getInstance(ImageFeedApp.getAppContext()).saveSearchTerm(searchTerm);
                return null;
            }
        };
        task.execute();
    }

    @Override
    public void loadHistory(final OnHistoryReady onHistoryReady) {
        AsyncTask<Void, Void, List<SearchTerm>> task = new AsyncTask<Void, Void, List<SearchTerm>>() {
            @Override
            protected List<SearchTerm> doInBackground(Void... voids) {
                return HistoryDBSource.getInstance(ImageFeedApp.getAppContext()).getHistoryItems();
            }

            @Override
            protected void onPostExecute(List<SearchTerm> result) {
                onHistoryReady.onHistoryReady(result);
            }
        };
        task.execute();

    }
}
