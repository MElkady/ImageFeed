package me.elkady.imagefeed.di.components;

import javax.inject.Singleton;

import dagger.Component;
import me.elkady.imagefeed.di.modules.ApplicationModule;
import me.elkady.imagefeed.di.modules.PresentationModule;
import me.elkady.imagefeed.history.HistoryFragment;
import me.elkady.imagefeed.search.SearchFragment;

/**
 * Created by work on 9/2/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class, PresentationModule.class})
public interface ApplicationComponent {
    void inject(SearchFragment searchFragment);
    void inject(HistoryFragment historyFragment);
}
