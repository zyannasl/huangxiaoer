package com.example.lenovo.huangxiaoer.utils;

import com.example.lenovo.huangxiaoer.bean.LoginBean;
import com.example.lenovo.huangxiaoer.bean.RegBean;
import com.example.lenovo.huangxiaoer.bean.UploadBean;
import com.example.lenovo.huangxiaoer.bean.UserMsgBean;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface RetrofitApi {

    @POST("user/login")
    @FormUrlEncoded
    Observable<LoginBean> login(@FieldMap Map<String, String> p);

    @POST("user/reg")
    @FormUrlEncoded
    Observable<RegBean> reg(@FieldMap Map<String, String> p);

    @POST("user/getUserInfo")
    @FormUrlEncoded
    Observable<UserMsgBean> userMsg(@FieldMap Map<String, String> p);

    @POST("file/upload")
    @Multipart
    Observable<UploadBean> fileupload(@Query("uid") String uid, @Part MultipartBody.Part file);

}
