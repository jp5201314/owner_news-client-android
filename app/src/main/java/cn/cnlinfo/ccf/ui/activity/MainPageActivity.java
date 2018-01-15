package cn.cnlinfo.ccf.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tendcloud.tenddata.TCAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.adapter.MainPageFragmentAdapter;
import cn.cnlinfo.ccf.manager.AppManage;
import cn.cnlinfo.ccf.view.StopScrollViewPager;

public class MainPageActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.vp)
    StopScrollViewPager vp;
    @BindView(R.id.tv_gauage_panel)
    TextView tvGauagePanel;
    @BindView(R.id.tv_trading_center)
    TextView tvTradingCenter;
    @BindView(R.id.tv_cc_mall)
    TextView tvCcMall;
    @BindView(R.id.tv_cc_union)
    TextView tvCcUnion;
    @BindView(R.id.tv_main_page)
    TextView tvMainPage;
    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private MainPageFragmentAdapter pageFragmentAdapter;
    private Unbinder unbinder;
    @BindView(R.id.ibt_add)
    ImageButton ibtAdd;
    private long exitTime = 0;
    private long currentTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        unbinder = ButterKnife.bind(this);
        TCAgent.onPageStart(this, "主页");
        validLoadGuidePage();
        //设置为false是停止滑动ViewPager切换Fragment
        vp.setStopScroll(true);
        init();
    }
    private void init() {
        ibtBack.setVisibility(View.INVISIBLE);
        tvTitle.setText("主页");
        registerOnClickListener();
        tvMainPage.setBackgroundColor(getResources().getColor(R.color.color_blue_4d8cd6));
        ibtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSharedPreference.getInstance().logout();
                AppManage.getInstance().exit(MainPageActivity.this);
            }
        });

        pageFragmentAdapter = new MainPageFragmentAdapter(getSupportFragmentManager());
        vp.setAdapter(pageFragmentAdapter);
        vp.setOffscreenPageLimit(5);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setTvMainPageBackgroundColor();
                        break;
                    case 1:
                        setTvGauagePanelBackgroundColor();
                        break;
                    case 2:
                        setTvTradingCenterBackgroundColor();
                        break;
                    case 3:
                        setTvCcMallBackgroundColor();
                        break;
                    case 4:
                        setTvCcUnionBackgroundColor();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
    private void registerOnClickListener() {
        tvMainPage.setOnClickListener(this);
        tvGauagePanel.setOnClickListener(this);
        tvTradingCenter.setOnClickListener(this);
        tvCcMall.setOnClickListener(this);
        tvCcUnion.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        TCAgent.onPageEnd(this, "主页");
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.tv_main_page:
                vp.setCurrentItem(0, false);
                setTvMainPageBackgroundColor();

                break;
            case R.id.tv_gauage_panel:
                //smoothScroll为false就是去除切换fragment的动画效果
                vp.setCurrentItem(1, false);
                setTvGauagePanelBackgroundColor();

                break;
            case R.id.tv_trading_center:
                vp.setCurrentItem(2, false);
                setTvTradingCenterBackgroundColor();

                break;
            case R.id.tv_cc_mall:
                vp.setCurrentItem(3, false);
                setTvCcMallBackgroundColor();

                break;
            case R.id.tv_cc_union:
                vp.setCurrentItem(4, false);
                setTvCcUnionBackgroundColor();

                break;

        }
    }

    private void setTvMainPageBackgroundColor() {
        tvMainPage.setBackgroundColor(getResources().getColor(R.color.color_blue_4d8cd6));
        tvGauagePanel.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvCcUnion.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvCcMall.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvTradingCenter.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvTitle.setText("主页");
    }

    private void setTvGauagePanelBackgroundColor() {
        tvMainPage.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvGauagePanel.setBackgroundColor(getResources().getColor(R.color.color_blue_4d8cd6));
        tvCcUnion.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvCcMall.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvTradingCenter.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvTitle.setText("仪表盘");
    }

    private void setTvCcUnionBackgroundColor() {
        tvMainPage.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvGauagePanel.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvCcUnion.setBackgroundColor(getResources().getColor(R.color.color_blue_4d8cd6));
        tvCcMall.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvTradingCenter.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvTitle.setText("CC联盟");
    }

    private void setTvCcMallBackgroundColor() {
        tvMainPage.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvGauagePanel.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvCcUnion.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvCcMall.setBackgroundColor(getResources().getColor(R.color.color_blue_4d8cd6));
        tvTradingCenter.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvTitle.setText("CC商城");
    }

    private void setTvTradingCenterBackgroundColor() {
        tvMainPage.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvGauagePanel.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvCcUnion.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvCcMall.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvTradingCenter.setBackgroundColor(getResources().getColor(R.color.color_blue_4d8cd6));
        tvTitle.setText("交易中心");
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            currentTime = System.currentTimeMillis();
            if((currentTime-exitTime) > 2000){
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
}
