package cn.cnlinfo.news.mvc.datasource;

import com.orhanobut.logger.Logger;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;

import java.util.List;

import cn.cnlinfo.news.Constant;
import cn.cnlinfo.news.HostType;
import cn.cnlinfo.news.rx.entity.NeteastVideoSummary;
import cn.cnlinfo.news.rx.method.RetrofitManager;
import cn.cnlinfo.news.rx.rxbus.RxBus;
import cn.cnlinfo.news.ui.callback.HandleRequestCallBack;

/**
 * Created by JP on 2018/3/8 0008.
 */

public class VideoListDataSource implements IAsyncDataSource<List<NeteastVideoSummary>>{
    private int page = 0;
    private int maxPage = Integer.MAX_VALUE;
    private String videoId;
    public VideoListDataSource(String videoId){
        this.videoId = videoId;
    }
    @Override
    public RequestHandle refresh(ResponseSender<List<NeteastVideoSummary>> sender) throws Exception {
        return loadImageList(sender,0);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<NeteastVideoSummary>> sender) throws Exception {
        page = page+1;
        return loadImageList(sender,page);
    }

    private RequestHandle loadImageList(final ResponseSender<List<NeteastVideoSummary>> sender, int page){
        RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO).toLoadVideoListData(new HandleRequestCallBack<List<NeteastVideoSummary>>() {
            @Override
            public void requestDataStart() {

            }

            @Override
            public void requestDataSuccess(List<NeteastVideoSummary> sinaPhotoDetails) {
                    Logger.d(sinaPhotoDetails.toString());
                    sender.sendData(sinaPhotoDetails);
            }

            @Override
            public void requestDataFail(String msg) {
                RxBus.get().post(Constant.ERRORMESSAGE,msg);
            }

            @Override
            public void requestCompleted() {

            }
        },videoId,page);
        return new OkHttpRequestHandler();
    }
    @Override
    public boolean hasMore() {
        return page<maxPage;
    }
}
