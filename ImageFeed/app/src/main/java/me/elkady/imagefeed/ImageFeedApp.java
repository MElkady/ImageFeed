package me.elkady.imagefeed;

import android.app.Application;
import android.content.Context;

import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;
import me.elkady.imagefeed.di.components.ApplicationComponent;
import me.elkady.imagefeed.di.components.DaggerApplicationComponent;
import me.elkady.imagefeed.di.modules.ApplicationModule;

/**
 * Created by MAK on 5/21/17.
 */

public class ImageFeedApp extends Application {
    public static final String LOG_TAG = "ImageFeed";
    private static Context appContext;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        TwitterAuthConfig authConfig =  new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig));
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static Context getAppContext() {
        return appContext;
    }
}
