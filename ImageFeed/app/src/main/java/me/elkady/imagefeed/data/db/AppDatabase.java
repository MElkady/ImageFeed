package me.elkady.imagefeed.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import me.elkady.imagefeed.models.SearchTerm;

/**
 * Created by MAK on 5/22/17.
 */

@Database(entities = {SearchTerm.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HistoryDAO historyDao();
}
