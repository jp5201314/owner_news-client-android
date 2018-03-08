package cn.cnlinfo.news.ui.activity.news_detail;

import cn.cnlinfo.news.Constant;
import cn.cnlinfo.news.rx.entity.NeteastNewsDetail;
import cn.cnlinfo.news.rx.rxbus.RxBus;
import cn.cnlinfo.news.ui.activity.news_detail.NeteastNewsDetailContact.Presenter;
import cn.cnlinfo.news.ui.activity.news_detail.model.INeteastNewsDatailInteractor;
import cn.cnlinfo.news.ui.activity.news_detail.model.INeteastNewsDatailInteractorImpl;
import cn.cnlinfo.news.ui.callback.HandleRequestCallBack;

/**
 * Created by Administrator on 2018/3/8 0008.
 */

public class NeteastNewsDetailPresenter implements Presenter {
    private String postId;
    private NeteastNewsDetailContact.View view;
    private INeteastNewsDatailInteractor iNeteastNewsDatailInteractor;

    public NeteastNewsDetailPresenter (NeteastNewsDetailContact.View view,String postId){
        this.view = view;
        this.postId = postId;
        iNeteastNewsDatailInteractor = new INeteastNewsDatailInteractorImpl();
    }

    @Override
    public void toGainNewsDetail() {
        iNeteastNewsDatailInteractor.gainNewsDatailData(new HandleRequestCallBack<NeteastNewsDetail>() {
            @Override
            public void requestDataStart() {

            }

            @Override
            public void requestDataSuccess(NeteastNewsDetail neteastNewsDetail) {
                view.successReceiveNewsDatail(neteastNewsDetail);
            }

            @Override
            public void requestDataFail(String msg) {
                RxBus.get().post(Constant.ERRORMESSAGE,msg);
            }

            @Override
            public void requestCompleted() {

            }
        }, postId);
    }
}
