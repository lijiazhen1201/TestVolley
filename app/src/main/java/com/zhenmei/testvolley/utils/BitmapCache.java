package com.zhenmei.testvolley.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * 自定义图片缓存类
 */
public class BitmapCache implements ImageLoader.ImageCache {

    /**
     * 图片缓存集合
     */
    private LruCache<String, Bitmap> mCache;

    /**
     * 缓存图片的大小设置为10M
     */
    private int maxSize = 10 * 1024 * 1024;

    public BitmapCache() {
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }

}
