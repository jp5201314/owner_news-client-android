package cn.cnlinfo.news.ui.fragment.news.model;


import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.query.Query;

import java.util.Arrays;
import java.util.List;

import cn.cnlinfo.news.API;
import cn.cnlinfo.news.OwnerNewsApplication;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.bean.NewsChannel;
import cn.cnlinfo.news.bean.NewsChannelDao;
import cn.cnlinfo.news.ui.callback.RequestCallback;
import cn.cnlinfo.news.utils.SpUtil;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * ClassName: INewsInteractorImpl<p>
 * Author: oubowu<p>
 * Fuction: 新闻Model层接口实现,数据库操作，第一次初始化频道，之后查询选中的频道<p>
 * CreateDate: 2016/2/20 15:05<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
public class INewsInteractorImpl implements INewsInteractor<List<NewsChannel>> {

    @Override
    public Subscription operateChannelDb(final RequestCallback<List<NewsChannel>> callback) {

        return Observable.create(new Observable.OnSubscribe<List<NewsChannel>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannel>> subscriber) {
                final NewsChannelDao dao = OwnerNewsApplication.getInstance().getDaoSession().getNewsChannelDao();
                if (!SpUtil.readBoolean("initDb")) {

                    List<String> channelName = Arrays.asList(OwnerNewsApplication.getContext().getResources()
                            .getStringArray(R.array.news_channel));

                    List<String> channelId = Arrays.asList(OwnerNewsApplication.getContext().getResources()
                            .getStringArray(R.array.news_channel_id));

                    for (int i = 0; i < channelName.size(); i++) {
                        NewsChannel table = new NewsChannel(channelName.get(i),
                                channelId.get(i), API.getType(channelId.get(i)), i <= 2,
                                // 前三是固定死的，默认选中状态
                                i, i <= 2);
                        dao.insert(table);
                    }
                    SpUtil.writeBoolean("initDb", true);
                    Logger.e("数据库初始化完毕！");
                }
                //筛选已选择的频道，按升序排列
                final Query<NewsChannel> build = dao.queryBuilder()
                        .where(NewsChannelDao.Properties.NewsChannelSelect.eq(true))
                        .orderAsc(NewsChannelDao.Properties.NewsChannelIndex).build();
                subscriber.onNext(build.list());
                subscriber.onCompleted();

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callback.beforeRequest();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<NewsChannel>>() {
                    @Override
                    public void onCompleted() {
                        callback.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getLocalizedMessage() + "\n" + e);
                        callback.requestError(e.getLocalizedMessage() + "\n" + e);
                    }

                    @Override
                    public void onNext(List<NewsChannel> newsChannels) {
                        callback.requestSuccess(newsChannels);
                    }
                });
    }
}
