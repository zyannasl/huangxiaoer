package com.example.lenovo.huangxiaoer.presenter;

import android.net.Uri;

import com.example.lenovo.huangxiaoer.bean.UploadBean;
import com.example.lenovo.huangxiaoer.model.LoginImagerModel;
import com.example.lenovo.huangxiaoer.view.interfaces.ILoginImagerView;


public class LoginImagerPresenter {
    private ILoginImagerView iLoginImagerView;
    private LoginImagerModel loginModel;

    public LoginImagerPresenter(ILoginImagerView iLoginImagerView) {
        this.iLoginImagerView = iLoginImagerView;
        loginModel = new LoginImagerModel();
    }

    public void loginImager(Uri imageUri) {
        loginModel.loginImager(imageUri,new LoginImagerModel.ILoginImagerModel() {
            @Override
            public void success(UploadBean uploadBean) {
                iLoginImagerView.loginSuccess(uploadBean);
            }
        });
    }
}
