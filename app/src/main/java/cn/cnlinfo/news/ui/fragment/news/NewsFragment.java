package cn.cnlinfo.news.ui.fragment.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.bean.NewsChannel;
import cn.cnlinfo.news.rx.rxbus.RxBus;
import cn.cnlinfo.news.ui.fragment.BaseFragment;
import cn.cnlinfo.news.ui.fragment.news.adapter.NewsChannelFragmentAdapter;
import cn.cnlinfo.news.utils.ViewUtil;

/**
 * Created by JP on 2017/10/11 0011.
 */

public class NewsFragment extends BaseFragment implements NewsContact.View {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private Unbinder unbinder;
    //private Observable<Boolean> mChannelObservable;
    private NewsPresenter newsListPresenter;
    //private static final String CHANNELCHANGE = "channelChange";

 /*   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_news);
        unbinder = ButterKnife.bind(this, getContentView());
        Logger.d("onCreateViewLazy");
    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        newsListPresenter = new NewsPresenter(this);
        Logger.d("onFragmentStartLazy");
    }

    @Override
    protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();
        Logger.d("onFragmentStopLazy");
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
        Logger.d("onDestroyViewLazy");
        //注销关注频道变化的观察者
        //RxBus.get().unregister(CHANNELCHANGE, mChannelObservable);
    }

    @Override
    public void initViewPager(List<NewsChannel> newsChannels) {
        //是否初始化数据成功
      /*  for (NewsChannel newsChannel : newsChannels) {
            Logger.d(newsChannel.toString());
        }*/
        List<NewsListFragment> newsListFragments = new ArrayList<>();
        List<String> tabTitle = new ArrayList<>();
        if (newsChannels!=null){
            for (int i = 0; i < newsChannels.size(); i++) {
                NewsChannel newsChannel = newsChannels.get(i);
                tabTitle.add(newsChannel.getNewsChannelName());
                newsListFragments.add(NewsListFragment.newInstace(newsChannel.getNewsChannelId(),newsChannel.getNewsChannelType(),newsChannel.getNewsChannelIndex()));
            }
            if (viewPager.getAdapter() == null) {
                // 初始化ViewPager
                NewsChannelFragmentAdapter adapter = new NewsChannelFragmentAdapter(getChildFragmentManager(),
                        newsListFragments, tabTitle);
                viewPager.setAdapter(adapter);
            } else {
                final NewsChannelFragmentAdapter adapter = (NewsChannelFragmentAdapter) viewPager.getAdapter();
                adapter.updateFragments(newsListFragments, tabTitle);
            }
            viewPager.setCurrentItem(0, false);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setScrollPosition(0, 0, true);
            // 根据Tab的长度动态设置TabLayout的模式
            ViewUtil.dynamicSetTabLayoutMode(tabLayout);
            setOnTabSelectEvent(viewPager, tabLayout);
        }else {
            toast("数据异常");
        }

    }
    /**
     * 用户已选择tab的情况再次点击该tab，列表返回顶部，需要在setupWithViewPager后设置比如监听会把覆盖
     *
     * @param viewPager
     * @param tabLayout TabLayout
     */
    protected void setOnTabSelectEvent(final ViewPager viewPager, final TabLayout tabLayout) {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                RxBus.get().post("enableRefreshLayoutOrScrollRecyclerView", tab.getPosition());
            }
        });
    }
   /* @Override
    public void initRxBusEvent() {
        mChannelObservable = RxBus.get().register(CHANNELCHANGE, Boolean.class);
        //若是用户订阅了新的频道，数据发生改变，通知代理去更新数据库中各个频道所处的位置
        mChannelObservable.subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    newsListPresenter.opChannelToDb();
                }
            }
        });
    }*/
}