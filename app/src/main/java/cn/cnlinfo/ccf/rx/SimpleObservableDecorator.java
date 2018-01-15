package cn.cnlinfo.ccf.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public class SimpleObservableDecorator {

    public static <T> Observable<T>getObservableByDecorator(Observable <T> observable){
        return observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io());
    }
}
