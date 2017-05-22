package me.elkady.imagefeed.data;

import java.util.List;

import me.elkady.imagefeed.models.PhotoItem;

/**
 * Created by MAK on 5/21/17.
 */

public interface PhotoSource {
    void searchPhotos(String text, OnPhotosReady onPhotosReady);

    interface OnPhotosReady {
        void onPhotosReady(List<PhotoItem> photos);
        void onError(Throwable t);
    }
}
