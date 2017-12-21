package com.bwie.zhouxuan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bwie.zhouxuan.adapter.RecyAdapter;
import com.bwie.zhouxuan.api.ApiService;
import com.bwie.zhouxuan.bean.SelectBean;
import com.bwie.zhouxuan.bean.ShowBean;
import com.bwie.zhouxuan.presenter.CartPresenter;
import com.bwie.zhouxuan.view.CartView;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CartActivity extends AppCompatActivity implements CartView{
    private RecyclerView recyclerView;
    private TextView total_price;
    private TextView total_num;
    private CheckBox quanxuan;
    private RecyAdapter recyAdapter;
    CartPresenter cartPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_View);
        total_price = (TextView) findViewById(R.id.total_price);
        total_num = (TextView) findViewById(R.id.total_num);
        quanxuan = (CheckBox) findViewById(R.id.quanxuan);
        cartPresenter = new CartPresenter(this);
        cartPresenter.getUrl("http://120.27.23.105");
        quanxuan.setTag(1);//1为不选中
        LinearLayoutManager manager = new LinearLayoutManager(CartActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);


        //new出适配器
        recyAdapter = new RecyAdapter(this);
        recyclerView.setAdapter(recyAdapter);

        //调用recyAdapter里面的接口,设置 全选按钮 总价 总数量
        recyAdapter.setUpdateListener(new RecyAdapter.UpdateListener() {
            @Override
            public void setTotal(String total, String num, boolean allCheck) {
                //设置ui的改变
                total_num.setText("共"+num+"件商品");//总数量
                total_price.setText("总价 :¥"+total+"元");//总价
                if(allCheck){
                    quanxuan.setTag(2);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_selected);
                }else{
                    quanxuan.setTag(1);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_unselected);
                }
                quanxuan.setChecked(allCheck);
            }
        });

        //这里只做ui更改, 点击全选按钮,,调到adapter里面操作
        quanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用adapter里面的方法 ,,把当前quanxuan状态传递过去

                int tag = (int) quanxuan.getTag();
                if(tag==1){
                    quanxuan.setTag(2);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_selected);
                }else{
                    quanxuan.setTag(1);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_unselected);
                }

                recyAdapter.quanXuan(quanxuan.isChecked());
            }
        });
    }

    @Override
    public void getData(SelectBean selectBean) {
        recyAdapter.add(selectBean);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cartPresenter=null;
    }
}
