package cn.cnlinfo.news;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.Logger;

import cn.bmob.v3.Bmob;
import cn.cnlinfo.news.manager.ACache;
import cn.cnlinfo.news.ui.login.LoginRegisterActivity;


/**
 * Created by JP on 2017/10/11 0011.
 */

public class OwnerNewsApplication extends Application {

    private static Context mContext;
    private static OwnerNewsApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        mContext = getApplicationContext();
        Logger.init("owner_news");
        //初始化Bmob
        /**
         * //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
         //BmobConfig config =new BmobConfig.Builder(this)
         ////设置appkey
         //.setApplicationId("Your Application ID")
         ////请求超时时间（单位为秒）：默认15s
         //.setConnectTimeout(30)
         ////文件分片上传时每片的大小（单位字节），默认512*1024
         //.setUploadBlockSize(1024*1024)
         ////文件的过期时间(单位为秒)：默认1800s
         //.setFileExpiration(2500)
         //.build();
         //Bmob.initialize(config);
         */
        Bmob.initialize(this,"223ee327a16960d28bd66cf3207f38e2");
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    protected boolean allowLogin(){
        return null==ACache.get(mContext).getAsString("isLogged");
    }

    protected void jumpToLogin(){
        if (!allowLogin()) return;
        ACache.get(mContext).put("isLogged",true,5);
        Intent intent = new Intent(mContext, LoginRegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public static synchronized OwnerNewsApplication getInstance() {
        return INSTANCE;
    }

    public static synchronized Context getContext(){return mContext;}


}
