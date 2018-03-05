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
                   initSelectState(subscriber, newsChannelDao);
               }else if (selectState==true){//进行增加订阅栏目操作
                Logger.d("增加订阅项目的操作  "+ channelName + ";" + selectState);
                final NewsChannel newsChannel = newsChannelDao.queryBuilder().where(NewsChannelDao.Properties.NewsChannelName.eq(channelName)).unique();
                //原来的索引位置
                long originalPos = newsChannel.getNewsChannelIndex();

                //现在应该处的索引位置
                long currentPos = newsChannelDao.queryBuilder().where(NewsChannelDao.Properties.NewsChannelSelect.eq(true)).count();

                   // gt大于   lt小于   ge大于等于   le 小于等于

                   // 找到比它位置小的没被选中的
                List<NewsChannel> ltOriginalPosNewsChannelList = newsChannelDao.queryBuilder().
                        where(NewsChannelDao.Properties.NewsChannelSelect.eq(false),
                                NewsChannelDao.Properties.NewsChannelIndex.lt(originalPos)).build().list();
                   for (NewsChannel channel : ltOriginalPosNewsChannelList) {
                       //当前的索引向后移动一位
                       channel.setNewsChannelIndex(channel.getNewsChannelIndex()+1);
                       newsChannelDao.update(channel);
                   }
                   //设置新增进来的新闻栏目所处的位置和订阅的状态，并且更新数据库
                   newsChannel.setNewsChannelIndex(currentPos);
                   newsChannel.setNewsChannelSelect(true);
                   newsChannelDao.update(newsChannel);

               }else if(selectState==false){//进行删除订阅栏目操作
                    Logger.d("进行删除订阅新闻项目的操作  "+ channelName + ";" + selectState);
                    final NewsChannel newsChannel = newsChannelDao.queryBuilder().
                            where(NewsChannelDao.Properties.NewsChannelName.eq(channelName)).unique();
                    //原来的位置
                   final long originalPos = newsChannel.getNewsChannelIndex();
                   //现在应该处的位置
                   final long toPos = newsChannelDao.loadAll().size()-1;

                   final List<NewsChannel> gtOriginalPosNewsChannelList = newsChannelDao.queryBuilder().
                           where(NewsChannelDao.Properties.NewsChannelSelect
                                   .eq(true),NewsChannelDao.Properties.NewsChannelIndex.gt(originalPos)).build().list();
                   for (NewsChannel channel : gtOriginalPosNewsChannelList) {
                       //所有的比删除项目索引大的项目的索引向前进一位
                       channel.setNewsChannelIndex(channel.getNewsChannelIndex()-1);
                       newsChannelDao.update(channel);
                   }

                   // 未选中的
                   final List<NewsChannel> unSelectChannels = newsChannelDao.queryBuilder()
                           .where(NewsChannelDao.Properties.NewsChannelSelect
                                   .eq(false)).build().list();
                   // 位置全部减1
                   for (NewsChannel s : unSelectChannels) {
                       s.setNewsChannelIndex(s.getNewsChannelIndex() - 1);
                       newsChannelDao.update(s);
                   }


                   //设置已订阅中删除的项目放到末尾，状态变为未订阅,并且更新数据库
                   newsChannel.setNewsChannelIndex(toPos);
                   newsChannel.setNewsChannelSelect(false);
                   newsChannelDao.update(newsChannel);
               }
               for (NewsChannel channel : newsChannelDao.loadAll()) {
                   if (channel.getNewsChannelSelect()){
                       Logger.i(channel.getNewsChannelIndex()+":"+channel.getNewsChannelName());
                   }
               }
               // 只做数据库操作，不关心结果直接调用完成
               subscriber.onCompleted();
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
                Logger.e(e.getMessage()+"\n"+e.getLocalizedMessage());
                callback.requestError(e.getMessage());
           }

           @Override
           public void onNext(Map<Boolean, List<NewsChannel>> booleanListMap) {
                callback.requestSuccess(booleanListMap);
           }
       });
    }

    public void initSelectState(Subscriber<? super Map<Boolean, List<NewsChannel>>> subscriber, NewsChannelDao newsChannelDao) {
        Map<Boolean,List<NewsChannel>> listMap = new HashMap<>();
        listMap.put(true,newsChannelDao.queryBuilder().where(NewsChannelDao.Properties.NewsChannelSelect.eq(true)).orderAsc(NewsChannelDao.Properties.NewsChannelIndex).build().list());
        listMap.put(false,newsChannelDao.queryBuilder().where(NewsChannelDao.Properties.NewsChannelSelect.eq(false)).orderAsc(NewsChannelDao.Properties.NewsChannelIndex).build().list());
        subscriber.onNext(listMap);
    }


    @Override
    public Subscription channelDbSwap(RequestCallback callback, final long fromPos, final long toPos) {
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                final NewsChannelDao dao = OwnerNewsApplication.getInstance()
                        .getDaoSession().getNewsChannelDao();

                // 交换前此位置对应的频道
                final NewsChannel fromChannel = dao.queryBuilder()
                        .where(NewsChannelDao.Properties.NewsChannelIndex.eq(fromPos))
                        .unique();

                final long fromPosition = fromChannel.getNewsChannelIndex();

                // 交换前此位置将要去的对应的频道
                final NewsChannel toChannel = dao.queryBuilder()
                        .where(NewsChannelDao.Properties.NewsChannelIndex.eq(toPos)).unique();

                final long toPosition = toChannel.getNewsChannelIndex();

                if (Math.abs(fromPosition - toPosition) == 1) {
                    // 相邻的交换，只需要调整两个位置即可
                    Logger.e("相邻的交换，只需要调整两个位置即可");
                    fromChannel.setNewsChannelIndex(toPosition);
                    toChannel.setNewsChannelIndex(fromPosition);
                    dao.update(fromChannel);
                    dao.update(toChannel);
                } else if (fromPosition - toPosition > 0) {
                    //  开始的位置大于要去的位置,往前移
                    Logger.e("开始的位置大于要去的位置,往前移");
                    final List<NewsChannel> moveChannels = dao.queryBuilder()
                            .where(NewsChannelDao.Properties.NewsChannelIndex
                                    .between(toPosition, fromPosition - 1)).build().list();
                    // 全部加一
                    for (NewsChannel c : moveChannels) {
                        c.setNewsChannelIndex(c.getNewsChannelIndex() + 1);
                        dao.update(c);
                    }
                    fromChannel.setNewsChannelIndex(toPosition);
                    dao.update(fromChannel);
                } else if (fromPosition - toPosition < 0) {
                    //  开始的位置小于要去的位置,往后移
                    Logger.e("开始的位置小于要去的位置,往后移: " + toPosition + ";" + fromPosition);
                    final List<NewsChannel> moveChannels = dao.queryBuilder()
                            .where(NewsChannelDao.Properties.NewsChannelIndex
                                    .between(fromPosition + 1, toPosition)).build().list();
                    Logger.e(moveChannels.size()+"");
                    // 全部减一
                    for (NewsChannel c : moveChannels) {
                        c.setNewsChannelIndex(c.getNewsChannelIndex() - 1);
                        dao.update(c);
                    }
                    fromChannel.setNewsChannelIndex(toPosition);
                    dao.update(fromChannel);
                }

                subscriber.onCompleted();

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }
}
