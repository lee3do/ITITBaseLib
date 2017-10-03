package io.itit.androidlibrary.network.http;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hwangjr.rxbus.RxBus;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import io.itit.androidlibrary.Consts;
import io.itit.androidlibrary.ITITApplication;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.Subject;
import retrofit2.HttpException;

public class BaseSubscriber<T> extends Subject<T> {

    public Context mContext;
    MaterialDialog dialog;

    public BaseSubscriber() {
//        if (!NetworkUtil.isNetworkAvailable(context)) {
//
//            Toast.makeText(context, "当前网络不可用，请检查网络情况", Toast.LENGTH_SHORT).show();
//            // **一定要主动调用下面这一句**
//            onCompleted();
//
//            return;
//        }
//        // 显示进度条

        mContext = ITITApplication.appContext;

    }

    public BaseSubscriber(boolean showLoading){
        mContext = ITITApplication.appContext;
        RxBus.get().post(Consts.BusAction.SHOW_LOADING,true);

    }

    @Override
    public void onError(final Throwable e) {
        Logger.e(e,"onError");
        RxBus.get().post(Consts.BusAction.SHOW_LOADING,false);
        if (e instanceof HttpException) {
            RxBus.get().post(Consts.BusAction.TOAST,"网络异常,请检查网络");
        } else if (e instanceof IOException) {
            RxBus.get().post(Consts.BusAction.TOAST,"网络异常,请检查网络");
        } else if (e instanceof NeedLoginException) {
            RxBus.get().post(Consts.BusAction.TOAST, e.getMessage());
            RxBus.get().post(Consts.BusAction.LOG_OUT,true);
        }else {
            RxBus.get().post(Consts.BusAction.TOAST, e.getMessage());
        }
    }

    @Override
    public void onComplete() {
        //关闭等待进度条
        RxBus.get().post(Consts.BusAction.SHOW_LOADING,false);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(T t) {

    }


    @Override
    public boolean hasObservers() {
        return false;
    }

    @Override
    public boolean hasThrowable() {
        return false;
    }

    @Override
    public boolean hasComplete() {
        return false;
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {

    }
}