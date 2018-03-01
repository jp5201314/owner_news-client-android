package cn.cnlinfo.news.ui.activity.channel;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.bean.NewsChannel;
import cn.cnlinfo.news.event.AddOrDeleteChannelEvent;
import cn.cnlinfo.news.rx.rxbus.RxBus;
import cn.cnlinfo.news.ui.activity.BaseActivity;
import cn.cnlinfo.news.ui.activity.channel.adapter.NewsChannelAdapter;
import cn.cnlinfo.news.ui.callback.SimpleItemTouchHelperCallback;
import cn.cnlinfo.news.utils.BaseSpacesItemDecoration;
import cn.cnlinfo.news.utils.MeasureUtil;
import rx.functions.Action1;

/**
 * 新闻栏目管理界面
 */
public class ChannelActivity extends BaseActivity implements ChannelContact.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_checked_list)
    RecyclerView rvCheckedList;
    @BindView(R.id.rv_unchecked_list)
    RecyclerView rvUncheckedList;
    private Unbinder unbinder;
    private ChannelPresenter channelPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        unbinder = ButterKnife.bind(this);
        setStatusBarColor(R.color.color_green_009688);//设置状态栏颜色
        initToolBar(toolbar, true, "栏目管理");
        channelPresenter = new ChannelPresenter(this);
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

    @Override
    public void initTwoRecycleView(List<NewsChannel> selectedChannel, List<NewsChannel> unSelectedChannel) {
        rvCheckedList.setLayoutManager(new GridLayoutManager(this,4,LinearLayout.VERTICAL ,false));
        rvCheckedList.addItemDecoration(new BaseSpacesItemDecoration(MeasureUtil.dip2px(this,8)));
        //给子项设置动画以及相应改变的动画时间
        rvCheckedList.setItemAnimator(new DefaultItemAnimator());
        rvCheckedList.getItemAnimator().setAddDuration(250);
        rvCheckedList.getItemAnimator().setMoveDuration(250);
        rvCheckedList.getItemAnimator().setChangeDuration(250);
        rvCheckedList.getItemAnimator().setRemoveDuration(250);
        final NewsChannelAdapter newsChannelAdapter1 = new NewsChannelAdapter(this,selectedChannel);
        rvCheckedList.setAdapter(newsChannelAdapter1);
        // 只有我的频道可以拖拽排序
        SimpleItemTouchHelperCallback callback1 = new SimpleItemTouchHelperCallback(newsChannelAdapter1);
        ItemTouchHelper itemTouchHelper1 = new ItemTouchHelper(callback1);
        itemTouchHelper1.attachToRecyclerView(rvCheckedList);
        newsChannelAdapter1.setItemTouchHelper(callback1);

        newsChannelAdapter1.setItemMoveListener(new NewsChannelAdapter.OnItemMoveListener() {
            @Override
            public void onItemMove(int fromPosition, int toPosition) {
                // 拖拽交换位置的时候通知代理更新数据库
                channelPresenter.toSwapItem(fromPosition, toPosition);
            }
        });


        rvUncheckedList.setLayoutManager(new GridLayoutManager(this,4,LinearLayout.VERTICAL ,false));
        rvUncheckedList.addItemDecoration(new BaseSpacesItemDecoration(MeasureUtil.dip2px(this,8)));
        //给子项设置动画以及相应改变的动画时间
        rvUncheckedList.setItemAnimator(new DefaultItemAnimator());
        rvUncheckedList.getItemAnimator().setAddDuration(250);
        rvUncheckedList.getItemAnimator().setMoveDuration(250);
        rvUncheckedList.getItemAnimator().setChangeDuration(250);
        rvUncheckedList.getItemAnimator().setRemoveDuration(250);
        final NewsChannelAdapter newsChannelAdapter2 = new NewsChannelAdapter(this,unSelectedChannel);
        rvUncheckedList.setAdapter(newsChannelAdapter2);


    }

    //接收通知界面更新，增加频道还是删除频道
    @Override
    public void initRxBusEvent() {
        RxBus.get().register("AddOrDeleteChannel", AddOrDeleteChannelEvent.class).subscribe(new Action1<AddOrDeleteChannelEvent>() {
            @Override
            public void call(AddOrDeleteChannelEvent addOrDeleteChannelEvent) {
             channelPresenter.toAddOrRemoveChannel(addOrDeleteChannelEvent.getChannelName(),addOrDeleteChannelEvent.isSelected());
            }
        });
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void toast(String msg) {
        toast(msg);
    }
}
