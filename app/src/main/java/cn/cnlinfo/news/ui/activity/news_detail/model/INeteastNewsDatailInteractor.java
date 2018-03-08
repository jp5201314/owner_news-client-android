package cn.cnlinfo.news.ui.activity.news_detail.model;

import cn.cnlinfo.news.rx.entity.NeteastNewsDetail;
import cn.cnlinfo.news.ui.callback.HandleRequestCallBack;
import rx.Subscription;

/**
 * Created by Administrator on 2018/3/8 0008.
 */

public interface INeteastNewsDatailInteractor {
    //获取新闻详情数据
    Subscription gainNewsDatailData(HandleRequestCallBack<NeteastNewsDetail> subscriber, String postId);
}
