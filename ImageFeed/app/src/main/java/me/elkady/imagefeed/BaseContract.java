package me.elkady.imagefeed;

import android.support.annotation.StringRes;

/**
 * Created by MAK on 5/22/17.
 */

public interface BaseContract {
    interface BasePresenter<T extends BaseView> {
        void attachView(T view);
        void detachView();
    }

    interface BaseView {
        void showErrorMessage(@StringRes int error);
    }
}