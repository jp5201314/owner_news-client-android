package cn.cnlinfo.news.mvc.datasource;

import com.orhanobut.logger.Logger;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;

import java.util.List;

import cn.cnlinfo.news.Constant;
import cn.cnlinfo.news.HostType;
import cn.cnlinfo.news.rx.entity.SinaPhotoDetail;
import cn.cnlinfo.news.rx.method.RetrofitManager;
import cn.cnlinfo.news.rx.rxbus.RxBus;
import cn.cnlinfo.news.ui.callback.HandleRequestCallBack;

/**
 * Created by JP on 2018/3/8 0008.
 */

public class ImageListDataSource implements IAsyncDataSource<List<SinaPhotoDetail>>{
    private int page = 0;
    private int maxPage = Integer.MAX_VALUE;
    @Override
    public RequestHandle refresh(ResponseSender<List<SinaPhotoDetail>> sender) throws Exception {
        return loadImageList(sender,0);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<SinaPhotoDetail>> sender) throws Exception {
        page = page+1;
        return loadImageList(sender,page);
    }

    private RequestHandle loadImageList(final ResponseSender<List<SinaPhotoDetail>> sender, int page){
        RetrofitManager.getInstance(HostType.SINA_IMAGE).toLoadImageListData(new HandleRequestCallBack<List<SinaPhotoDetail>>() {
            @Override
            public void requestDataStart() {

            }

            @Override
            public void requestDataSuccess(List<SinaPhotoDetail> sinaPhotoDetails) {
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
        },page);
        return new OkHttpRequestHandler();
    }
    @Override
    public boolean hasMore() {
        return page<maxPage;
    }
}
