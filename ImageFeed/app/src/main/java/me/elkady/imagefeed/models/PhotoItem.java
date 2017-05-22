package me.elkady.imagefeed.models;

/**
 * Created by MAK on 5/21/17.
 */

public abstract class PhotoItem {
    private String imageUrl;
    private String caption;
    private String id;
    private long timestamp;

    public PhotoItem(){

    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public String toString() {
        return "PhotoItem{" +
                "imageUrl='" + imageUrl + '\'' +
                ", caption='" + caption + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public abstract String getSource();
}
