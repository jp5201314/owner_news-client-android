package cn.cnlinfo.news.ui.callback;

import com.orhanobut.logger.Logger;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cn.cnlinfo.news.OwnerNewsApplication;
import cn.cnlinfo.news.rx.response.exception.ApiException;
import cn.cnlinfo.news.utils.NetUtil;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Administrator on 2018/1/17 0017.
 * 把回调各个方法统一处理，并且这里对返回错误做了统一处理
 */

public abstract class HandleRequestCallBack<T> extends Subscriber<T>{


    @Override
    public void onStart() {
        super.onStart();
        requestDataStart();
    }

    @Override
    public void onCompleted() {
        Logger.d("completed");
        requestCompleted();
    }

    @Override
    public void onError(Throwable e) {
        String errorMsg = e.getMessage();
        if (e instanceof HttpException) {
            switch (((HttpException) e).code()) {
                case 403:
                    errorMsg = "没有权限访问此链接！";
                    break;
                case 504:
                    if (!NetUtil.isConnected(OwnerNewsApplication.getContext())) {
                        errorMsg = "没有联网哦！";
                    } else {
                        errorMsg = "网络连接超时！";
                    }
                    break;
                default:
                    errorMsg = ((HttpException) e).message();
                    break;
            }
        } else if (e instanceof UnknownHostException) {
            errorMsg = "不知名主机！";
        } else if (e instanceof SocketTimeoutException) {
            errorMsg = "网络连接超时！";
        }else if (e instanceof ApiException){
            errorMsg = ((ApiException) e).getErrorMsg();
        }
        requestDataFail(errorMsg);
    }

    @Override
    public void onNext(T t) {
        requestDataSuccess(t);
        requestCompleted();
    }

    public abstract void requestDataStart();

    public abstract void requestDataSuccess(T t);

    public abstract void requestDataFail(String msg);

    public abstract void requestCompleted();
}
