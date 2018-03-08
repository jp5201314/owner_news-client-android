package cn.cnlinfo.news.ui.activity.main;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.UserSharedPreference;
import cn.cnlinfo.news.adapter.MainPageFragmentAdapter;
import cn.cnlinfo.news.dialog.DialogCreater;
import cn.cnlinfo.news.ui.activity.BaseActivity;
import cn.cnlinfo.news.ui.activity.channel.ChannelActivity;
import cn.cnlinfo.news.ui.fragment.CCMallFragment;
import cn.cnlinfo.news.ui.fragment.CCUnionFragment;
import cn.cnlinfo.news.ui.fragment.image.ImageFragment;
import cn.cnlinfo.news.ui.fragment.TradingCenterFragment;
import cn.cnlinfo.news.ui.fragment.news.NewsFragment;
import cn.cnlinfo.news.utils.RxHeadImageTool;
import cn.cnlinfo.news.utils.RxPermissionsTool;
import cn.cnlinfo.news.utils.RxToast;
import cn.cnlinfo.news.utils.ToastTool;
import cn.cnlinfo.news.view.StopScrollViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainPageActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener,ViewPager.OnPageChangeListener, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.vp)
    StopScrollViewPager vp;
    @BindView(R.id.bt_negative_button)
    BottomNavigationBar btNegativeButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private MainPageFragmentAdapter pageFragmentAdapter;
    private Unbinder unbinder;
    private long exitTime = 0;
    private long currentTime = 0;
    private List<Fragment> fragmentList;
    private CircleImageView head_image;
    private TextView userName;
    private TextView userDescribe;
    private ActionSheetDialog headImageDialog;
    private final int CODE_TAKE_PHOTO = 1;//相机RequestCode
    public final int CODE_SELECT_IMAGE = 2;//相册RequestCode
    private Uri photoUri;//拍照图片的存储路径的Uri
    private String []bottomNavigationBarTitles = {"新闻","图片","视频","商城","百度"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        unbinder = ButterKnife.bind(this);
        validLoadGuidePage();//验证引导指导页
        //设置为false是停止滑动ViewPager切换Fragment
        vp.setStopScroll(true);
        initToolBar(toolbar,true,"新闻");
        toolbar.setBackgroundColor(getResources().getColor(R.color.color_green_009688));
        setStatusBarColor(R.color.color_green_009688);//设置状态栏颜色
        init();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //注册侧面导航栏项的点击监听
        navView.setNavigationItemSelectedListener(this);
        View view = navView.getHeaderView(0);//获取头部控件View  xml文件中静态添加了头部控件布局 app:headerLayout="@layout/nav_header_main"
        //navView.inflateHeaderView(R.layout.nav_header_main);//动态添加头部控件布局，删除xml文件中 app:headerLayout="@layout/nav_header_main"
        head_image = view.findViewById(R.id.ri_head_image);
        userName = view.findViewById(R.id.tv_name);//用户名字
        userDescribe = view.findViewById(R.id.tv_content);//用户描述
        head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headImageDialog = DialogCreater.createActionSheetDialog(MainPageActivity.this, "选择头像来源", new String[]{"照相机", "相册"}, new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            /**
                             * http://blog.csdn.net/u011150924/article/details/71748464?fps=1&locationNum=4
                             * 调起相册和相机获取相应的路径
                             */
                            case 0:
                                if (Build.VERSION.SDK_INT >= 23) {
                                    RxPermissionsTool.with(MainPageActivity.this).addPermission(Manifest.permission.CAMERA).addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).initPermission();
                                }
                                photoUri = RxHeadImageTool.tunedUpSysCamera(MainPageActivity.this);
                                if (headImageDialog.isShowing()) {
                                    headImageDialog.dismiss();
                                }
                                break;
                            case 1:
                                if (Build.VERSION.SDK_INT >= 23) {
                                    RxPermissionsTool.with(MainPageActivity.this).addPermission(Manifest.permission.READ_EXTERNAL_STORAGE).initPermission();
                                }
                                RxHeadImageTool.tunedUpSysAlbum(MainPageActivity.this);
                                if (headImageDialog.isShowing()) {
                                    headImageDialog.dismiss();
                                }
                                break;
                        }
                    }
                });
                headImageDialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //调起相机成功并成功返回结果
            case CODE_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        if (data.hasExtra("data")) {
                            Log.i("URI", "data is not null");
                            Bitmap bitmap = data.getParcelableExtra("data");
                            head_image.setImageBitmap(bitmap);//imageView即为当前页面需要展示照片的控件，可替换
                        }
                    } else {
                        Log.i("URI", "Data is null");
                        if (Build.VERSION.SDK_INT >= 24) {
                            Bitmap bitmap = null;
                            try {
                                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(photoUri));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            head_image.setImageBitmap(bitmap);
                        } else {
                            Bitmap bitmap = BitmapFactory.decodeFile(photoUri.getPath());
                            head_image.setImageBitmap(bitmap);
                        }
                    }
                }
                break;
            /**
             * 成功调起访问相册并返回结果
             */
            case CODE_SELECT_IMAGE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Bitmap bitmap = null;
                        try {

                            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        head_image.setImageBitmap(bitmap);
                    }
                }
                break;
        }
    }

    /**
     * 验证是否加载引导页
     */
    private void validLoadGuidePage() {
        if (!validNewVersion()) {
            if (validLogin()) {
                if (UserSharedPreference.getInstance().getIsFirstLogin()) {
                    UserSharedPreference.getInstance().setIsFirstLogin(false);
                }
            } else {
                finish();
            }
        }
    }

    //加入选择菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_channel,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_channel) {
            toChannelActivity();
            return true;
        }
        return false;
    }
    //跳转到新闻频道界面
    private void toChannelActivity() {
        startActivity(new Intent(this,ChannelActivity.class));
        overridePendingTransition(R.anim.fade_entry,R.anim.fade_exit);
    }

    //初始化界面和底部导航栏
    private void init() {
        fragmentList = new ArrayList<>();
        pageFragmentAdapter = new MainPageFragmentAdapter(getFragmentList(), getSupportFragmentManager());
        vp.setAdapter(pageFragmentAdapter);
        vp.setOffscreenPageLimit(1);
        vp.setOnPageChangeListener(this);
        setBtNegativeButton();
    }

    //将fragment放入到集合中
    private List<Fragment> getFragmentList() {
        fragmentList.add(0, new NewsFragment());
        fragmentList.add(1, new ImageFragment());
        fragmentList.add(2, new TradingCenterFragment());
        fragmentList.add(3, new CCMallFragment());
        fragmentList.add(4, new CCUnionFragment());
        return fragmentList;
    }

    private void setBtNegativeButton() {
        //设置底部bar之间的布局模式
        btNegativeButton.setMode(BottomNavigationBar.MODE_FIXED);
        btNegativeButton.setFirstSelectedPosition(0);
        btNegativeButton.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        btNegativeButton.setBarBackgroundColor(android.R.color.white);

        TextBadgeItem badgeItem = new TextBadgeItem().setBackgroundColor(Color.RED).setText("99")//设置角标内容
                .setHideOnSelect(true); //设置被选中时隐藏角标
        btNegativeButton.setActiveColor(R.color.colorAccent) //设置选中的颜色
                .setInActiveColor(R.color.colorPrimary);//未选中颜色

        btNegativeButton.addItem(new BottomNavigationItem(R.drawable.ic_home_page, bottomNavigationBarTitles[0]))//添加图标和文字
                .addItem(new BottomNavigationItem(R.drawable.ic_trading_center, bottomNavigationBarTitles[1]))
                .addItem(new BottomNavigationItem(R.drawable.ic_trading_center, bottomNavigationBarTitles[2]))
                .addItem(new BottomNavigationItem(R.drawable.ic_cc_mall, bottomNavigationBarTitles[3]))
                .addItem(new BottomNavigationItem(R.drawable.ic_cc_union, bottomNavigationBarTitles[4])
                        .setBadgeItem(badgeItem))
                .initialise();
        btNegativeButton.setTabSelectedListener(this);
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
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {//按返回键如果抽屉开着的，按下就关闭
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if (headImageDialog.isShowing()) {//按返回键如果选择头像的dialog显示着的，按下就关闭
                    headImageDialog.dismiss();
                }
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 底部导航栏选择监听
     *
     * @param position
     */
    @Override
    public void onTabSelected(int position) {
        vp.setCurrentItem(position);//选中底部导航栏tab切换相应的fragment
        toolbar.setTitle(bottomNavigationBarTitles[position]);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    /**
     * viewpager页面滑动监听
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        btNegativeButton.selectTab(position);
        toolbar.setTitle(bottomNavigationBarTitles[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //设置各个控件的监听事件
    @OnClick({R.id.toolbar, R.id.vp, R.id.bt_negative_button, R.id.fab, R.id.nav_view, R.id.drawer_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar:
                break;
            case R.id.vp:
                break;
            case R.id.bt_negative_button:
                break;
            case R.id.fab:
                //点击悬浮按钮执行的操作
                Snackbar.make(fab, "start your action", Snackbar.LENGTH_LONG).setAction("set action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastTool.showShort(MainPageActivity.this, "your action");
                    }
                }).show();

                break;
            case R.id.nav_view:

                break;
            case R.id.drawer_layout:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_news:
                RxToast.info("import");
                break;
            case R.id.nav_pic:
                RxToast.info("Gallery");
                break;
            case R.id.nav_video:
                RxToast.info("Tools");
                break;
            case R.id.nav_setting:
                RxToast.info("Send");
                break;
            case R.id.nav_share:
                RxToast.info("Share");
                break;
            case R.id.nav_message:
                RxToast.info("Slideshow");
                break;
            default:
                break;
        }
        //当选择侧滑菜单中的某一项就关闭抽屉
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
