package cn.cnlinfo.news.ui.activity.main;

import java.util.List;

import cn.cnlinfo.news.bean.NewsChannel;
import cn.cnlinfo.news.ui.base.IBasePresenter;
import rx.Subscription;

/**
 * Created by JP on 2018/2/28 0028.
 */

public interface MainContact {
    interface View {
        void initViewPager(List<NewsChannel> newsChannels);

        void initRxBusEvent();
    }
    interface Presenter extends IBasePresenter{
        Subscription opChannelToDb();
    }
}
