package cn.cnlinfo.ccf.ui.login;

import cn.cnlinfo.ccf.ui.base.IBasePresenter;
import cn.cnlinfo.ccf.ui.base.IBaseView;
import rx.Subscription;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public interface LoginContact {
    interface View extends IBaseView{
        void loginSuccess(Subscription subscription,String success);
        void loginFail(Subscription subscription,String fail);
    }

    interface Presenter extends IBasePresenter {
        void toLogin( String baseUrl, String userName, String passWord);
    }
}
