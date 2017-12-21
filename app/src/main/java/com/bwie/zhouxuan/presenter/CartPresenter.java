package com.bwie.zhouxuan.presenter;

import com.bwie.zhouxuan.bean.SelectBean;
import com.bwie.zhouxuan.model.UserCartModel;
import com.bwie.zhouxuan.view.CartView;

/**
 * Created by dream on 2017/12/21.
 */

public class CartPresenter implements UserCartModel.CartFinish{
    public final CartView cartView;
    public final UserCartModel userCartModel;

    public CartPresenter(CartView cartView) {
        this.cartView = cartView;
        userCartModel = new UserCartModel(this);
    }
   public void getUrl(String url){
        userCartModel.getUrl(url);
   }
    @Override
    public void CartFinished(SelectBean selectBean) {
        cartView.getData(selectBean);
    }
}
