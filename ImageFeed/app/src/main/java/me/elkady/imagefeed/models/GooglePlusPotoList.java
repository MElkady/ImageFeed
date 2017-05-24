package me.elkady.imagefeed.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAK on 5/24/17.
 */

public class GooglePlusPotoList {
    @SerializedName("items")
    private List<GooglePlusPhotoItem> media;

    @NonNull
    public List<GooglePlusPhotoItem> getMedia() {
        return (media == null)? new ArrayList<GooglePlusPhotoItem>() : media;
    }

    public void setMedia(List<GooglePlusPhotoItem> media) {
        this.media = media;
    }
}
