package me.elkady.imagefeed.data.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import me.elkady.imagefeed.BuildConfig;
import me.elkady.imagefeed.data.PhotosRepository;
import me.elkady.imagefeed.models.GooglePlusPhotoItem;
import me.elkady.imagefeed.models.GooglePlusPotoList;
import me.elkady.imagefeed.models.InstagramPhotoItem;
import me.elkady.imagefeed.models.InstagramPhotoList;
import me.elkady.imagefeed.models.PhotoItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by MAK on 5/24/17.
 */

public class GooglePlusPhotoSource implements PhotosRepository.PhotoSource {
    private static GooglePlusService  sGooglePlusService;

    interface GooglePlusService {
        @GET("activities")
        Call<GooglePlusPotoList> search(@Query("query") String query, @Query("key") String key);
    }

    @Override
    public void searchPhotos(String text, final PhotosRepository.OnPhotosReady onPhotosReady) {
        if(sGooglePlusService == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(GooglePlusPhotoItem.class, new GooglePlusPhotoItem.GooglePlusPhotoItemDeserializer());
            Gson myGson = gsonBuilder.create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.GOOGLE_PLUS_SERVICE_URL)
                    .addConverterFactory(GsonConverterFactory.create(myGson))
                    .build();

            sGooglePlusService = retrofit.create(GooglePlusService.class);
        }

        sGooglePlusService.search(text, BuildConfig.GOOGLE_PLUS_KEY).enqueue(new Callback<GooglePlusPotoList>() {
            @Override
            public void onResponse(Call<GooglePlusPotoList> call, Response<GooglePlusPotoList> response) {
                List<PhotoItem> photoItems = new ArrayList<>();
                if(response.body() != null && response.body().getMedia().size() > 0) {
                    for(GooglePlusPhotoItem item : response.body().getMedia()) {
                        if(item.getImageUrl() != null) {
                            photoItems.add(item);
                        }
                    }
                }

                onPhotosReady.onPhotosReady(photoItems);
            }

            @Override
            public void onFailure(Call<GooglePlusPotoList> call, Throwable t) {
                onPhotosReady.onError(t);
            }
        });
    }
}
