package com.example.lenovo.huangxiaoer.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.huangxiaoer.R;
import com.example.lenovo.huangxiaoer.bean.LoginBean;
import com.example.lenovo.huangxiaoer.model.LoginModel;
import com.example.lenovo.huangxiaoer.presenter.LoginPresenter;
import com.example.lenovo.huangxiaoer.utils.Common;
import com.example.lenovo.huangxiaoer.view.interfaces.ILoginView;
import com.example.mvp.BaseActivity;
import com.example.mvp.mvp.BaseModel;
import com.mob.MobSDK;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView,View.OnClickListener{


    private EditText iphone;
    private EditText sms;
    private Button sms_btn;
    private Button login_btn;
    private TextView login_tv;
    private ImageView qq;
    private ImageView wx;
    private SharedPreferences sp;
    String APPKEY = "26cc1f615172f";
    String APPSECRET = "9c574cc219f443311b906807b4a3bd71";
    int i = 30;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                sms_btn.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                sms_btn.setText("获取验证码");
                sms_btn.setClickable(true);
                i = 30;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        Toast.makeText(getApplicationContext(), "提交验证码成功",
                                Toast.LENGTH_SHORT).show();

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "正在获取验证码",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this,"验证码不正确",Toast.LENGTH_SHORT).show();
                        ((Throwable) data).printStackTrace();
                    }
                }
            }
        }
    };
    @Override
    protected void initData() {

    }

    @Override
    protected BaseModel initModel() {
        return new LoginModel();
    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initView() {
        iphone = findViewById(R.id.login_iphone);
        sms = findViewById(R.id.login_sms);
        sms_btn = findViewById(R.id.sms_btn);
        sms_btn.setOnClickListener(this);
        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
        login_tv = findViewById(R.id.login_tv);
        login_tv.setOnClickListener(this);
        qq = findViewById(R.id.login_qq);
        wx = findViewById(R.id.login_wx);

// 启动短信验证sdk
        MobSDK.init(this, APPKEY, APPSECRET);
        EventHandler eventHandler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        String phoneNums = iphone.getText().toString();  //取出咱们输入的手机号
        switch (v.getId()){
            case R.id.login_tv:
                Intent intent = new Intent(LoginActivity.this, IphonLogin.class);
                startActivity(intent);

                break;
            case R.id.login_btn:
                SMSSDK.submitVerificationCode("86", phoneNums, sms
                        .getText().toString());
                presenter.login(phoneNums,"123456");
                Intent intent1 = new Intent(LoginActivity.this, DianCanActivity.class);
                startActivity(intent1);
                break;
            case R.id.sms_btn:
                // 1. 判断手机号是不是11位并且看格式是否合理
                if (!Common.isMobileNO(phoneNums)) {
                    return;
                } // 2. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86", phoneNums);

                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                sms_btn.setClickable(false);
                sms_btn.setText("重新发送(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();
                break;
        }
    }

    @Override
    public void loginSuccess(LoginBean loginBean) {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        //记录登录状态
        //获取sp对象
        sp = getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("mobile", loginBean.getData().getMobile());
        edit.putString("token", loginBean.getData().getToken());
        edit.putString("uid", loginBean.getData().getUid() + "");
        edit.putBoolean("have", true);
        edit.commit();
        Log.e("cccc",sp.getBoolean("have",false)+"");

    }

    @Override
    public void loginError(String mag) {
        Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mobileError() {

    }

    @Override
    public void pwdError() {

    }
}
