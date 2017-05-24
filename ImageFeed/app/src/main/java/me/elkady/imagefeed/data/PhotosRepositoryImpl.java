package me.elkady.imagefeed.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.elkady.imagefeed.data.network.GooglePlusPhotoSource;
import me.elkady.imagefeed.data.network.InstagramPhotosSource;
import me.elkady.imagefeed.data.network.TwitterPhotosSource;
import me.elkady.imagefeed.models.PhotoItem;

/**
 * Created by MAK on 5/21/17.
 */

public class PhotosRepositoryImpl implements PhotosRepository {
    private final TwitterPhotosSource mTwitterPhotosSource;
    private final InstagramPhotosSource mInstagramPhotosSource;
    private final GooglePlusPhotoSource mGooglePlusPhotoSource;

    public PhotosRepositoryImpl(){
        this.mInstagramPhotosSource = new InstagramPhotosSource();
        this.mTwitterPhotosSource = new TwitterPhotosSource();
        this.mGooglePlusPhotoSource = new GooglePlusPhotoSource();
    }

    public void searchPhotos(final String text, final PhotosRepository.OnPhotosReady onPhotosReady) {
        final List<PhotoItem> photoItems = new ArrayList<>();

        mTwitterPhotosSource.searchPhotos(text, new PhotosRepository.OnPhotosReady() {
            @Override
            public void onPhotosReady(List<PhotoItem> photos) {
                photoItems.addAll(photos);

                mInstagramPhotosSource.searchPhotos(text, new PhotosRepository.OnPhotosReady() {
                    @Override
                    public void onPhotosReady(List<PhotoItem> photos) {
                        photoItems.addAll(photos);

                        mGooglePlusPhotoSource.searchPhotos(text, new OnPhotosReady() {
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

            @Override
            public void onError(Throwable t) {
                onPhotosReady.onError(t);
            }
        });
    }
}
