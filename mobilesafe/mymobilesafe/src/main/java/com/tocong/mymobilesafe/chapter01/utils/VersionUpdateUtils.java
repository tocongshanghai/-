package com.tocong.mymobilesafe.chapter01.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.tocong.mymobilesafe.HomeActivity;
import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter01.entity.VersionEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by tocong on 2016/4/21.
 * 版本更新工具类
 */
public class VersionUpdateUtils {
    //网络错误
    private static final int MESSAGE_NET_ERROR = 101;
    private static final int MESSAGE_Io_ERROR = 102;
    private static final int MESSAGE_JSON_ERROR = 103;
    private static final int MESSAGE_SHOW_DIALOG = 104;
    private static final int MESSAGE__ENTERHOME = 105;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_Io_ERROR:
                    Toast.makeText(context, "io异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case MESSAGE_JSON_ERROR:
                    Toast.makeText(context, "json异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case MESSAGE_NET_ERROR:
                    Toast.makeText(context, "net异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case MESSAGE_SHOW_DIALOG:
                    showUpdateDialog(versionEntity);
                    break;
                case MESSAGE__ENTERHOME:
                    Intent intent = new Intent(context, HomeActivity.class);
                    context.startActivity(intent);
                    context.finish();
                    break;

            }
        }
    };


    public VersionUpdateUtils(String version, Activity activity) {
        mVersion = version;
        context = activity;

    }


    //    本地版本号
    private String mVersion;
    private Activity context;
    private ProgressDialog mPrgressDialog;
    private VersionEntity versionEntity;



/*
* 获取服务器版本号
*
* */

    public void getCloudVersion() {

        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://15.83.251.180:8080/update.json");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = connection.getInputStream();
                String result = StreamUtils.readFromStream(inputStream);
                System.out.println("网络返回的数据:" + result);
                JSONObject jsonObject = new JSONObject(result);
                versionEntity = new VersionEntity();
                String code = jsonObject.getString("versionName");
                String desc = jsonObject.getString("desc");
                String apkUrl = jsonObject.getString("apkUrl");
                versionEntity.setVersioncode(code);
                versionEntity.setDescription(desc);
                versionEntity.setApkurl(apkUrl);
//版本号不一致
                if (!mVersion.equals(versionEntity.getVersioncode())) {

                    handler.sendEmptyMessage(MESSAGE_SHOW_DIALOG);


                }

            }

        } catch (MalformedURLException e) {
            handler.sendEmptyMessage(MESSAGE_NET_ERROR);
            e.printStackTrace();
        } catch (IOException e) {
            handler.sendEmptyMessage(MESSAGE_Io_ERROR);

            e.printStackTrace();
        } catch (JSONException e) {
            handler.sendEmptyMessage(MESSAGE_JSON_ERROR);
            e.printStackTrace();
        }


    }

/*
* 弹出更新提示框
*
*
*
* */

    private void showUpdateDialog(final VersionEntity versionEntity) {
//
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("check new version:" + versionEntity.getVersioncode());
        builder.setMessage(versionEntity.getDescription());
        builder.setCancelable(false);
        builder.setIcon(R.mipmap.ic_launcher);
//
        builder.setPositiveButton("now update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initProgressDialog();
                downloadNewApk(versionEntity.apkurl);
            }
        });

        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                enterHome();
            }
        });
        builder.show();
    }


/*
* 初始化进度条对话框
* */

    private void initProgressDialog() {
        mPrgressDialog = new ProgressDialog(context);
        mPrgressDialog.setTitle("准备下载");
        mPrgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mPrgressDialog.show();
    }


/*
* 下载新版本
*
* */


    protected void downloadNewApk(String apkUrl) {
        String target= Environment.getExternalStorageDirectory()+"/update.apk";
        DownLoadUtils downLoadUtils = new DownLoadUtils();
        downLoadUtils.downapk(apkUrl,target, new DownLoadUtils.MyCallBack() {
            @Override
            public void onSuccess(ResponseInfo<File> arg0) {
                mPrgressDialog.dismiss();
                MyUtils.installApk(context,arg0.result);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                mPrgressDialog.setMessage("下载失败");
                mPrgressDialog.dismiss();
                enterHome();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                mPrgressDialog.setMax((int) total);
                mPrgressDialog.setMessage("正在下载");
                mPrgressDialog.setProgress((int) current);
            }
        });


    }

    private void enterHome() {
        handler.sendEmptyMessageDelayed(MESSAGE__ENTERHOME, 2000);


    }

}
