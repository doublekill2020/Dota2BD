package com.badr.infodota.util;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by ABadretdinov
 * 17.04.2015
 * 12:26
 */
public class GrayImageLoadingListener implements ImageLoadingListener {
    @Override
    public void onLoadingStarted(String imageUri, View view) {

    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

    }

    @Override
    @SuppressWarnings("deprecation")
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
        ((ImageView) view).setImageBitmap(TrackUtils.toGrayScale(bitmap));
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {

    }
}
