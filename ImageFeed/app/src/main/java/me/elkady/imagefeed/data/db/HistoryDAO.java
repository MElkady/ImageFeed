package me.elkady.imagefeed.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import me.elkady.imagefeed.models.SearchTerm;

/**
 * Created by MAK on 5/22/17.
 */

@Dao
public interface HistoryDAO {
    @Query("SELECT * FROM SearchTerm order by timestamp DESC")
    List<SearchTerm> getHistoryItems();

    @Insert
    void insertItem(SearchTerm searchTerm);
}
