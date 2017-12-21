package com.bwie.zhouxuan.model;

import com.bwie.zhouxuan.api.ApiService;
import com.bwie.zhouxuan.bean.SelectBean;

import java.util.List;

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

public class UserCartModel implements CartModel {
    CartFinish cartFinish;

    public UserCartModel(CartFinish cartFinish) {
        this.cartFinish = cartFinish;
    }

    public interface CartFinish{
        void CartFinished(SelectBean selectBean);
    }
    @Override
    public void getUrl(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        final Observable<SelectBean> login = apiService.getSelectCart("android","983");
        login.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SelectBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SelectBean selectBean) {
                       cartFinish.CartFinished(selectBean);
                    }
                });
    }
}
