package me.elkady.imagefeed;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import me.elkady.imagefeed.data.HistoryRepository;
import me.elkady.imagefeed.data.PhotosRepository;
import me.elkady.imagefeed.models.InstagramPhotoItem;
import me.elkady.imagefeed.models.PhotoItem;
import me.elkady.imagefeed.models.SearchTerm;
import me.elkady.imagefeed.models.TwitterPhotoItem;
import me.elkady.imagefeed.search.SearchContract;
import me.elkady.imagefeed.search.SearchPresenter;

import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by MAK on 5/21/17.
 */

public class SearchPresenterTest {
    @Mock
    private HistoryRepository mHistoryRepository;

    @Mock
    private PhotosRepository mPhotosRepository;

    @Mock
    private SearchContract.View mView;

    @Captor
    private ArgumentCaptor<PhotosRepository.OnPhotosReady> mPhotosRepositoryOnPhotosReady;

    @Captor
    private ArgumentCaptor<HistoryRepository.OnHistoryReady> mHistoryRepositoryOnHistoryReady;

    private SearchPresenter mPresenter;

    private final String searchText = "dubai";
    private final Matcher<SearchTerm> searchTermMatcher = new TypeSafeMatcher<SearchTerm>() {
        @Override
        protected boolean matchesSafely(SearchTerm item) {
            return searchText.equals(item.getKeyword());
        }

        @Override
        public void describeTo(Description description) {

        }
    };

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new SearchPresenter(mPhotosRepository, mHistoryRepository);
        mPresenter.attachView(mView);
    }

    @Test
    public void testSearch() {
        List<PhotoItem> photoItems = new ArrayList<>();
        photoItems.add(new InstagramPhotoItem());
        photoItems.add(new TwitterPhotoItem());

        mPresenter.search(searchText);

        verify(mView).displayLoading();
        verify(mHistoryRepository).addHistoryItem(argThat(searchTermMatcher));
        verify(mPhotosRepository).searchPhotos(eq(searchText), mPhotosRepositoryOnPhotosReady.capture());
        mPhotosRepositoryOnPhotosReady.getValue().onPhotosReady(photoItems);

        verify(mView).hideLoading();
        verify(mView).showPhotos(photoItems);
        mPresenter.detachView();
    }
}
