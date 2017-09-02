package me.elkady.imagefeed.history;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import me.elkady.imagefeed.data.HistoryRepository;
import me.elkady.imagefeed.history.HistoryContract;
import me.elkady.imagefeed.history.HistoryPresenter;
import me.elkady.imagefeed.models.SearchTerm;

import static org.mockito.Mockito.verify;

/**
 * Created by MAK on 5/23/17.
 */

public class HistoryPresenterTest {
    @Mock
    private HistoryRepository mHistoryRepository;

    @Mock
    private HistoryContract.View mView;

    @Captor
    private ArgumentCaptor<HistoryRepository.OnHistoryReady> mHistoryRepositoryOnHistoryReady;

    private HistoryContract.Presenter mPresenter;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        mPresenter = new HistoryPresenter(mHistoryRepository);
        mPresenter.attachView(mView);
    }

    @Test
    public void testLoadHistory() {
        List<SearchTerm> searchTermList = new ArrayList<>();

        mPresenter.loadHistory();
        verify(mHistoryRepository).loadHistory(mHistoryRepositoryOnHistoryReady.capture());
        mHistoryRepositoryOnHistoryReady.getValue().onHistoryReady(searchTermList);
        verify(mView).displayHistoryItems(searchTermList);
    }
}
