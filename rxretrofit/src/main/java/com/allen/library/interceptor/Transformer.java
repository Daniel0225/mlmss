package com.allen.library.interceptor;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Allen on 2016/12/20.
 * <p>
 *
 * @author Allen
 *         控制操作线程的辅助类
 */

public class Transformer {

    /**
     * 无参数
     *
     * @param <T> 泛型
     * @return 返回Observable
     */
    public static <T> ObservableTransformer<T, T> switchSchedulers() {
        return switchSchedulers(new Consumer() {
            @Override
            public void accept(Object o) {

            }
        });
    }

    /**
     * 带参数  显示loading对话框
     *
     * @param <T>      泛型
     * @param consumer consumer
     * @return 返回Observable
     */
    public static <T> ObservableTransformer<T, T> switchSchedulers(final Consumer consumer) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(consumer)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 带参数  显示loading对话框
     *
     * @param loadDialog loadDialog
     * @param <T>        泛型
     * @param consumer   consumer
     * @param action     action
     * @return 返回Observable
     */
 /*   public static <T> ObservableTransformer<T, T> switchSchedulers(final Dialog loadDialog, final Consumer consumer, final Action action) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(consumer)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(action);
            }
        };
    }*/

}
