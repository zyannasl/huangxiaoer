package com.example.asus.huangxiaoer.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.asus.huangxiaoer.Iview.IMBanBenGengXinActivity;
import com.example.asus.huangxiaoer.R;
import com.example.asus.huangxiaoer.bean.BanBenGengXinBean;
import com.example.asus.huangxiaoer.presenter.BanBenGengXinPresenter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BanBenGengXinActivity extends AppCompatActivity implements IMBanBenGengXinActivity<BanBenGengXinBean> {
    private File file;
    private SharedPreferences sp;
    private Long fileLength;
    private boolean isCoun = false;
    private ProgressDialog dialog;
    private Button bt_jiancebanben;
    private boolean isMust = false;
    private BanBenGengXinBean.DataBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ban_ben_geng_xin );
        bt_jiancebanben = findViewById( R.id.bt_jiancebanben );
        if (Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED )) {
            //若挂载 则创建存放位置(SDK中)
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            String path = externalStorageDirectory.getAbsolutePath() + File.separator + "new.apk";
            file = new File( path );
            //判断文件是否存在 不存在则创建
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        sp = getSharedPreferences( "version", MODE_PRIVATE );

        fileLength = sp.getLong( "length", 0 );

        if (fileLength != 0) {
            isCoun = true;
        } else {
            isCoun = false;
        }
        BanBenGengXinPresenter presenter = new BanBenGengXinPresenter( this );

        dialog = new ProgressDialog( BanBenGengXinActivity.this );
        //设置进度条框式为横向
        dialog.setProgressStyle( ProgressDialog.STYLE_HORIZONTAL );
        //设置手指触摸弹出框外，触发点击事件
        dialog.setOnCancelListener( new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialog.dismiss();
                finish();
            }
        } );
        initLenter();
    }

    private void initLenter() {
        bt_jiancebanben.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int last_version = data.getType();

                try {
                    PackageManager packageManager = getPackageManager();
                    PackageInfo packageInfo = packageManager.getPackageInfo( getPackageName(), 0 );

                    int i = packageInfo.versionCode;
                    if (i < last_version) {
                        AlertDialog.Builder builder = new AlertDialog.Builder( BanBenGengXinActivity.this );
                        builder.setTitle( "更新" ).setMessage( "检查到最新版本" );
                        //判断是否要强制更新
                        if (i < data.getVId()) {
                            isMust = true;
                            Toast.makeText( BanBenGengXinActivity.this, "版本太需要强制更新", Toast.LENGTH_SHORT ).show();
                            //设置按钮并添加点击事件监听
                            builder.setPositiveButton( "立刻更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //调用下载新版本Apk
                                    setUpdateVersion( data.getApkUrl(),null );
                                }
                            } )//当手指触摸到弹框之外进行监听
                                    .setOnCancelListener( new DialogInterface.OnCancelListener() {
                                        @Override
                                        public void onCancel(DialogInterface dialogInterface) {

                                            //关闭弹框
                                            dialog.dismiss();
                                            //关闭当前程序
                                            finish();
                                        }
                                    } );
                        } else {
                            builder.setNegativeButton( "取消", null ).setPositiveButton( "更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText( BanBenGengXinActivity.this, "更新版本", Toast.LENGTH_SHORT ).show();
                                }
                            } );
                        }
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside( !isMust );
                        alertDialog.show();
                    } else {
                        Toast.makeText( BanBenGengXinActivity.this, "已近是最新版本", Toast.LENGTH_SHORT ).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } );
    }

    private void setUpdateVersion(String url, final String md5) {
        dialog.show();
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url( url )
                .get()
                .build();
        okhttp3.Call call = client.newCall( request );
        call.enqueue( new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                long l = response.body().contentLength();
                InputStream inputStream = response.body().byteStream();

                toInputString( l, inputStream, md5 );
            }
        } );


    }


    private void toInputString(long l, InputStream inputStream,String md5) {
        try {

            //随机储存的类
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

            //定义一个byte数据 来决定一次读取多少字节
            byte[] bytes = new byte[2048];
            //
            int len = 0;

            //获取文件的长度
            long sum = file.length();

            //将获取过文件长度的变量存入 随机储存类中,
            //当sd卡中文件长度是0是，随机储存类储存的位置也是在0
            randomAccessFile.seek(sum);

            //while循环进行读取数据
            while ((len = inputStream.read(bytes, 0, bytes.length)) != -1) {

                sum += len;
                //进行写入数据
                randomAccessFile.write(bytes, 0, len);

                //移动到当前已经下载的文件大小的位置
                randomAccessFile.seek(sum);

                //算出下载进度
                int i = (int) (sum * 100 / l);

                //为进度条赋值
                dialog.setProgress(i);

                //当进度条数大于99时进行关闭
                if (i > 99) {

                    //读取完了 需要进行关闭流
                    inputStream.close();
                    //并关闭进度条的弹框
                    dialog.dismiss();

                    //调用检测 本地文件是否还和网络文件一致
                    checkAPK(file, md5);

                    //跳出循环
                    break;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void checkAPK(File file, String md5) {
        //调用工具类 算出文件的md5值
        String fileMD5 = FileMd5Utils.getFileMD5(file);

        Log.d("--", "网络md5:---" + md5 + "--本地文件的MD5:--" + fileMD5);
        //equalsIgnoreCase 实现可以忽略大小写， 由于md5是又大小写
        //判断 文件的md5值 和网络下载的md5值 是否一致
        if (fileMD5.equalsIgnoreCase(md5)) {
            insertAPK(file);
        } else {
            Toast.makeText(this, "文件出现故障 md5值不一致", Toast.LENGTH_SHORT).show();
        }

    }
    private void insertAPK(File file) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType( Uri.fromFile(file), "application/vnd.android.package-archive");
        //最好添加上这个Flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, 1000);
        System.exit(0);

    }
    @Override
    public void banbengengxin(BanBenGengXinBean banBenGengXinBean) {
        data = banBenGengXinBean.getData();
    }
}
