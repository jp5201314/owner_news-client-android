package cn.cnlinfo.ccf.rx;

import cn.cnlinfo.ccf.net_okhttp.OKHttpManager;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public class NetOperationTool {

    public static  Retrofit getRetrofit(String baseUrl){
        return new Retrofit.Builder().baseUrl(baseUrl).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(OKHttpManager.getInstance()).build();
    }
}
