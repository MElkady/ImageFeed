package me.elkady.imagefeed.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by MAK on 5/22/17.
 */

@Entity
public class SearchTerm {
    @PrimaryKey
    private int id;

    private String keyword;
    private long timestamp;
}
