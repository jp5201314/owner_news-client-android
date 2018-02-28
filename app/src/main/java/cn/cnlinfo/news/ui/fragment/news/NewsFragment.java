package cn.cnlinfo.news.ui.fragment.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.bean.NewsChannel;
import cn.cnlinfo.news.rx.rxbus.RxBus;
import cn.cnlinfo.news.ui.fragment.BaseFragment;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by JP on 2017/10/11 0011.
 */

public class NewsFragment extends BaseFragment implements NewsContact.View {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private Unbinder unbinder;
    private Observable<Boolean> mChannelObservable;
    private NewsPresenter newsListPresenter;
    private static final String CHANNELCHANGE = "channelChange";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsListPresenter = new NewsPresenter(this);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_news);
        unbinder = ButterKnife.bind(this, getContentView());
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
        //注销关注频道变化的观察者
        RxBus.get().unregister(CHANNELCHANGE, mChannelObservable);
    }

    @Override
    public void initViewPager(List<NewsChannel> newsChannels) {
        //是否初始化数据成功
      /*  for (NewsChannel newsChannel : newsChannels) {
            Logger.d(newsChannel.toString());
        }*/

    }

    @Override
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
    }
}