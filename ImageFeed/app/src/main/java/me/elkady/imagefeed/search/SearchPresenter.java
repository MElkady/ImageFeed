package me.elkady.imagefeed.search;

import java.util.List;

import me.elkady.imagefeed.R;
import me.elkady.imagefeed.data.HistoryRepository;
import me.elkady.imagefeed.data.PhotosRepository;
import me.elkady.imagefeed.models.PhotoItem;
import me.elkady.imagefeed.models.SearchTerm;

/**
 * Created by MAK on 5/21/17.
 */

public class SearchPresenter implements SearchContract.Presenter {
    private final SearchContract.View mView;
    private final PhotosRepository mPhotosRepository;
    private final HistoryRepository mHistoryRepository;


    public SearchPresenter(SearchContract.View view, PhotosRepository photosRepository, HistoryRepository historyRepository) {
        this.mView = view;
        this.mPhotosRepository = photosRepository;
        this.mHistoryRepository = historyRepository;
    }

    @Override
    public void search(String text) {
        mView.displayLoading();

        SearchTerm searchTerm = new SearchTerm();
        searchTerm.setKeyword(text);
        searchTerm.setTimestamp(System.currentTimeMillis());
        mHistoryRepository.addHistoryItem(searchTerm);

        mPhotosRepository.searchPhotos(text, new PhotosRepository.OnPhotosReady() {
            @Override
            public void onPhotosReady(List<PhotoItem> photos) {
                mView.showPhotos(photos);
                mView.hideLoading();
            }

            @Override
            public void onError(Throwable t) {
                mView.hideLoading();
                mView.showErrorMessage(R.string.an_error_happen);
            }
        });
    }
}
