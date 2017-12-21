package com.bwie.zhouxuan.api;

import com.bwie.zhouxuan.bean.AddBean;
import com.bwie.zhouxuan.bean.DeleteBean;
import com.bwie.zhouxuan.bean.LoginBean;
import com.bwie.zhouxuan.bean.RegistBean;
import com.bwie.zhouxuan.bean.SelectBean;
import com.bwie.zhouxuan.bean.ShowBean;
import com.bwie.zhouxuan.bean.XiangqingBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by dream on 2017/12/21.
 */

public interface ApiService {
    //http://120.27.23.105/user/login
    @GET("user/login")
    Observable<LoginBean> getLogin(@Query("mobile") String mobile,@Query("password") String password);
   //http://120.27.23.105/user/reg
    @GET("user/reg")
    Observable<RegistBean> getRegist(@Query("mobile") String mobile, @Query("password") String password);
    //http://120.27.23.105/ad/getAd
    @GET("ad/getAd")
    Observable<ShowBean> getShow();
    //http://120.27.23.105/product/getProductDetail?source=android&pid=55
    @GET("product/getProductDetail")
    Observable<XiangqingBean> getXiangqing(@Query("source") String source,@Query("pid") String pid);
    //http://120.27.23.105/product/addCart
    //http://120.27.23.105/product/addCart?source=android&uid=983&pid=58
    @GET("product/addCart")
    Observable<AddBean> getAdd(@Query("source") String source, @Query("uid") String uid, @Query("pid") String pid);
   //http://120.27.23.105/product/getCarts?source=android&uid=983

   @GET("product/getCarts")
   Observable<SelectBean> getSelectCart(@Query("source") String source, @Query("uid") String uid);

   //http://120.27.23.105/product/deleteCart?uid=983&pid=56
   @GET("product/deleteCart")
   Observable<DeleteBean> getDelete(@Query("uid") String uid, @Query("pid") String pid);

}

