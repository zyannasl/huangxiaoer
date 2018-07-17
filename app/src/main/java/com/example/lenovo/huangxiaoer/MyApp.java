package com.example.lenovo.huangxiaoer;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hjhrq991.screenadapter.ScreenAdapterApplication;
import com.tencent.bugly.crashreport.CrashReport;

public class MyApp extends ScreenAdapterApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        //bugly
        CrashReport.initCrashReport(getApplicationContext(), "351170a891", true);
    }
}
