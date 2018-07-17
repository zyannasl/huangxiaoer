package com.example.lenovo.huangxiaoer.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.lenovo.huangxiaoer.R;
import com.example.mvp.BaseActivity;
import com.example.mvp.mvp.BaseModel;
import com.example.mvp.mvp.BasePresenter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

public class WelcomeActivity extends BaseActivity {


    private ImageView welcome_iv;
    private SimpleDraweeView welcome_sdv;

    @Override
    protected void initData() {
        DraweeController mDraweeController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                //设置uri,加载本地的gif资源
                .setUri(Uri.parse("res://"+getPackageName()+"/"+R.mipmap.sp))//设置uri
                .build();
        welcome_sdv.setController(mDraweeController);
        welcome_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
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
        welcome_iv = findViewById(R.id.welcome_iv);
        welcome_sdv=findViewById(R.id.welcome_sdv);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_welcome;
    }
}
