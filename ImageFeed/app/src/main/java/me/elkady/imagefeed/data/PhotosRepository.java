package me.elkady.imagefeed.data;

import java.util.List;

import me.elkady.imagefeed.models.PhotoItem;

/**
 * Created by MAK on 5/22/17.
 */

public interface PhotosRepository {
    void searchPhotos(String text, OnPhotosReady onPhotosReady);

    interface PhotoSource {
        void searchPhotos(String text, OnPhotosReady onPhotosReady);
    }

    interface OnPhotosReady {
        void onPhotosReady(List<PhotoItem> photos);
        void onError(Throwable t);
    }
}
