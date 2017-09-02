package me.elkady.imagefeed.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.elkady.imagefeed.data.HistoryRepository;
import me.elkady.imagefeed.data.HistoryRepositoryImpl;
import me.elkady.imagefeed.data.PhotosRepository;
import me.elkady.imagefeed.data.PhotosRepositoryImpl;

/**
 * Created by work on 9/2/17.
 */

@Module
public class ApplicationModule {
    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context providesContext() {
        return context;
    }

    @Provides
    @Singleton
    public PhotosRepository providePhotosRepository(){
        return new PhotosRepositoryImpl();
    }

    @Provides
    @Singleton
    public HistoryRepository provideHistoryRepository(){
        return new HistoryRepositoryImpl();
    }
}
