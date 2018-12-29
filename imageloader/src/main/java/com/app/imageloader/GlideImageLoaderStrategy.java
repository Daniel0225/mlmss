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
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.app.imageloader.glide.GlideAppliesOptions;
import com.app.imageloader.glide.GlideJJ;
import com.app.imageloader.glide.GlideRequest;
import com.app.imageloader.glide.GlideRequests;
import com.app.imageloader.utils.PlugImgLoadUtils;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.Target;

/**
 * @Version 2.9.0 ：
 * #1.修复渐变和{@link Target}不能同时使用
 * #2.修复{@link Target}和默认，错误，加载中配置不能同时使用
 * <p>
 * <p>
 * ================================================
 * 此类只是简单的实现了 Glide 加载的策略,方便快速使用,但大部分情况会需要应对复杂的场景
 * 这时可自行实现 {@link BaseImageLoaderStrategy} 和 {@link ImageConfig} 替换现有策略
 * @see ImageLoader#init(BaseImageLoaderStrategy)
 * Created by Vea on 2018/9/14
 * ================================================
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<ImageConfigImpl>, GlideAppliesOptions {
    @Override
    public void loadImage(Context ctx, ImageConfigImpl config) {

        if (ctx == null) {
            throw new NullPointerException("Context is required");
        } else if (config == null) {
            throw new NullPointerException("ImageConfigImpl is required");
        } else if (config.getTarget() == null && config.getImageView() == null) {
            throw new NullPointerException("Imageview is required");
        } else if (!PlugImgLoadUtils.checkedContext(ctx)) {
            return;
        }

        GlideRequests requests;
        requests = GlideJJ.with(ctx);//如果context是activity则自动使用Activity的生命周期

        GlideRequest<Drawable> glideRequest = requests.load(config.getUrl());

        switch (config.getCacheStrategy()) {//缓存策略
            case 0:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case 1:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case 2:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case 3:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case 4:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;
            default:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
        }

        if (config.isCrossFade()) {
            glideRequest.transition(DrawableTransitionOptions.withCrossFade());
        }

        if (config.isCenterCrop()) {
            glideRequest.centerCrop();
        }

        if (config.isCircle()) {
            glideRequest.circleCrop();
        }

        if (config.isImageRadius()) {
            glideRequest.transform(new RoundedCorners(config.getImageRadius()));
        }

        if (config.getTransformation() != null) {//glide用它来改变图形的形状
            glideRequest.transform(config.getTransformation());
        }

        if (config.getPlaceholder() != 0)//设置占位符
            glideRequest.placeholder(config.getPlaceholder());

        if (config.getErrorPic() != 0)//设置错误的图片
            glideRequest.error(config.getErrorPic());

        if (config.getFallback() != 0)//设置请求 url 为空图片
            glideRequest.fallback(config.getFallback());

        if (config.getTarget() != null) {
            glideRequest.into(config.getTarget());
        } else if (config.getImageView() != null) {
            glideRequest
                    .into(config.getImageView());
        }
    }

    @Override
    public void clear(final Context ctx, ImageConfigImpl config) {

        if (ctx == null) {
            throw new NullPointerException("Context is required");
        } else if (config == null) {
            throw new NullPointerException("ImageConfigImpl is required");
        } else if (!PlugImgLoadUtils.checkedContext(ctx)) {
            return;
        }

        if (config.getImageView() != null) {
            GlideJJ.get(ctx).getRequestManagerRetriever().get(ctx).clear(config.getImageView());
        }

        if (config.getImageViews() != null && config.getImageViews().length > 0) {//取消在执行的任务并且释放资源
            for (ImageView imageView : config.getImageViews()) {
                GlideJJ.get(ctx).getRequestManagerRetriever().get(ctx).clear(imageView);
            }
        }
    }

    @Override
    public void applyGlideOptions(Context context, GlideBuilder builder) {
        Log.d("plugin-imageLoader", "applyGlideOptions");
        // TODO 全局配置
//        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(R.mipmap.ic_launcher);
//        builder.setDefaultRequestOptions(requestOptions);

    }
}
