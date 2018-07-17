package com.example.lenovo.huangxiaoer.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.huangxiaoer.R;
import com.example.lenovo.huangxiaoer.bean.UploadBean;
import com.example.lenovo.huangxiaoer.bean.UserMsgBean;
import com.example.lenovo.huangxiaoer.presenter.LoginImagerPresenter;
import com.example.lenovo.huangxiaoer.presenter.MyMsgPresenter;
import com.example.lenovo.huangxiaoer.view.interfaces.ILoginImagerView;
import com.example.lenovo.huangxiaoer.view.interfaces.IMyMsgView;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.File;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WodeActivity extends TakePhotoActivity implements View.OnClickListener,IMyMsgView,ILoginImagerView {


    private ImageView ziliao;
    private RelativeLayout shezhi;
    private ImageView tou;
    private PopupWindow mPopWindow;
    private TakePhoto takePhoto;
    private Uri imageUri;
    private CropOptions cropOptions;
    private MyMsgPresenter myMsgPresenter;
    private TextView tel;
    private LoginImagerPresenter loginImagerPresenter;
    private ImageView yuyue;

    //获取照片的输入保存地址
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode);
        initData();
        initView();
        //申请权限
        initPermission();
    }

    protected void initData() {

        myMsgPresenter = new MyMsgPresenter(this);
        loginImagerPresenter = new LoginImagerPresenter(this);
        //获取uid pid token
        SharedPreferences sp = getSharedPreferences("User", Context.MODE_PRIVATE);
        String uid = sp.getString("uid", "");
        Log.e("xxxxx",uid);
        myMsgPresenter.userMsg(uid);

        //获取takePhoto实例
        takePhoto=getTakePhoto();
        //设置裁剪参数
        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
    }


    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        Toast.makeText(WodeActivity.this, "Error:" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String iconPath = result.getImage().getOriginalPath();
        //Toast显示图片路径
        Toast.makeText(this, "imagePath:" + iconPath, Toast.LENGTH_SHORT).show();
        //Google Glide库 用于加载图片资源
        Glide.with(this).load(iconPath).into(tou);
    }

    private void initPermission() {
        AndPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .send();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }

    //权限申请回调接口
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            if(requestCode == 100) {
                // TODO 相应代码。
                //do nothing
            }
        }
        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(WodeActivity.this, deniedPermissions)) {

                // 用自定义的提示语
                AndPermission.defaultSettingDialog(WodeActivity.this, 103)
                        .setTitle("权限申请失败")
                        .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                        .setPositiveButton("好，去设置")
                        .show();
            }
        }
    };

    protected void initView() {
        ziliao = findViewById(R.id.ziliao);
        ziliao.setOnClickListener(this);
        shezhi = findViewById(R.id.shezhi);
        shezhi.setOnClickListener(this);
        tou = findViewById(R.id.tou);
        tou.setOnClickListener(this);
        tel = findViewById(R.id.tel);
        yuyue = findViewById(R.id.wode_yuyue);
        yuyue.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ziliao:
              //  Intent intent = new Intent(WodeActivity.this, ZiliaoActivity.class);
               // startActivity(intent);
                break;
            case R.id.shezhi:
                Intent intent = new Intent(WodeActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.tou:
                showPopupVindow();
                break;
            case R.id.xiangji:
                imageUri = getImageCropUri();
                //拍照并裁剪
                takePhoto.onPickFromCaptureWithCrop(imageUri, cropOptions);
                mPopWindow.dismiss();
                loginImagerPresenter.loginImager(imageUri);
                break;
            case R.id.xiangce:
                imageUri = getImageCropUri();
                //从相册中选取图片并裁剪
                takePhoto.onPickFromGalleryWithCrop(imageUri, cropOptions);
                mPopWindow.dismiss();
                loginImagerPresenter.loginImager(imageUri);
                break;
            case R.id.quxiao:
                mPopWindow.dismiss();
                break;
            case R.id.wode_yuyue:
                Intent intent1 = new Intent(WodeActivity.this, YuyueActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void showPopupVindow() {
        //设置contentView
        View contentView = LayoutInflater.from(WodeActivity.this).inflate(R.layout.item_popupwindow, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //设置各个控件的点击响应
        Button xiangji = contentView.findViewById(R.id.xiangji);
        Button xiangce = contentView.findViewById(R.id.xiangce);
        Button quxiao = contentView.findViewById(R.id.quxiao);
        xiangji.setOnClickListener(this);
        xiangce.setOnClickListener(this);
        quxiao.setOnClickListener(this);
        //显示PopupWindow
        View rootview = LayoutInflater.from(WodeActivity.this).inflate(R.layout.activity_main, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void userMsg(UserMsgBean userMsgBean) {
        if (userMsgBean.getData().getIcon() == null) {
            Glide.with(this).load(R.mipmap.tou).into(tou);
        } else {
            Glide.with(this).load(userMsgBean.getData().getIcon()).into(tou);
        }

        tel.setText(userMsgBean.getData().getMobile());

    }

    @Override
    public void loginSuccess(UploadBean uploadBean) {
        Toast.makeText(WodeActivity.this, uploadBean.getMsg(), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < 100; i++) {
            Async async = new Async();
            async.executeOnExecutor(new ThreadPoolExecutor(5, 1000, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(10)), i + "");

        }

    }

    class Async extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
//            publishProgress(100);
            System.out.println("params:" + strings[0]);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
