package cn.cnlinfo.news;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.query.QueryBuilder;

import cn.bmob.v3.Bmob;
import cn.cnlinfo.news.bean.DaoMaster;
import cn.cnlinfo.news.bean.DaoSession;
import cn.cnlinfo.news.event.ErrorMessageEvent;
import cn.cnlinfo.news.manager.ACache;
import cn.cnlinfo.news.rx.rxbus.RxBus;
import cn.cnlinfo.news.ui.activity.login.LoginRegisterActivity;
import rx.Observable;
import rx.functions.Action1;


/**
 * Created by JP on 2017/10/11 0011.
 */

public class OwnerNewsApplication extends Application {

    private static Context mContext;
    private static OwnerNewsApplication INSTANCE;
    private static DaoSession daoSession;
    private static Observable<ErrorMessageEvent> messageEventObservable;
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
        //配置数据库
        setupDatabase();
        initRxBusEvent();
    }

    /**
     * 初始化错误信息显示
     */
    private void initRxBusEvent() {
        messageEventObservable = RxBus.get().register(Constant.ERRORMESSAGE,ErrorMessageEvent.class);
        messageEventObservable.subscribe(new Action1<ErrorMessageEvent>() {
            @Override
            public void call(ErrorMessageEvent errorMessageEvent) {
                int code = errorMessageEvent.getErrorCode();
                String msg = errorMessageEvent.getMsg();
                switch (code){
                    case 500:
                        toast("服务器正在维护，请稍后再试!!!");
                        break;
                    case -1:
                        toast(msg);
                        break;
                    case -2:
                        toast(msg);
                        break;
                    default:
                        toast(msg);
                        break;
                }
            }
        });
    }
    private void toast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }
    private void setupDatabase() {
        // // 官方推荐将获取 DaoMaster 对象的方法放到 Application 层，这样将避免多次创建生成 Session 对象
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        //创建数据库shop.db  DaoMaster是创建和删除数据表
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constant.DB_NAME, null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取dao对象管理者
        daoSession = daoMaster.newSession();
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = BuildConfig.DEBUG;
        QueryBuilder.LOG_VALUES = BuildConfig.DEBUG;
    }
    //返回daoSession是操作实体类
    public DaoSession getDaoSession(){
        return daoSession;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        RxBus.get().unregister(Constant.ERRORMESSAGE,messageEventObservable);
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
