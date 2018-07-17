package com.example.lenovo.huangxiaoer.presenter;


import com.example.lenovo.huangxiaoer.bean.UserMsgBean;
import com.example.lenovo.huangxiaoer.model.MyMsgModel;
import com.example.lenovo.huangxiaoer.view.interfaces.IMyMsgView;

public class MyMsgPresenter  {
     private IMyMsgView iMyMsgView;
     private MyMsgModel myMsgModel;

     public MyMsgPresenter(IMyMsgView iMyMsgView) {
          this.iMyMsgView = iMyMsgView;
          myMsgModel=new MyMsgModel();
     }
     public void userMsg(String uid){
          myMsgModel.myMsg(uid, new MyMsgModel.IMyMsgModel() {
               @Override
               public void success(UserMsgBean userMsgBean) {
                    iMyMsgView.userMsg(userMsgBean);
               }
          });
     }
}
