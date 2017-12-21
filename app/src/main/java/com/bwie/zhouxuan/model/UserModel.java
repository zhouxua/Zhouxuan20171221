package com.bwie.zhouxuan.model;

import android.content.Intent;
import android.widget.Toast;

import com.bwie.zhouxuan.MainActivity;
import com.bwie.zhouxuan.ShowActivity;
import com.bwie.zhouxuan.api.ApiService;
import com.bwie.zhouxuan.bean.LoginBean;
import com.bwie.zhouxuan.bean.ShowBean;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dream on 2017/12/21.
 */

public class UserModel implements Imodel {
    OnFinish onFinish;

    public UserModel(OnFinish onFinish) {
        this.onFinish = onFinish;
    }

    public interface OnFinish{
        void OnFinished(ShowBean showBean);
    }

    @Override
    public void getUrl(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        final Observable<ShowBean> login = apiService.getShow();
        login.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShowBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ShowBean showBean) {
                      onFinish.OnFinished(showBean);
                    }
                });
    }
}
