package cn.cnlinfo.news.ui.activity;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.shizhefei.mvc.MVCHelper;

import cc.cloudist.acplibrary.ACProgressFlower;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.UserSharedPreference;
import cn.cnlinfo.news.dialog.DialogCreater;
import cn.cnlinfo.news.inter.IActivityFinish;
import cn.cnlinfo.news.inter.IComponentContainer;
import cn.cnlinfo.news.inter.ILifeCycleComponent;
import cn.cnlinfo.news.manager.AppManage;
import cn.cnlinfo.news.manager.LifeCycleComponentManager;
import cn.cnlinfo.news.manager.PhoneManager;
import cn.cnlinfo.news.manager.SystemBarTintManager;
import cn.cnlinfo.news.mvc.factory.MyLoadViewFactory;
import cn.cnlinfo.news.receiver.NetworkConnectChangedReceiver;
import cn.cnlinfo.news.ui.activity.login.LoginRegisterActivity;
import cn.cnlinfo.news.view.RefreshHeaderView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class BaseActivity extends AppCompatActivity implements IComponentContainer, IActivityFinish {

    public static final String BROADCAST_NETWORK_FLAG = "cn.cnlinfo.ccf.net";
    private LifeCycleComponentManager mComponentContainer = new LifeCycleComponentManager();
    protected ACProgressFlower waitingDialog;
    private NetworkConnectChangedReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManage.getInstance().addActivity(this);
        receiver = new NetworkConnectChangedReceiver();
        registerNetworkConnectChangedReceiver();
        MVCHelper.setLoadViewFractory(new MyLoadViewFactory());
    }

    private void registerNetworkConnectChangedReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction(BROADCAST_NETWORK_FLAG);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }


    /**
     * 设置状态栏的背景颜色
     * @param color
     */
    protected void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(color);//通知栏所需颜色
        }
    }

    /**
     * 初始化 Toolbar
     *
     * @param toolbar
     * @param homeAsUpEnabled
     * @param title
     * http://blog.csdn.net/lovexieyuan520/article/details/9974929  解释setDisplayHomeAsUpEnabled
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.color_green_009688));//设置Toolbar的背景颜色
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);//给左上角图标的左边加上一个返回的图标并且可以点击
        getSupportActionBar().setHomeButtonEnabled(true);//setHomeButtonEnabled这个小于4.0版本的默认值为true的。但是在4.0及其以上是false，该方法的作用：决定左上角的图标是否可以点击。没有向左的小图标。 true 图标可以点击  false 不可以点击。
    }

    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, int resTitle) {
        initToolBar(toolbar, homeAsUpEnabled, getString(resTitle));
    }

    protected void showWaitingDialog(boolean show) {
        showWaitingDialog(show, getString(R.string.please_wait));
    }

    protected void toLogin(){
        startActivity(new Intent(this,LoginRegisterActivity.class));
    }

    protected void showWaitingDialog(boolean show, String waitingNotice) {
        if (!show) {
            waitingDialog.dismiss();
            return;
        }
        waitingDialog = DialogCreater.createProgressDialog(this, waitingNotice);
        waitingDialog.show();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void finish() {
        AppManage.getInstance().finishActivity(this);
    }

    @Override
    public void finishActivity() {
        super.finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mComponentContainer.onBecomesVisibleFromTotallyInvisible();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mComponentContainer.onBecomesVisibleFromPartiallyInvisible();
        Intent intent = new Intent();
        intent.setAction(BROADCAST_NETWORK_FLAG);
        sendBroadcast(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mComponentContainer.onBecomesPartiallyInvisible();
    }


    @Override
    public void onStop() {
        super.onStop();
        mComponentContainer.onBecomesTotallyInvisible();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mComponentContainer.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 开始执行contentView动画
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void startContentViewAnimation(View contentView, AnimatorListenerAdapter onAnimationEnd) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(contentView, "alpha", 1),
                ObjectAnimator.ofFloat(contentView, "translationY", 0)
        );
        set.setDuration(400).start();
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.addListener(onAnimationEnd);
    }

    @Override
    public void addComponent(ILifeCycleComponent component) {
        mComponentContainer.addComponent(component);
    }


    protected void setMaterialHeader(PtrClassicFrameLayout ptr) {
        RefreshHeaderView ptrHeader = new RefreshHeaderView(getApplicationContext());
        ptrHeader.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        ptrHeader.setPtrFrameLayout(ptr);
        ptr.setLoadingMinTime(800);
        ptr.setDurationToCloseHeader(800);
        ptr.setHeaderView(ptrHeader);
        ptr.addPtrUIHandler(ptrHeader);
    }

    /**
     * 验证是否登录,如果未登录,则跳转登录页面
     *
     * @return
     */
    protected boolean validLogin() {
        if (!UserSharedPreference.getInstance().hasLogined()) {
            startActivity(new Intent(BaseActivity.this, LoginRegisterActivity.class));
            AppManage.getInstance().finishOther();
            return false;
        }
        return true;
    }

    /**
     * 验证是否新版,如果新版,则跳转引导页
     *
     * @return
     */
    protected boolean validNewVersion() {
        int nowVersionCode = PhoneManager.getVersionInfo().versionCode;
        UserSharedPreference userSharedPreference = UserSharedPreference.getInstance();
        if (userSharedPreference.isNewVersionCode(nowVersionCode)) {
            userSharedPreference.setLatestVersionCode(nowVersionCode);
            startActivity(new Intent(BaseActivity.this, GuideActivity.class));
            finish();
            return true;
        }
        return false;
    }


    protected String getRunningActivityName() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        String shortClassName = info.topActivity.getShortClassName(); //类名
        String className = info.topActivity.getClassName(); //完整类名
        String packageName = info.topActivity.getPackageName();//包名
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        return shortClassName;
    }

    /**
     * Override this function when you need control whether you will cancel OkHttpFinal after Destroy
     *
     * @return boolean
     */
    protected boolean cancelOkHttpFinalAfterDestory() {
        return true;
    }

    protected void exit(){
        UserSharedPreference.getInstance().logout();
        AppManage.getInstance().exit(this);
    }
}
