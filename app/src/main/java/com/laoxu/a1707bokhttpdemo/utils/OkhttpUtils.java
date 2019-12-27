package com.laoxu.a1707bokhttpdemo.utils;

import android.os.Handler;
import android.os.Message;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 单例模式封装okhttp，二次封装，封装get和post请求
 */
public class OkhttpUtils {

    Handler handler = new Handler();
    //私有属性
    private static  OkhttpUtils okhttpUtils;
    private OkHttpClient okHttpClient;

    //私有构造
    private OkhttpUtils(){

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();

    }

    /**
     * 暴露给调用者
     * @return 单例
     */
    public static OkhttpUtils getInstance() {

        if (okhttpUtils==null){
            synchronized (OkhttpUtils.class){
                if (okhttpUtils==null){
                    okhttpUtils = new OkhttpUtils();
                }
            }
        }

        return okhttpUtils;
    }

    /**
     * get请求
     */
    public void doGet(String url,OkhttpCallback okhttpCallback){

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                if (okhttpCallback!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            okhttpCallback.onFailure(e);
                        }
                    });

                }

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (okhttpCallback!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                okhttpCallback.onResponse(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            }
        });

    }


    /**
     * post请求
     */
    public void doPost(String url, HashMap<String,String> params,OkhttpCallback okhttpCallback){

        /**
         * 单独创建构建者，表单方式提交
         */
        FormBody.Builder builder = new FormBody.Builder();

        //遍历所有map的参数，取到key和value
        for (Map.Entry<String, String> paramsMap : params.entrySet()) {
            //把key和value添加到请求体中
            builder.add(paramsMap.getKey(),paramsMap.getValue());
        }

        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                if (okhttpCallback!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            okhttpCallback.onFailure(e);
                        }
                    });

                }

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (okhttpCallback!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                okhttpCallback.onResponse(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            }
        });

    }

    //声明接口引用
    private OkhttpCallback okhttpCallback;

    //对引用初始化
    public void setOkhttpCallback(OkhttpCallback okhttpCallback) {
        this.okhttpCallback = okhttpCallback;
    }

    //第一步：创建接口
    public interface OkhttpCallback{
       void onResponse(String result);
        void onFailure( Throwable throwable);
    }


}
