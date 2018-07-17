package com.example.lenovo.huangxiaoer.view;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.huangxiaoer.R;
import com.example.lenovo.huangxiaoer.utils.DataCleanManager;
import com.example.mvp.BaseActivity;
import com.example.mvp.mvp.BaseModel;
import com.example.mvp.mvp.BasePresenter;

import java.io.File;

public class SettingActivity extends BaseActivity implements View.OnClickListener{


    private RelativeLayout del_tu;
    private TextView del_num;

    @Override
    protected void initData() {
        File file =new File(this.getCacheDir().getPath());
        try {
            del_num.setText(DataCleanManager.getCacheSize(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        del_tu = findViewById(R.id.del_tu);
        del_tu.setOnClickListener(this);
        del_num = findViewById(R.id.del_num);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.del_tu:
                DataCleanManager.cleanInternalCache(SettingActivity.this);
                DataCleanManager.cleanDatabases(SettingActivity.this);
                DataCleanManager.cleanSharedPreference(SettingActivity.this);
                Toast.makeText(this, "清除成功", Toast.LENGTH_SHORT).show();
                del_num.setText("0.0MB");
                break;

            default:
                break;

        }
    }


}
