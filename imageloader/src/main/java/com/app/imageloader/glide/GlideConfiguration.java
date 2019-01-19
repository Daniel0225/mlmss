/*
 * Copyright 2018 闪电降价
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.app.imageloader.glide;

import android.content.Context;

import com.app.imageloader.BaseImageLoaderStrategy;
import com.app.imageloader.ImageLoader;
import com.app.imageloader.integration.OkHttpUrlLoader;
import com.app.imageloader.utils.PlugImgLoadUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.File;
import java.io.InputStream;

/**
 * ================================================
 * {@link AppGlideModule} 的默认实现类
 * 用于配置缓存文件夹,切换图片请求框架等操作
 * <p>
 * Created by Vea on 2018/9/14
 * ================================================
 */
@GlideModule(glideName = "GlideApp")
public class GlideConfiguration extends AppGlideModule {
    public static final int IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024;//图片缓存文件最大值为100Mb


    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {

//        Glide 使用 DiskLruCacheWrapper 作为默认的 磁盘缓存 。
//        DiskLruCacheWrapper 是一个使用 LRU 算法的固定大小的磁盘缓存。
//        默认磁盘大小为 250 MB ，位置是在应用的 缓存文件夹 中的一个 特定目录 。
//        假如应用程序展示的媒体内容是公开的（从无授权机制的网站上加载，或搜索引擎等），
//        那么应用可以将这个缓存位置改到外部存储：
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                File cacheDirectory = PlugImgLoadUtils.makeDirs(PlugImgLoadUtils.getTjjCacheFile(context));
                return DiskLruCacheWrapper.create(cacheDirectory, IMAGE_DISK_CACHE_MAX_SIZE);
            }
        });

        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));


        // TODO 可使用application注入
        BaseImageLoaderStrategy loadImgStrategy = ImageLoader.instance().getLoadImgStrategy();
        if (loadImgStrategy != null && loadImgStrategy instanceof GlideAppliesOptions) {
            ((GlideAppliesOptions) loadImgStrategy).applyGlideOptions(context, builder);
        }
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        //Glide 默认使用 HttpURLConnection 做网络请求,在这切换成 Okhttp 请求
        // TODO  第三个参数可传入Call.Factory
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }


    //为了维持对 Glide v3 的 GlideModules 的向后兼容性，
    // Glide 仍然会解析应用程序和所有被包含的库中的 AndroidManifest.xml 文件，
    // 并包含在这些清单中列出的旧 GlideModules 模块类。
    //如果你已经迁移到 Glide v4 的 AppGlideModule 和 LibraryGlideModule ，
    // 你可以完全禁用清单解析。这样可以改善 Glide 的初始启动时间，并避免尝试解析元数据时的一些潜在问题。
    // 要禁用清单解析，请在你的 AppGlideModule 实现中复写 isManifestParsingEnabled() 方法：
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
