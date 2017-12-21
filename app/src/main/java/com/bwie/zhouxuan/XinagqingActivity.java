package com.bwie.zhouxuan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.zhouxuan.api.ApiService;
import com.bwie.zhouxuan.api.MessageEvent;
import com.bwie.zhouxuan.bean.AddBean;
import com.bwie.zhouxuan.bean.XiangqingBean;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class XinagqingActivity extends AppCompatActivity {

    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img)
    SimpleDraweeView img;
    String pid = "";
    @BindView(R.id.add)
    TextView add;
    @BindView(R.id.into)
    TextView into;

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void ononMoonStickyEvent(MessageEvent messageEvent) {
        pid = messageEvent.getMessage();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinagqing);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://120.27.23.105")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        final Observable<XiangqingBean> login = apiService.getXiangqing("android", pid);
        login.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<XiangqingBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(XiangqingBean xiangqingBean) {
                        String[] split = xiangqingBean.getData().getImages().split("\\|");
//                        img.setImageResource(Integer.parseInt(split[0]));
                        img.setImageURI(split[0]);
                        price.setText("￥" + xiangqingBean.getData().getPrice());
                        title.setText(xiangqingBean.getData().getTitle());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.into, R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.into:
                Intent intent = new Intent(XinagqingActivity.this, CartActivity.class);
                startActivity(intent);
                break;
            case R.id.add:
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://120.27.23.105")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
                ApiService apiService = retrofit.create(ApiService.class);
                final Observable<AddBean> login = apiService.getAdd("android", "983", pid);
                login.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<AddBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(AddBean addBean) {
                                String code = addBean.getCode();
                                if (code.equals("0")) {
                                    Toast.makeText(XinagqingActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(XinagqingActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }
}
