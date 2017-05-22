package me.elkady.imagefeed.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAK on 5/22/17.
 */

public class InstagramPhotoList {
    @SerializedName("data")
    private List<InstagramPhotoItem> media;

    @NonNull
    public List<InstagramPhotoItem> getMedia() {
        return (media == null)? new ArrayList<InstagramPhotoItem>() : media;
    }

    public void setMedia(List<InstagramPhotoItem> media) {
        this.media = media;
    }
}
