package cn.cnlinfo.ccf.rx.method;

import cn.cnlinfo.ccf.rx.NetOperationTool;
import cn.cnlinfo.ccf.rx.SimpleObservableDecorator;
import cn.cnlinfo.ccf.rx.net_inter.ILoginService;
import cn.cnlinfo.ccf.rx.response.entity.User;
import cn.cnlinfo.ccf.rx.response.tool.ResponseChecker;
import rx.Subscriber;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public class HttpMethod {

    public static HttpMethod getInstance(){
        return  SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder{
        private final static HttpMethod INSTANCE = new HttpMethod();
    }

    /**
     * 登录操作
     * @param subscriber  观察者，也叫订阅者
     * @param baseUrl  login的主机地址
     * @param userName  用户名
     * @param passWord  密码
     */
    public void startLogin(Subscriber<User> subscriber, String baseUrl, String userName, String passWord){
        SimpleObservableDecorator.getObservableByDecorator(NetOperationTool.getRetrofit(baseUrl).create(ILoginService.class).login(userName,passWord)).map(new ResponseChecker<User>()).subscribe(subscriber);
    }
}
