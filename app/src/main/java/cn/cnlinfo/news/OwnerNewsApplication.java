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
