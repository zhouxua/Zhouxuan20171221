package com.bwie.zhouxuan.presenter;

import android.webkit.WebView;

import com.bwie.zhouxuan.bean.ShowBean;
import com.bwie.zhouxuan.model.UserModel;
import com.bwie.zhouxuan.view.Iview;

/**
 * Created by dream on 2017/12/21.
 */

public class Ipresenter implements UserModel.OnFinish{
    public final Iview iview;
    public final UserModel userModel;

    public Ipresenter(Iview iview) {
        this.iview = iview;
        userModel = new UserModel(this);
    }

    public void getUrl(String url){
        userModel.getUrl(url);
    }

    @Override
    public void OnFinished(ShowBean showBean) {
        iview.getShowData(showBean);
    }
}
