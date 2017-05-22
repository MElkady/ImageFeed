package me.elkady.imagefeed.models;

/**
 * Created by MAK on 5/22/17.
 */

public class TwitterPhotoItem extends PhotoItem {
    @Override
    public String getSource() {
        return "Twitter";
    }
}
