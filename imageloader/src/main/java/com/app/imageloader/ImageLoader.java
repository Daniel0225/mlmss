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
package com.app.imageloader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.imageloader.utils.Preconditions;

/**
 * ================================================
 * {@link ImageLoader} 使用策略模式和建造者模式,可以动态切换图片请求框架(比如说切换成 Picasso )
 * 当需要切换图片请求框架或图片请求框架升级后变更了 Api 时
 * 这里可以将影响范围降到最低,所以封装 {@link ImageLoader}
 * <p>
 * Created by Vea on 8/5/16 15:57
 * ================================================
 */
public final class ImageLoader {
    private static ImageLoader ourInstance = null;
    private static boolean hasInit = false;
    private BaseImageLoaderStrategy mStrategy;

    private ImageLoader() {
    }

    public static ImageLoader instance() {

        if (ourInstance == null) {
            synchronized (ImageLoader.class) {
                if (ourInstance == null) {
                    ourInstance = new ImageLoader();
                }
            }
        }
        return ourInstance;
    }

    public void init(@NonNull BaseImageLoaderStrategy strategy) {
        mStrategy = strategy;
        hasInit = true;
    }

    public boolean isInit() {
        return hasInit;
    }

    /**
     * 加载图片
     *
     * @param context
     * @param config
     * @param <T>
     */
    public <T extends ImageConfig> void loadImage(Context context, T config) {
        if (mStrategy == null) {
            mStrategy = new GlideImageLoaderStrategy();
        }
        this.mStrategy.loadImage(context, config);
    }

    /**
     * 停止加载释放资源
     *
     * @param context
     * @param config
     * @param <T>
     */
    public <T extends ImageConfig> void clear(Context context, T config) {
        if (mStrategy == null) {
            mStrategy = new GlideImageLoaderStrategy();
        }
        this.mStrategy.clear(context, config);
    }

    @Nullable
    public BaseImageLoaderStrategy getLoadImgStrategy() {
        return mStrategy;
    }

    /**
     * 可在运行时随意切换 {@link BaseImageLoaderStrategy}
     *
     * @param strategy
     */
    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        Preconditions.checkNotNull(strategy, "strategy == null");
        this.mStrategy = strategy;
    }
}
