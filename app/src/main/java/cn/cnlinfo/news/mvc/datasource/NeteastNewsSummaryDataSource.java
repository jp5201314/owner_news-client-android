package cn.cnlinfo.news.mvc.datasource;

import com.orhanobut.logger.Logger;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;

import java.util.List;

import cn.cnlinfo.news.Constant;
import cn.cnlinfo.news.HostType;
import cn.cnlinfo.news.event.ErrorMessageEvent;
import cn.cnlinfo.news.rx.entity.NeteastNewsSummary;
import cn.cnlinfo.news.rx.method.RetrofitManager;
import cn.cnlinfo.news.rx.rxbus.RxBus;
import rx.Subscriber;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class NeteastNewsSummaryDataSource implements IAsyncDataSource<List<NeteastNewsSummary>>{

    private String channelId;
    private String channelType;
    private int startPage = 0;
    private int number = 10;
    private int maxPage = 0;

    public NeteastNewsSummaryDataSource(String channel_id,String channel_type){
        this.channelId = channel_id;
        this.channelType = channel_type;
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<NeteastNewsSummary>> sender) throws Exception {
        maxPage = 0;
        return loadUserLeaveMessageRecordList(sender,0);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<NeteastNewsSummary>> sender) throws Exception {
        return loadUserLeaveMessageRecordList(sender,maxPage);
    }

    private RequestHandle loadUserLeaveMessageRecordList(final ResponseSender<List<NeteastNewsSummary>> sender, final int page){
        RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO).toLoadNeteastNewsSummaryListData(new Subscriber<List<NeteastNewsSummary>>() {
            @Override
            public void onCompleted() {
               maxPage +=number;
            }

            @Override
            public void onError(Throwable e) {
                RxBus.get().post(Constant.ERRORMESSAGE,new ErrorMessageEvent(e.getMessage()));
            }

            @Override
            public void onNext(List<NeteastNewsSummary> neteastNewsSummaries) {
                sender.sendData(neteastNewsSummaries);
            }
        },channelId,channelType,page);
        return new OkHttpRequestHandler();
    }


    @Override
    public boolean hasMore() {
        return startPage<maxPage;
    }
}
