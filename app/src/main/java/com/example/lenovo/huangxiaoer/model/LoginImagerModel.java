package com.example.lenovo.huangxiaoer.model;

import android.net.Uri;
import android.os.Environment;

import com.example.lenovo.huangxiaoer.bean.UploadBean;
import com.example.lenovo.huangxiaoer.utils.RetrofitUtils;
import com.example.mvp.mvp.BaseModel;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class LoginImagerModel extends BaseModel {
    private RetrofitUtils retrofitUtils;
  //  File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");

    public void loginImager(Uri imageUri, final ILoginImagerModel iLoginImagerModel) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), imageUri.getPath());
        String name = "13597";
        MultipartBody.Part part = MultipartBody.Part.createFormData("imageUri", imageUri.getPath(), requestBody);
        retrofitUtils = RetrofitUtils.getInstance();
        retrofitUtils.getApi().fileupload(name, part)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Observer<UploadBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UploadBean uploadBean) {
                        iLoginImagerModel.success(uploadBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface ILoginImagerModel {
        void success(UploadBean uploadBean);
    }
}
