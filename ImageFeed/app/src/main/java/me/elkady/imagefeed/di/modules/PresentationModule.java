package me.elkady.imagefeed.di.modules;

import dagger.Module;
import dagger.Provides;
import me.elkady.imagefeed.data.HistoryRepository;
import me.elkady.imagefeed.data.PhotosRepository;
import me.elkady.imagefeed.history.HistoryContract;
import me.elkady.imagefeed.history.HistoryPresenter;
import me.elkady.imagefeed.search.SearchContract;
import me.elkady.imagefeed.search.SearchPresenter;

/**
 * Created by work on 9/2/17.
 */

@Module
public class PresentationModule  {
    @Provides
    SearchContract.Presenter provideSearchPresenter(PhotosRepository photosRepository, HistoryRepository historyRepository){
        return new SearchPresenter(photosRepository, historyRepository);
    }

    @Provides
    HistoryContract.Presenter provideHistoryRepository(HistoryRepository historyRepository){
        return new HistoryPresenter(historyRepository);
    }
}
