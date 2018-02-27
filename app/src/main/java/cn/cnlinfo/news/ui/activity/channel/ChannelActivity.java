package cn.cnlinfo.news.ui.activity.channel;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.ui.activity.BaseActivity;

/**
 * 新闻栏目管理界面
 */
public class ChannelActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_checked_list)
    RecyclerView rvCheckedList;
    @BindView(R.id.rv_unchecked_list)
    RecyclerView rvUncheckedList;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        unbinder = ButterKnife.bind(this);
        setStatusBarColor(R.color.color_green_009688);//设置状态栏颜色
        initToolBar(toolbar, true, "栏目管理");
    }

    //点击toolbar上的返回按钮结束当前activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            overridePendingTransition(R.anim.fade_entry,R.anim.fade_exit);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
