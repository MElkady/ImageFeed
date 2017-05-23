package me.elkady.imagefeed;

import android.support.annotation.StringRes;

/**
 * Created by MAK on 5/22/17.
 */

public interface BaseContract {
    interface BasePresenter {

    }

    interface BaseView<T extends BasePresenter> {
        void showErrorMessage(@StringRes int error);
    }
}