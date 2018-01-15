package cn.cnlinfo.ccf.ui.login;

import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.rx.method.HttpMethod;
import cn.cnlinfo.ccf.rx.response.entity.User;
import rx.Subscriber;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public class LoginPresenter implements LoginContact.Presenter{
    private LoginContact.View view;

    public LoginPresenter(LoginContact.View view) {
        this.view = view;
    }

    @Override
    public void toLogin( String baseUrl, String userName, String passWord) {
        HttpMethod.getInstance().startLogin(new Subscriber<User>() {
            @Override
            public void onCompleted() {
                view.loginSuccess(this,"登录成功");
            }

            @Override
            public void onError(Throwable e) {
                view.loginFail(this,e.getMessage());
            }

            @Override
            public void onNext(User user) {
                UserSharedPreference.getInstance().setJwtToken("1");
                UserSharedPreference.getInstance().setIsFirstLogin(true);
                UserSharedPreference.getInstance().setUser(user);
            }
        }, baseUrl, userName, passWord);
    }
}
