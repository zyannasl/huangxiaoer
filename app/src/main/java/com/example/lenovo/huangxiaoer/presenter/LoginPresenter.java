package com.example.lenovo.huangxiaoer.presenter;


import com.example.lenovo.huangxiaoer.bean.LoginBean;
import com.example.lenovo.huangxiaoer.model.LoginModel;
import com.example.lenovo.huangxiaoer.view.interfaces.ILoginView;
import com.example.mvp.mvp.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginModel,ILoginView>{
    public void login(String mobile, String pwd){
        model.login(mobile, pwd, new LoginModel.ILoginModel() {
            @Override
            public void success(LoginBean loginBean) {
                view.loginSuccess(loginBean);
            }
        });
    }
}
