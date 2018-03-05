package cn.cnlinfo.news.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JP on 2018/1/9 0009.
 */

public class BaseObservableTransfer<T> implements Observable.Transformer<T,T> {


    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io());
    }
}
