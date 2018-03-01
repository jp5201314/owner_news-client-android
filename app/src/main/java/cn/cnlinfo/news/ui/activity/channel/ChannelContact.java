package cn.cnlinfo.news.ui.activity.channel;

import java.util.List;

import cn.cnlinfo.news.bean.NewsChannel;
import cn.cnlinfo.news.ui.base.IBasePresenter;
import cn.cnlinfo.news.ui.base.IBaseView;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public interface ChannelContact {
    interface View extends IBaseView{
        void initTwoRecycleView(List<NewsChannel> selectedChannel,List<NewsChannel> unSelectedChannel);
        void initRxBusEvent();
    }
    interface Presenter extends IBasePresenter{
        void toAddOrRemoveChannel(String channelName,boolean isSelected);
        void toSwapItem(long formPos,long toPos);
    }
}
