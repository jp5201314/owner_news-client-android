package cn.cnlinfo.ccf.rx.response.entity;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public class DataEntity<T> {
   private T userinfo;

    public T getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(T userinfo) {
        this.userinfo = userinfo;
    }
}
