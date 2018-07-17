package com.example.lenovo.huangxiaoer.presenter;


import com.example.lenovo.huangxiaoer.bean.RegBean;
import com.example.lenovo.huangxiaoer.model.ZhuceModel;
import com.example.lenovo.huangxiaoer.view.interfaces.IZhuceView;
import com.example.mvp.mvp.BasePresenter;

public class ZhucePresenter extends BasePresenter<ZhuceModel,IZhuceView> {
    public void zhuce(String mobile,String password){
        model.zhuce(mobile, password, new ZhuceModel.IZhuceModel() {
            @Override
            public void success(RegBean regBean) {
                view.success(regBean);
            }
        });
    }
}
