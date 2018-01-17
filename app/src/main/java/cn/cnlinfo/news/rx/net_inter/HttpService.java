package cn.cnlinfo.news.rx.net_inter;

import cn.cnlinfo.news.API;
import cn.cnlinfo.news.rx.response.entity.DataEntity;
import cn.cnlinfo.news.rx.response.entity.HttpResult;
import cn.cnlinfo.news.rx.response.entity.User;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public interface HttpService {
    @FormUrlEncoded
    @POST(API.CCFLOGIN)
    Observable<HttpResult<DataEntity<User>>> login(@Field("username") String username, @Field("password") String password);


}
