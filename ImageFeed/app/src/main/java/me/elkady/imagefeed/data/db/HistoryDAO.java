package me.elkady.imagefeed.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import me.elkady.imagefeed.models.SearchTerm;

@Dao
interface HistoryDAO {
    @Query("SELECT * FROM SearchTerm order by timestamp DESC")
    List<SearchTerm> getHistoryItems();

    @Insert
    void insertItem(SearchTerm searchTerm);
}
