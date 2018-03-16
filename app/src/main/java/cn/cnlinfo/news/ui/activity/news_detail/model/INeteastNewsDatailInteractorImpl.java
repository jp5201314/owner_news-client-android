package cn.cnlinfo.news.ui.activity.news_detail.model;

import cn.cnlinfo.news.HostType;
import cn.cnlinfo.news.rx.entity.NeteastNewsDetail;
import cn.cnlinfo.news.rx.method.RetrofitManager;
import cn.cnlinfo.news.ui.callback.HandleRequestCallBack;
import rx.Subscription;

/**
 * Created by Administrator on 2018/3/8 0008.
 */

public class INeteastNewsDatailInteractorImpl implements INeteastNewsDatailInteractor {

    @Override
    public Subscription gainNewsDatailData(HandleRequestCallBack<NeteastNewsDetail> subscriber, String postId) {

        return RetrofitManager.getInstance(HostType.NETEASE_NEWS).toGainNeteastNewsDatailData(subscriber,postId);
    }
}
