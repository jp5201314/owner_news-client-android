package cn.cnlinfo.news.ui.fragment.news;

import java.util.List;

import cn.cnlinfo.news.bean.NewsChannel;
import cn.cnlinfo.news.ui.callback.RequestCallback;
import cn.cnlinfo.news.ui.fragment.news.model.INewsInteractor;
import cn.cnlinfo.news.ui.fragment.news.model.INewsInteractorImpl;

/**
 * Created by JP on 2018/2/28 0028.
 */

public class NewsPresenter implements NewsContact.Presenter,RequestCallback<List<NewsChannel>> {

    private NewsContact.View view = null;
    private INewsInteractor<List<NewsChannel>> iNewsInteractor;
    public NewsPresenter(NewsContact.View view){
        this.view = view;
        iNewsInteractor = new INewsInteractorImpl();
        iNewsInteractor.operateChannelDb(this);
    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void requestComplete() {

    }

    @Override
    public void requestSuccess(List<NewsChannel> data) {
        view.initViewPager(data);
    }

}
