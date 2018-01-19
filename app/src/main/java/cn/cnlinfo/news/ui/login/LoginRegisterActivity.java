package cn.cnlinfo.news.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.ui.activity.BaseActivity;
import cn.cnlinfo.news.ui.activity.MainPageActivity;
import cn.cnlinfo.news.utils.ObtainVerificationCode;
import cn.cnlinfo.news.utils.ToastTool;
import rx.Subscription;

/**
 * Created by JP on 2017/10/11 0011.
 */

public class LoginRegisterActivity extends BaseActivity implements LoginContact.View{
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.tv_get_verification_code)
    TextView tvGetVerificationCode;
    private Unbinder unbinder;
    private LoginPresenter loginPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        unbinder = ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVerificationCode();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    //登录
    public void toLogin(View view) {
        startLogin();
    }

    //注册
    public void toRegister(View view) {
        //startActivity(new Intent(this, RegisterActivity.class));
    }

    //获取验证码
    public void gainVerificationCode(View view) {
        setVerificationCode();
    }
    //设置验证码
    private void setVerificationCode() {
        tvGetVerificationCode.setText(ObtainVerificationCode.createVerificationCode());
    }
    //忘记密码
    public void toForgetPass(View view) {
        //startActivity(new Intent(this, ForgetPasswordActivity.class));
    }

    /**
     * 开始登陆
     */
    private void startLogin() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String verificationCode = etVerificationCode.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            ToastTool.showShort(this,"用户名或密码不能为空");
        } else {
            if (verificationCode != null && verificationCode.toLowerCase().equals(tvGetVerificationCode.getText().toString().trim().toLowerCase())) {
               loginPresenter.toLogin(1,username,password);
            } else {
                ToastTool.showShort(this,"验证码不正确，请重新输入");
            }
        }
    }

    /**
     * 登录成功
     * @param subscription
     */
    @Override
    public void loginSuccess(Subscription subscription) {
        if (subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        Intent intent = new Intent(LoginRegisterActivity.this, MainPageActivity.class);
        startActivity(intent);
        LoginRegisterActivity.this.finish();
    }

    /**
     * 登录失败
     * @param subscription
     */
    @Override
    public void loginFail(Subscription subscription) {
        if (subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
    //显示登录进度
    @Override
    public void showProgress() {
        showWaitingDialog(true);
    }
    //关闭进度
    @Override
    public void hideProgress() {
        showWaitingDialog(false);
    }
    //显示进度
    @Override
    public void toast(String msg) {
        ToastTool.showShort(this,msg);
    }
}
