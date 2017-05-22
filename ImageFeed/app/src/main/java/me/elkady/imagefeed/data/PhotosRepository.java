package me.elkady.imagefeed.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.elkady.imagefeed.models.PhotoItem;

/**
 * Created by MAK on 5/21/17.
 */

public class PhotosRepository {
    TwitterPhotosSource mTwitterPhotosSource;
    InstagramPhotosSource mInstagramPhotosSource;

    public PhotosRepository(){
        mInstagramPhotosSource = new InstagramPhotosSource();
        mTwitterPhotosSource = new TwitterPhotosSource();
    }

    public void searchPhotos(final String text, final PhotoSource.OnPhotosReady onPhotosReady) {
        final List<PhotoItem> photoItems = new ArrayList<>();

        // TODO Save term in DB

        mTwitterPhotosSource.searchPhotos(text, new PhotoSource.OnPhotosReady() {
            @Override
            public void onPhotosReady(List<PhotoItem> photos) {
                photoItems.addAll(photos);

                mInstagramPhotosSource.searchPhotos(text, new PhotoSource.OnPhotosReady() {
                    @Override
                    public void onPhotosReady(List<PhotoItem> photos) {
                        photoItems.addAll(photos);
                        Collections.sort(photoItems, new Comparator<PhotoItem>() {
                            @Override
                            public int compare(PhotoItem t1, PhotoItem t2) {
                                return (t2.getTimestamp() > t1.getTimestamp())? 1 : -1;
                            }
                        });
                        onPhotosReady.onPhotosReady(photoItems);
                    }

                    @Override
                    public void onError(Throwable t) {
                        onPhotosReady.onError(t);
                    }
                });
            }

            @Override
            public void onError(Throwable t) {
                onPhotosReady.onError(t);
            }
        });
    }

    public void getRecentSearches() {

    }
}
