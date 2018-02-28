package cn.cnlinfo.news.ui.fragment.news;

import java.util.List;

import cn.cnlinfo.news.bean.NewsChannel;
import cn.cnlinfo.news.ui.base.IBasePresenter;
import rx.Subscription;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public interface NewsContact {
    interface View {
        void initViewPager(List<NewsChannel> newsChannels);

        void initRxBusEvent();
    }
    interface Presenter extends IBasePresenter {
        Subscription opChannelToDb();
    }
}
