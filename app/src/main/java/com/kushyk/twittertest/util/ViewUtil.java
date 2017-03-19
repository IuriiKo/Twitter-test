package com.kushyk.twittertest.util;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * Created by xaocu on 19.03.2017.
 */

public class ViewUtil {
    public static void loadCircleUserImage(final ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(imageView){
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getView().getContext().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        getView().setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

}
