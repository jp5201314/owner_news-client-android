package cn.cnlinfo.news.ui.activity.channel.model;

import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cnlinfo.news.OwnerNewsApplication;
import cn.cnlinfo.news.bean.NewsChannel;
import cn.cnlinfo.news.bean.NewsChannelDao;
import cn.cnlinfo.news.ui.callback.RequestCallback;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by JP on 2018/3/1 0001.
 */

public class INewsChannelInteractorImpl implements INewsChannelInteractor<Map<Boolean,List<NewsChannel>>> {

    @Override
    public Subscription channelDbOperate(final RequestCallback<Map<Boolean, List<NewsChannel>>> callback, final String channelName, final Boolean selectState) {

       return Observable.create(new Observable.OnSubscribe<Map<Boolean, List<NewsChannel>>>() {
           @Override
           public void call(Subscriber<? super Map<Boolean, List<NewsChannel>>> subscriber) {
               NewsChannelDao newsChannelDao = OwnerNewsApplication.getInstance().getDaoSession().getNewsChannelDao();
               if (selectState==null){
                   Logger.d("初始化操作");
                  Map<Boolean,List<NewsChannel>> listMap = new HashMap<>();
                  listMap.put(true,newsChannelDao.queryBuilder().where(NewsChannelDao.Properties.NewsChannelSelect.eq(true)).orderAsc(NewsChannelDao.Properties.NewsChannelIndex).build().list());
                  listMap.put(false,newsChannelDao.queryBuilder().where(NewsChannelDao.Properties.NewsChannelSelect.eq(false)).orderAsc(NewsChannelDao.Properties.NewsChannelIndex).build().list());
                  subscriber.onNext(listMap);
               }else if (selectState==true){//进行增加订阅栏目操作
                Logger.d("增加订阅项目的操作");
                NewsChannel newsChannel = newsChannelDao.queryBuilder().where(NewsChannelDao.Properties.NewsChannelName.eq(channelName)).unique();
                //原来的索引位置
                long originalPos = newsChannel.getNewsChannelIndex();

                //现在的索引位置
                   //long currentPos =



               }else {//进行删除订阅栏目操作

               }
           }
       }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Action0() {
           @Override
           public void call() {
               if (selectState==null){
                   // 只在初始化的时候加载动画
                   callback.beforeRequest();
               }
           }
       }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Map<Boolean, List<NewsChannel>>>() {
           @Override
           public void onCompleted() {
               callback.requestComplete();
           }

           @Override
           public void onError(Throwable e) {
                Logger.d(e.getMessage());
                callback.requestError(e.getMessage());
           }

           @Override
           public void onNext(Map<Boolean, List<NewsChannel>> booleanListMap) {
                callback.requestSuccess(booleanListMap);
           }
       });
    }


    @Override
    public Subscription channelDbSwap(RequestCallback callback, long fromPos, long toPos) {
        return null;
    }
}
