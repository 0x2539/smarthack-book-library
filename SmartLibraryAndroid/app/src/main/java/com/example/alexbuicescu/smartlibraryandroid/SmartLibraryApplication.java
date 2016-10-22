package com.example.alexbuicescu.smartlibraryandroid;

import android.app.Application;

import com.example.alexbuicescu.smartlibraryandroid.rest.RestClient;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class SmartLibraryApplication extends Application {

    private static DisplayImageOptions displayImageOptions;

    @Override
    public void onCreate() {
        super.onCreate();

        RestClient.getInstance(getApplicationContext());
        initUniversalImageLoader();
    }


    private void initUniversalImageLoader() {

        displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.white_bg)//display stub image until image is loaded
                .showImageOnFail(R.drawable.book_placeholder)
                .showImageForEmptyUri(R.drawable.book_placeholder)
//                .displayer(new RoundedBitmapDisplayer(20))
                .build();

        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(20 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCacheSize(100 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .writeDebugLogs()
                .threadPoolSize(10)
                .defaultDisplayImageOptions(displayImageOptions)
                .build();

        ImageLoader.getInstance().init(config);

    }
}
