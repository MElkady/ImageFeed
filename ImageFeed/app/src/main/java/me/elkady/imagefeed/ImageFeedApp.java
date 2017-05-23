package me.elkady.imagefeed;

import android.app.Application;
import android.content.Context;

import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

/**
 * Created by MAK on 5/21/17.
 */

public class ImageFeedApp extends Application {
    public static final String LOG_TAG = "ImageFeed";
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();

        TwitterAuthConfig authConfig =  new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig));
    }

    public static Context getAppContext() {
        return appContext;
    }
}
