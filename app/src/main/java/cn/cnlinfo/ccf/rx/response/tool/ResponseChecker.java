package cn.cnlinfo.ccf.rx.response.tool;

import cn.cnlinfo.ccf.rx.response.entity.DataEntity;
import cn.cnlinfo.ccf.rx.response.entity.HttpResult;
import cn.cnlinfo.ccf.rx.response.exception.ApiException;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public class ResponseChecker<T> implements Func1<HttpResult<DataEntity<T>>,T>{

    @Override
    public T call(HttpResult<DataEntity<T>> dataEntityHttpResult) {
        if (dataEntityHttpResult.getMessageID() != 0) {
            try {
                throw new ApiException(dataEntityHttpResult.getMessageID());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataEntityHttpResult.getData().getUserinfo();
    }
}
