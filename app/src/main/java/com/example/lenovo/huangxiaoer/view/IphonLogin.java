package com.example.lenovo.huangxiaoer.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.huangxiaoer.R;
import com.example.mvp.BaseActivity;
import com.example.mvp.mvp.BaseModel;
import com.example.mvp.mvp.BasePresenter;

public class IphonLogin extends BaseActivity implements View.OnClickListener{
    private EditText iphone;
    private EditText sms;
    private ImageView pass;
    private Button login_btn;
    private TextView login_tv;
    private ImageView qq;
    private ImageView wx;

    @Override
    protected void initData() {

    }

    @Override
    protected BaseModel initModel() {
        return null;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        iphone = findViewById(R.id.ilogin_iphone);
        sms = findViewById(R.id.ilogin_sms);
        pass = findViewById(R.id.ilogin_pass);
        login_btn = findViewById(R.id.ilogin_btn);
        login_btn.setOnClickListener(this);
        login_tv = findViewById(R.id.ilogin_tv);
        login_tv.setOnClickListener(this);
        qq = findViewById(R.id.ilogin_qq);
        wx = findViewById(R.id.ilogin_wx);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_iphon_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ilogin_tv:
                Intent intent = new Intent(IphonLogin.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.ilogin_btn:
                Intent intent1 = new Intent(IphonLogin.this, DianCanActivity.class);
                startActivity(intent1);
        }

    }
}
