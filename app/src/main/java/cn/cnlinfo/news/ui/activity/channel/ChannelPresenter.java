package cn.cnlinfo.news.ui.activity.channel;

import java.util.List;
import java.util.Map;

import cn.cnlinfo.news.bean.NewsChannel;
import cn.cnlinfo.news.ui.activity.channel.model.INewsChannelInteractor;
import cn.cnlinfo.news.ui.activity.channel.model.INewsChannelInteractorImpl;
import cn.cnlinfo.news.ui.callback.RequestCallback;

/**
 * Created by JP on 2018/3/1 0001.
 */

public class ChannelPresenter implements ChannelContact.Presenter,RequestCallback<Map<Boolean,List<NewsChannel>>> {

    private ChannelContact.View view;
    private INewsChannelInteractor iNewsChannelInteractor;


    public ChannelPresenter(ChannelContact.View view){
        this.view = view;
        view.initRxBusEvent();
        iNewsChannelInteractor = new INewsChannelInteractorImpl();
        iNewsChannelInteractor.channelDbOperate(this,"",null);
    }

    @Override
    public void toAddOrRemoveChannel(String channelName, boolean isSelected) {
        iNewsChannelInteractor.channelDbOperate(this,channelName,isSelected);
    }

    @Override
    public void toSwapItem(long formPos, long toPos) {
        iNewsChannelInteractor.channelDbSwap(this,formPos,toPos);
    }


    @Override
    public void beforeRequest() {

    }

    @Override
    public void requestError(String msg) {
        view.toast(msg);
    }

    @Override
    public void requestComplete() {
    }

    @Override
    public void requestSuccess(Map<Boolean, List<NewsChannel>> data) {
        //已选择的频道，未选择的频道
        view.initTwoRecycleView(data.get(true),data.get(false));
    }
}
