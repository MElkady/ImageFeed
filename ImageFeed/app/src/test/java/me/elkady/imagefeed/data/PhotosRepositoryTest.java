package me.elkady.imagefeed.data;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import me.elkady.imagefeed.data.PhotosRepository;
import me.elkady.imagefeed.data.PhotosRepositoryImpl;
import me.elkady.imagefeed.data.network.GooglePlusPhotoSource;
import me.elkady.imagefeed.data.network.InstagramPhotosSource;
import me.elkady.imagefeed.data.network.TwitterPhotosSource;
import me.elkady.imagefeed.models.PhotoItem;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by MAK on 5/25/17.
 */

public class PhotosRepositoryTest {
    private PhotosRepository photosRepository;

    @Mock
    private InstagramPhotosSource mInstagramPhotosSource;

    @Mock
    private GooglePlusPhotoSource mGooglePlusPhotoSource;

    @Mock
    private TwitterPhotosSource mTwitterPhotosSource;

    @Mock
    private PhotosRepository.OnPhotosReady onPhotosReady;

    @Captor
    private ArgumentCaptor<PhotosRepository.OnPhotosReady> onPhotosReadyArgumentCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        photosRepository = new PhotosRepositoryImpl(mTwitterPhotosSource, mInstagramPhotosSource, mGooglePlusPhotoSource);
    }

    @Test
    public void testLoadData() {
        String text = "test";
        List<PhotoItem> photoItems = new ArrayList<>();
        photosRepository.searchPhotos(text, onPhotosReady);
        verify(mTwitterPhotosSource).searchPhotos(eq(text), onPhotosReadyArgumentCaptor.capture());
        onPhotosReadyArgumentCaptor.getValue().onPhotosReady(photoItems);

        verify(mInstagramPhotosSource).searchPhotos(eq(text), onPhotosReadyArgumentCaptor.capture());
        onPhotosReadyArgumentCaptor.getValue().onPhotosReady(photoItems);

        verify(mGooglePlusPhotoSource).searchPhotos(eq(text), onPhotosReadyArgumentCaptor.capture());
        onPhotosReadyArgumentCaptor.getValue().onPhotosReady(photoItems);
    }
}
