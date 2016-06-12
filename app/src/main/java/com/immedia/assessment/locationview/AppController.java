package com.immedia.assessment.locationview;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Rudolph on 2016/06/08.
 */
public class AppController extends Application {
    private static final String TAG="AppController";
    private static AppController mInstance;
    private ImageLoader mImageLoader;
    private static ImageLoader.ImageCache mCache;

    public static synchronized AppController getInstance(){
        return mInstance;
    }

    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        getRequestQueue();
        mCache = new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap>
                    cache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                    cache.put(url, bitmap);
            }
        };
        mImageLoader = new ImageLoader(mRequestQueue,mCache);

    }

    public RequestQueue getRequestQueue(){
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag){
        if (mRequestQueue != null){
            mRequestQueue.cancelAll(tag);
        }
    }
    public static  ImageLoader.ImageCache getCache(){
        return mCache;
    }
    public ImageLoader getImageLoader(Context context) {
        return mImageLoader;
    }
}
