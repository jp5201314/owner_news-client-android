package cn.cnlinfo.ccf.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.adapter.MainPageFragmentAdapter;
import cn.cnlinfo.ccf.fragment.CCMallFragment;
import cn.cnlinfo.ccf.fragment.CCUnionFragment;
import cn.cnlinfo.ccf.fragment.GaugePanelFragment;
import cn.cnlinfo.ccf.fragment.MainPageFragment;
import cn.cnlinfo.ccf.fragment.TradingCenterFragment;
import cn.cnlinfo.ccf.view.StopScrollViewPager;

public class MainPageActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{

    @BindView(R.id.vp)
    StopScrollViewPager vp;
    @BindView(R.id.bt_negative_button)
    BottomNavigationBar btNegativeButton;
    private MainPageFragmentAdapter pageFragmentAdapter;
    private Unbinder unbinder;
    private long exitTime = 0;
    private long currentTime = 0;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        unbinder = ButterKnife.bind(this);
        validLoadGuidePage();
        //设置为false是停止滑动ViewPager切换Fragment
        vp.setStopScroll(true);
        init();
    }
    /**
     * 验证是否加载引导页
     */
    private void validLoadGuidePage() {
        if (!validNewVersion()) {
            if (validLogin()) {
                if (UserSharedPreference.getInstance().getIsFirstLogin()) {
                    UserSharedPreference.getInstance().setIsFirstLogin(false);
                } else {

                }
            } else {
                finish();
            }
        }
    }

    private void init() {
        fragmentList = new ArrayList<>();
        pageFragmentAdapter = new MainPageFragmentAdapter(getFragmentList(), getSupportFragmentManager());
        vp.setAdapter(pageFragmentAdapter);
        vp.setOffscreenPageLimit(2);
        setBtNegativeButton();
    }

    //将fragment放入到集合中
    private List<Fragment> getFragmentList(){
        fragmentList.add(0,new MainPageFragment());
        fragmentList.add(1,new GaugePanelFragment());
        fragmentList.add(2,new TradingCenterFragment());
        fragmentList.add(3,new CCMallFragment());
        fragmentList.add(4,new CCUnionFragment());
        return fragmentList;
    }

    private void setBtNegativeButton(){
        btNegativeButton.setTabSelectedListener(this);
        btNegativeButton.setMode(BottomNavigationBar.MODE_FIXED);
        btNegativeButton.setFirstSelectedPosition(0);
        btNegativeButton.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        btNegativeButton.setBarBackgroundColor(android.R.color.white);

        TextBadgeItem badgeItem = new TextBadgeItem().setBackgroundColor(Color.RED).setText("99")//设置角标内容
                .setHideOnSelect(true); //设置被选中时隐藏角标
        btNegativeButton.setActiveColor(R.color.colorAccent) //设置选中的颜色
                .setInActiveColor(R.color.colorPrimary);//未选中颜色

        btNegativeButton.addItem(new BottomNavigationItem(R.drawable.ic_home_page, "首页"))//添加图标和文字
                .addItem(new BottomNavigationItem(R.drawable.ic_trading_center,"仪表盘"))
                .addItem(new BottomNavigationItem(R.drawable.ic_trading_center, "店铺"))
                .addItem(new BottomNavigationItem(R.drawable.ic_cc_mall, "购物车"))
                .addItem(new BottomNavigationItem(R.drawable.ic_cc_union, "我的")
                        .setBadgeItem(badgeItem))
                .initialise();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    //判断连续按返回键退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            currentTime = System.currentTimeMillis();
            if ((currentTime - exitTime) > 2000) {
                Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
                exitTime = currentTime;
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //点击tab换到该位置的fragment
    @Override
    public void onTabSelected(int position) {
        btNegativeButton.selectTab(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
