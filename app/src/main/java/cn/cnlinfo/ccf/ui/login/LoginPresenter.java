package cn.cnlinfo.ccf.ui.login;

import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.rx.method.RetrofitManager;
import cn.cnlinfo.ccf.rx.response.entity.User;
import cn.cnlinfo.ccf.ui.callback.HandleRequestCallBack;
import rx.Subscription;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public class LoginPresenter implements LoginContact.Presenter{
    private LoginContact.View view;

    public LoginPresenter(LoginContact.View view) {
        this.view = view;
    }

    @Override
    public Subscription toLogin(int type, String userName, String passWord) {
        return RetrofitManager.getInstance(type).startLogin(new HandleRequestCallBack<User>() {

            //开始发起数据请求
            @Override
            public void requestDataStart() {
                view.showProgress();
            }

            //成功请求数据
            @Override
            public void requestDataSuccess(User user) {
                UserSharedPreference.getInstance().setJwtToken("1");
                UserSharedPreference.getInstance().setIsFirstLogin(true);
                UserSharedPreference.getInstance().setUser(user);
            }

            //失败请求数据
            @Override
            public void requestDataFail(String msg) {
                view.hideProgress();
                view.loginFail(this);
                view.toast(msg);
            }
            //完成请求数据
            @Override
            public void requestCompleted() {
                view.hideProgress();
                view.loginSuccess(this);
                view.toast("登录成功");
            }
        }, userName, passWord);
    }
}
