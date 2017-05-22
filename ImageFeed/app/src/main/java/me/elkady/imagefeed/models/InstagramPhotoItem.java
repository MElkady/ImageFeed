package me.elkady.imagefeed.models;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by MAK on 5/22/17.
 */

public class InstagramPhotoItem extends PhotoItem {

    @Override
    public String getSource() {
        return "Instagram";
    }

    public static class InstagramPhotoItemDeserializer implements JsonDeserializer<InstagramPhotoItem> {
        @Override
        public InstagramPhotoItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Gson gson = new Gson();

            JsonObject jobject = (JsonObject) json;
            InstagramPhotoItem m = new InstagramPhotoItem();
            m.setId(jobject.get("id").getAsString());
            m.setCaption(jobject.get("caption").getAsJsonObject().get("text").getAsString());
            m.setImageUrl(jobject.get("images").getAsJsonObject().get("standard_resolution").getAsJsonObject().get("url").getAsString());
            m.setTimestamp(jobject.get("created_time").getAsLong() * 1000);
            return m;
        }
    }
}
