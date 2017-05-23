package me.elkady.imagefeed.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import me.elkady.imagefeed.models.SearchTerm;

@Database(entities = {SearchTerm.class}, version = 1)
abstract class AppDatabase extends RoomDatabase {
    public abstract HistoryDAO historyDao();
}
