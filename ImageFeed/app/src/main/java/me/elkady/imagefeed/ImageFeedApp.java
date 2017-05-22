package me.elkady.imagefeed;

import android.app.Application;

import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

/**
 * Created by MAK on 5/21/17.
 */

public class ImageFeedApp extends Application {
    public static final String LOG_TAG = "ImageFeed";

    @Override
    public void onCreate() {
        super.onCreate();

        TwitterAuthConfig authConfig =  new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig));
    }
}
