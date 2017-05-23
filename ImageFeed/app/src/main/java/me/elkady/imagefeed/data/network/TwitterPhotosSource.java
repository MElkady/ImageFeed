package me.elkady.imagefeed.data.network;

import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.elkady.imagefeed.ImageFeedApp;
import me.elkady.imagefeed.data.PhotosRepository;
import me.elkady.imagefeed.models.PhotoItem;
import me.elkady.imagefeed.models.TwitterPhotoItem;
import retrofit2.Call;

/**
 * Created by MAK on 5/21/17.
 */

public class TwitterPhotosSource implements PhotosRepository.PhotoSource {
    private static final String twitterFormat="EEE MMM dd HH:mm:ss ZZZZZ yyyy";

    @Override
    public void searchPhotos(String text, final PhotosRepository.OnPhotosReady onPhotosReady) {
        TwitterApiClient apiClient = TwitterCore.getInstance().getGuestApiClient();
        String query = text + " AND filter:images";
        Call<Search> result = apiClient.getSearchService().tweets(query, null, null, null, "mixed", 20, null, null, null, true);
        result.enqueue(new Callback<Search>() {
            @Override
            public void success(Result<Search> result) {
                List<Tweet> ts = result.data.tweets;

                List<PhotoItem> photos = new ArrayList<>();
                for(Tweet t : ts) {
                    if (t.entities.media != null && t.entities.media.size() > 0){
                        PhotoItem pi = new TwitterPhotoItem();
                        pi.setId(t.idStr);
                        pi.setCaption(t.text);
                        pi.setImageUrl(t.entities.media.get(0).mediaUrlHttps);
                        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
                        sf.setLenient(true);
                        try {
                            Date d = sf.parse(t.createdAt);
                            pi.setTimestamp(d.getTime());
                        } catch (ParseException pe) {
                            Log.e(ImageFeedApp.LOG_TAG, "Unable to parse date: " + t.createdAt);
                            pi.setTimestamp(new Date().getTime());
                        }
                        photos.add(pi);
                    }
                }

                onPhotosReady.onPhotosReady(photos);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e(ImageFeedApp.LOG_TAG, "Error getting tweets: " + exception.getMessage(), exception);
                onPhotosReady.onError(exception);
            }
        });
    }
}
