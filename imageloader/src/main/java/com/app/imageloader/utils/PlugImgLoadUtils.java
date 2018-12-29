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
package com.app.imageloader.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;

import java.io.File;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;

public class PlugImgLoadUtils {
    /**
     * 检查上下文
     *
     * @param context
     * @return
     */
    public static boolean checkedContext(Context context) {
        if (context instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) context;
            if (Build.VERSION.SDK_INT >= JELLY_BEAN_MR1) {
                return !activity.isDestroyed() && !activity.isFinishing();
            } else {
                boolean destroyed = activity.getSupportFragmentManager().isDestroyed();
                return !activity.isFinishing() && !destroyed;
            }
        } else if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (Build.VERSION.SDK_INT >= JELLY_BEAN_MR1) {
                return !activity.isDestroyed() && !activity.isFinishing();
            } else {
                return !activity.isFinishing();
            }
        } else {
            return context instanceof Application;
        }
    }

    /**
     * 返回缓存文件夹
     */
    public static File getCacheFile(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = null;
            file = context.getExternalCacheDir();//获取系统管理的sd卡缓存文件
            if (file == null) {//如果获取的文件为空,就使用自己定义的缓存文件夹做缓存路径
                file = new File(getCacheFilePath(context));
                makeDirs(file);
            }
            return file;
        } else {
            return context.getCacheDir();
        }
    }

    /**
     * 返回淘集集Glide缓存文件夹
     */
    public static File getTjjCacheFile(Context context) {
        return new File(PlugImgLoadUtils.getCacheFile(context), "Glide");
    }

    /**
     * 获取自定义缓存文件地址
     *
     * @param context
     * @return
     */
    public static String getCacheFilePath(Context context) {
        String packageName = context.getPackageName();
        return "/mnt/sdcard/" + packageName;
    }

    /**
     * 创建未存在的文件夹
     *
     * @param file
     * @return
     */
    public static File makeDirs(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
