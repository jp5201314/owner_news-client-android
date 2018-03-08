package cn.cnlinfo.news.ui.activity.news_detail;

import cn.cnlinfo.news.rx.entity.NeteastNewsDetail;
import cn.cnlinfo.news.ui.base.IBasePresenter;

/**
 * Created by JP on 2018/3/8 0008.
 */

public interface NeteastNewsDetailContact  {

    interface View {
        //接收到新闻详情数据
        void successReceiveNewsDatail(NeteastNewsDetail neteastNewsDetail);
        void fialReceiveNewsDatail(String msg);
    }
    interface Presenter extends IBasePresenter{
        // TODO: 2018/3/8 0008  获取新闻详情
        void toGainNewsDetail();
    }
}
