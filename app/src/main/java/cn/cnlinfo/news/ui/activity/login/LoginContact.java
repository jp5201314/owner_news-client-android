package cn.cnlinfo.news.ui.activity.login;

import cn.cnlinfo.news.ui.base.IBasePresenter;
import cn.cnlinfo.news.ui.base.IBaseView;
import rx.Subscription;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public interface LoginContact {
    interface View extends IBaseView {
        void loginSuccess(Subscription subscription);
        void loginFail(Subscription subscription);
    }

    interface Presenter extends IBasePresenter {
        Subscription toLogin(int type, String userName, String passWord);
    }
}
