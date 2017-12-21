package com.bwie.zhouxuan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bwie.zhouxuan.adapter.ShowAdapter;
import com.bwie.zhouxuan.bean.ShowBean;
import com.bwie.zhouxuan.presenter.Ipresenter;
import com.bwie.zhouxuan.view.Iview;

public class ShowActivity extends AppCompatActivity implements Iview{
     Ipresenter ipresenter;
     RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ipresenter = new Ipresenter(this);
        ipresenter.getUrl("http://120.27.23.105");
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void getShowData(ShowBean showBean) {
        ShowBean.TuijianBean tuijian = showBean.getTuijian();
        System.out.println("数据："+ tuijian);
        ShowAdapter showAdapter = new ShowAdapter(ShowActivity.this, showBean.getTuijian());
        recyclerView.setAdapter(showAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ipresenter=null;
    }
}
