package me.elkady.imagefeed.models;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by MAK on 5/24/17.
 */

public class GooglePlusPhotoItem extends PhotoItem {
    private static final String googlePlusFormat="yyyy-MM-dd'T'HH:mm:ss.SSS";

    @Override
    public String getSource() {
        return "G+";
    }

    public static class GooglePlusPhotoItemDeserializer implements JsonDeserializer<GooglePlusPhotoItem> {
        @Override
        public GooglePlusPhotoItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jobject = (JsonObject) json;
            GooglePlusPhotoItem m = new GooglePlusPhotoItem();
            m.setId(jobject.get("id").getAsString());
            m.setCaption(jobject.get("title").getAsString());
            try {
                m.setImageUrl(jobject.get("object").getAsJsonObject().get("attachments").getAsJsonArray().get(0).getAsJsonObject().get("fullImage").getAsJsonObject().get("url").getAsString());
            } catch (Exception e) {
                // Ignore, this activity may not contain an image...
            }

            SimpleDateFormat sf = new SimpleDateFormat(googlePlusFormat, Locale.ENGLISH);
            sf.setLenient(true);
            try {
                Date d = sf.parse(jobject.get("published").getAsString());
                m.setTimestamp(d.getTime());
            } catch (ParseException e) {
                m.setTimestamp(new Date().getTime());
            }

            return m;
        }
    }
}
