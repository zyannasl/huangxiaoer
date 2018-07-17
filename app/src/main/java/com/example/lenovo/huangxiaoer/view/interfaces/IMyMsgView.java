package com.example.lenovo.huangxiaoer.view.interfaces;


import com.example.lenovo.huangxiaoer.bean.UserMsgBean;
import com.example.mvp.mvp.IBaseView;

public interface IMyMsgView extends IBaseView {
    //获取信息
    void userMsg(UserMsgBean userMsgBean);

}
