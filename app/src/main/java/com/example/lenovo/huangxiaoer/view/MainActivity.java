package com.example.lenovo.huangxiaoer.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.lenovo.huangxiaoer.R;
import com.example.lenovo.huangxiaoer.adapter.ViewpagerAdapter;
import com.example.mvp.BaseActivity;
import com.example.mvp.mvp.BaseModel;
import com.example.mvp.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private List<View> list;
    private boolean isLastPage = false;
    private boolean isDragPage = false;
    private boolean canJumpPage = true;
    private SharedPreferences sp;


    @Override
    protected void initData() {
        sp = getSharedPreferences("User", Context.MODE_PRIVATE);
        boolean have = sp.getBoolean("have", false);
        Log.e("xxxx",have+"");
        if (have == true) {
            Intent intent = new Intent(MainActivity.this, DianCanActivity.class);
            startActivity(intent);
        } else {
            ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(list, this);
            viewPager.setAdapter(viewpagerAdapter);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (isLastPage && isDragPage && positionOffsetPixels == 0) {   //当前页是最后一页，并且是拖动状态，并且像素偏移量为0
                        if (canJumpPage) {
                            canJumpPage = false;
                            JumpToNext();
                        }
                    }
                }

                @Override
                public void onPageSelected(int position) {
                    isLastPage = position == list.size() - 1;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    isDragPage = state == 1;
                }
            });
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

    private void JumpToNext() {
        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.viewPager);
        list = new ArrayList<>();
        LayoutInflater from = getLayoutInflater().from(MainActivity.this);
        View view1 = from.inflate(R.layout.activity_viewpager1, null);
        View view2 = from.inflate(R.layout.activity_viewpager2, null);
        View view3 = from.inflate(R.layout.activity_viewpager3, null);
        list.add(view1);
        list.add(view2);
        list.add(view3);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_main;
    }
}
