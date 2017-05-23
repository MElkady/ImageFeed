package me.elkady.imagefeed.data.db;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import me.elkady.imagefeed.models.SearchTerm;

/**
 * Created by MAK on 5/22/17.
 */

public class HistoryDBSource {
    private static HistoryDBSource sInstance;
    private final AppDatabase db;

    private HistoryDBSource(Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class, "database-name").build();
    }
    public static HistoryDBSource getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new HistoryDBSource(context);
        }
        return sInstance;
    }
    public List<SearchTerm> getHistoryItems() {
        return db.historyDao().getHistoryItems();
    }

    public void saveSearchTerm(SearchTerm searchTerm) {
        db.historyDao().insertItem(searchTerm);
    }
}
