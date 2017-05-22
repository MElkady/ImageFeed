package me.elkady.imagefeed.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import me.elkady.imagefeed.BuildConfig;
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
 * Created by MAK on 5/22/17.
 */

public class InstagramPhotosSource implements PhotoSource {
    private static InstagramService sInstagramService;

    interface InstagramService {
        @GET("search")
        Call<InstagramPhotoList> search(@Query("q") String q);
    }

    @Override
    public void searchPhotos(String text, final OnPhotosReady onPhotosReady) {
        if(sInstagramService == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(InstagramPhotoItem.class, new InstagramPhotoItem.InstagramPhotoItemDeserializer());
            Gson myGson = gsonBuilder.create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.INSTAGRAM_SERVICE_URL)
                    .addConverterFactory(GsonConverterFactory.create(myGson))
                    .build();

            sInstagramService = retrofit.create(InstagramService.class);
        }

        sInstagramService.search(text).enqueue(new Callback<InstagramPhotoList>() {
            @Override
            public void onResponse(Call<InstagramPhotoList> call, Response<InstagramPhotoList> response) {
                List<PhotoItem> photoItems = new ArrayList<>();
                if(response.body() != null && response.body().getMedia().size() > 0) {
                    for(InstagramPhotoItem item : response.body().getMedia()) {
                        photoItems.add(item);
                    }
                }

                onPhotosReady.onPhotosReady(photoItems);
            }

            @Override
            public void onFailure(Call<InstagramPhotoList> call, Throwable t) {
                onPhotosReady.onError(t);
            }
        });
    }
}
