package com.example.lenovo.huangxiaoer.view;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.lenovo.huangxiaoer.R;
import com.example.mvp.BaseActivity;
import com.example.mvp.mvp.BaseModel;
import com.example.mvp.mvp.BasePresenter;

public class DianCanActivity extends BaseActivity implements View.OnClickListener{


    private ImageView wode;
    private ImageView yueyue;

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
        wode = findViewById(R.id.wode);
        wode.setOnClickListener(this);
        yueyue = findViewById(R.id.yuyue);
        yueyue.setOnClickListener(this);

    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_dian_can;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wode:
                Intent intent = new Intent(DianCanActivity.this, WodeActivity.class);
                startActivity(intent);
                break;
            case R.id.yuyue:
                Intent intent1 = new Intent(DianCanActivity.this, YuyueActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
