package me.elkady.imagefeed.search;

import java.util.List;

import me.elkady.imagefeed.BaseContract.BasePresenter;
import me.elkady.imagefeed.BaseContract.BaseView;
import me.elkady.imagefeed.models.PhotoItem;

/**
 * Created by MAK on 5/21/17.
 */

public interface SearchContract {
    interface Presenter extends BasePresenter<View> {
        void search(String text);
    }

    interface View extends BaseView {
        void showPhotos(List<PhotoItem> photoItems);
        void displayLoading();
        void hideLoading();
    }
}
