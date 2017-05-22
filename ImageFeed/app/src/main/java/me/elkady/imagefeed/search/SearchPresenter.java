package me.elkady.imagefeed.search;

import android.content.Context;

import java.util.List;

import me.elkady.imagefeed.R;
import me.elkady.imagefeed.data.PhotoSource;
import me.elkady.imagefeed.data.PhotosRepository;
import me.elkady.imagefeed.models.PhotoItem;

/**
 * Created by MAK on 5/21/17.
 */

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View mView;
    private Context mContext;
    private PhotosRepository mPhotosRepository;


    public SearchPresenter(Context context, SearchContract.View view) {
        this.mView = view;
        this.mContext = context;
        this.mPhotosRepository = new PhotosRepository();
    }

    @Override
    public void search(String text) {
        mView.showLoadingMessage();
        mPhotosRepository.searchPhotos(text, new PhotoSource.OnPhotosReady() {
            @Override
            public void onPhotosReady(List<PhotoItem> photos) {
                mView.showPhotos(photos);
                mView.hideLoadingMessage();
            }

            @Override
            public void onError(Throwable t) {
                mView.hideLoadingMessage();
                mView.showErrorMessage(mContext.getString(R.string.an_error_happen));
            }
        });
    }
}
