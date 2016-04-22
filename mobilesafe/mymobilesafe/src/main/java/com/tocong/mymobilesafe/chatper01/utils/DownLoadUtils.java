package com.tocong.mymobilesafe.chatper01.utils;


import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

/**
 * Created by tocong on 2016/4/20.
 */
public class DownLoadUtils {

    interface MyCallBack {
        void onSuccess(ResponseInfo<File> arg0);

        void onFailure(HttpException arg0, String arg1);

        void onLoading(long total, long current, boolean isUploading);


    }

    /*
    * url 下载的地址
    * targetFile 下载我呢见的本地路径
    * MyCallBack 一个接口对象----用于监听文件下载状态的
    * */
    public void downapk(String url, String targetFile, final MyCallBack myCallBack) {
        //创建httpUtils对象
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.download(url, targetFile, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                myCallBack.onSuccess(responseInfo);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                myCallBack.onFailure(error, msg);
            }

            public void onLoading(long total, long current, boolean isUploading) {
                myCallBack.onLoading(total, current, isUploading);

            }

        });

    }


}
