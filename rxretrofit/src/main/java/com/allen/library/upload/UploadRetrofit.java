package com.allen.library.upload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.allen.library.RxHttpUtils;
import com.allen.library.http.RetrofitClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * <pre>
 *      @author : Allen
 *      date    : 2018/06/14
 *      desc    : 为上传单独建一个retrofit
 *      version : 1.0
 * </pre>
 */
public class UploadRetrofit {
    private static UploadRetrofit instance;
    private static String baseUrl = "";
    private Retrofit mRetrofit;

    public UploadRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    public static UploadRetrofit getInstance() {

        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new UploadRetrofit();
                }
            }
        }
        return instance;
    }

    /**
     * 上传一张图片
     *
     * @param uploadUrl 上传图片的服务器url
     * @param filePath  图片路径
     * @return Observable
     */
    public static Observable<ResponseBody> uploadImage(String uploadUrl, String filePath) {
        List<String> filePaths = new ArrayList<>();
        filePaths.add(filePath);
        return uploadFilesWithParams(uploadUrl, "uploaded_file", null, filePaths);
    }

    /**
     * 图片和参数同时上传的请求
     *
     * @param uploadUrl 上传图片的服务器url
     * @param fileName  后台协定的接受图片的name（没特殊要求就可以随便写）
     * @param paramsMap 普通参数
     * @param filePaths 图片路径
     * @return Observable
     */
    public static Observable<ResponseBody> uploadFilesWithParams(String uploadUrl, String fileName, Map<String, String> paramsMap, List<String> filePaths) {

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        if (null != paramsMap) {
            for (String key : paramsMap.keySet()) {
                builder.addFormDataPart(key, paramsMap.get(key));
            }
        }

        for (int i = 0; i < filePaths.size(); i++) {
            File file = new File(filePaths.get(i));
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            //"fileName"+i 后台接收图片流的参数名
            builder.addFormDataPart(fileName, file.getName(), imageBody);
        }

        List<MultipartBody.Part> parts = builder.build().parts();

        return RxHttpUtils
                .createApi(UploadFileApi.class)
                .uploadFiles(uploadUrl, parts);
    }

    /**
     * 只上传图片
     *
     * @param uploadUrl 上传图片的服务器url
     * @param filePaths 图片路径
     * @return Observable
     */
    public static Observable<ResponseBody> uploadImages(String uploadUrl, List<String> filePaths) {
        return uploadFilesWithParams(uploadUrl, "uploaded_file", null, filePaths);
    }

    /**
     * 图片路径转成上传所需格式
     *
     * @param pathList 图片路径
     * @return 返回上传所需格式
     */
    public static List<MultipartBody.Part> pathsToMultipartBodyParts(List<String> pathList) {
        List<MultipartBody.Part> parts = new ArrayList<>(pathList.size());
        for (int i = 0; i < pathList.size(); i++) {

            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), new File(pathList.get(i)));
            MultipartBody.Part part = MultipartBody.Part.createFormData("file" + i, "uploadFile" + i + ".png", requestBody);
            parts.add(part);
        }
        return parts;
    }

    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> pathList) {
        List<MultipartBody.Part> parts = new ArrayList<>(pathList.size());
        for (int i = 0; i < pathList.size(); i++) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), pathList.get(i));
            MultipartBody.Part part = MultipartBody.Part.createFormData("file" + i, "uploadFile" + i + ".png", requestBody);
            parts.add(part);
        }
        return parts;
    }

    /**
     * 图片路径转成上传所需格式
     *
     * @param path 图片路径
     * @return 返回上传所需格式
     */
    public static MultipartBody.Part fileToMultipartBodyParts(String path) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), new File(path));
        return MultipartBody.Part.createFormData("file", "uploadFile" + ".png", requestBody);
    }

    /**
     * bitmap和参数同时上传的请求
     *
     * @param bitmapList bitmapList集合
     * @param uploadUrl  上传图片的服务器url
     * @param fileName   后台协定的接受图片的name（没特殊要求就可以随便写）
     * @param paramsMap  普通参数
     * @param filePath
     * @return Observable
     */
    public static Observable<ResponseBody> uploadBitmapsWithParams(String uploadUrl, String fileName, Map<String, String> paramsMap, List<File> files) {

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        if (null != paramsMap) {
            for (String key : paramsMap.keySet()) {
                builder.addFormDataPart(key, paramsMap.get(key));
            }
        }

        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                Bitmap bitmapOrg = BitmapFactory.decodeFile(files.get(i).getPath());
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                byte[] imagearray = bao.toByteArray();
                String ba = Base64.encodeToString(imagearray, Base64.DEFAULT);
                //"fileName"+i 后台接收图片流的参数名
                builder.addFormDataPart(fileName + (i + 1), ba);
            }
        }
        List<MultipartBody.Part> parts = builder.build().parts();


        return RxHttpUtils.createApi(UploadFileApi.class)
                .uploadFiles(uploadUrl, parts);
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
