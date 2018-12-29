package com.allen.library.base;

import android.content.Context;

import com.allen.library.exception.ApiException;
import com.allen.library.interfaces.ISubscriber;
import com.allen.library.manage.RxHttpManager;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @param <T>
 * @author ym.li
 * @version 2.11.0
 * @since 2018年12月24日11:29:49
 */
public abstract class BaseObserver<T> implements Observer<T>, ISubscriber<T> {
    /**
     * 网络请求承载环境,网络请求回调发生在该载体上(fragment或Activity),当前载体在销毁时应注意网络请求回调是否已取消
     */
    private WeakReference<Context> mContext;

    /**
     * constructor
     *
     * @param context 上下文载体
     */
    public BaseObserver(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    /**
     * 获取上下文对象
     *
     * @return Context
     */
    protected Context getContext() {
        return mContext.get();
    }

    /**
     * 是否隐藏toast
     *
     * @return true or false
     */
    protected boolean isHideToast() {
        return false;
    }

    /**
     * 标记网络请求的tag
     * tag下的一组或一个请求，用来处理一个页面的所以请求或者某个请求
     * 设置一个tag就行就可以取消当前页面所有请求或者某个请求了
     *
     * @return string
     */
    protected String setTag() {
        return mContext.getClass().getSimpleName();
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        RxHttpManager.get().add(setTag(), d);
        doOnSubscribe(d);
    }

    @Override
    public void onNext(@NonNull T t) {
        doOnNext(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        setError(error);
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }

    private void setError(String errorMsg) {
        doOnError(errorMsg);
    }
}
