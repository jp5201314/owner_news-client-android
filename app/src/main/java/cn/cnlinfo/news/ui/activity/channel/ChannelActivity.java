package cn.cnlinfo.news.ui.activity.channel;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import rx.Observable;
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
    private   NewsChannelAdapter newsChannelAdapter1;
    private   NewsChannelAdapter newsChannelAdapter2;
    private static final String CHANNELCHANGE = "channelChange";
    private boolean mChannelChange;
    private Observable<AddOrDeleteChannelEvent> mAddOrDeleteChannelObservable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        unbinder = ButterKnife.bind(this);
        setStatusBarColor(R.color.color_green_009688);//设置状态栏颜色
        initToolBar(toolbar, true, "栏目管理");
        toolbar.setBackgroundColor(getResources().getColor(R.color.color_green_009688));
        initRecycleView(rvCheckedList);
        initRecycleView(rvUncheckedList);
        channelPresenter = new ChannelPresenter(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //更改完成后通知界面刷新
        //RxBus.get().post(CHANNELCHANGE,mChannelChange);
        RxBus.get().unregister("AddOrDeleteChannel",mAddOrDeleteChannelObservable);
        unbinder.unbind();
    }

    @Override
    public void initTwoRecycleView(List<NewsChannel> selectedChannel, List<NewsChannel> unSelectedChannel) {
        newsChannelAdapter1 = new NewsChannelAdapter(this,selectedChannel);
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

        newsChannelAdapter2 = new NewsChannelAdapter(this,unSelectedChannel);
        rvUncheckedList.setAdapter(newsChannelAdapter2);
    }

    /**
     * 初始化RecyclerView的属性
     * @param recyclerView
     */
    public void initRecycleView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(this,4, LinearLayout.VERTICAL ,false));
        recyclerView.addItemDecoration(new BaseSpacesItemDecoration(MeasureUtil.dip2px(this,8)));
        //给子项设置动画以及相应改变的动画时间
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.getItemAnimator().setAddDuration(250);
        recyclerView.getItemAnimator().setMoveDuration(250);
        recyclerView.getItemAnimator().setChangeDuration(250);
        recyclerView.getItemAnimator().setRemoveDuration(250);
    }

    //接收通知界面更新，增加频道还是删除频道
    @Override
    public void initRxBusEvent() {
        mAddOrDeleteChannelObservable = RxBus.get().register("AddOrDeleteChannel", AddOrDeleteChannelEvent.class);
        mAddOrDeleteChannelObservable.subscribe(new Action1<AddOrDeleteChannelEvent>() {
            @Override
            public void call(AddOrDeleteChannelEvent addOrDeleteChannelEvent) {
                channelPresenter.toAddOrRemoveChannel(addOrDeleteChannelEvent.getChannelName(),addOrDeleteChannelEvent.isSelected());
               // mChannelChange = true;
                if (addOrDeleteChannelEvent.isSelected()){
                    //若是已选择则进行增加已订阅的  适配器1增加项目 适配器2删除增加项目的位置
                   newsChannelAdapter1.addItem(newsChannelAdapter1.getItemCount(),newsChannelAdapter2.getData().get(addOrDeleteChannelEvent.getPosition()));
                   newsChannelAdapter2.deleteItem(addOrDeleteChannelEvent.getPosition(),newsChannelAdapter2.getData().get(addOrDeleteChannelEvent.getPosition()));
                }else {
                    newsChannelAdapter2.addItem(newsChannelAdapter2.getItemCount(),newsChannelAdapter1.getData().get(addOrDeleteChannelEvent.getPosition()));
                    newsChannelAdapter1.deleteItem(addOrDeleteChannelEvent.getPosition(),newsChannelAdapter1.getData().get(addOrDeleteChannelEvent.getPosition()));
                }
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
