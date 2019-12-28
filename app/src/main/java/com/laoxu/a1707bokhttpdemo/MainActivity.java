package com.laoxu.a1707bokhttpdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.laoxu.a1707bokhttpdemo.api.Api;
import com.laoxu.a1707bokhttpdemo.utils.OkhttpUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_banner)
    public void getBanner(View view){

//        //okhttp请求接口
//        //第一步，创建okhttp的客户端对象（）,总管理,java设计模式建造者模式
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()//对象构造
//                .writeTimeout(3000, TimeUnit.MILLISECONDS)//写超时
//                .readTimeout(3,TimeUnit.SECONDS)//读超时
//                .connectTimeout(3,TimeUnit.SECONDS)//连接超时
//                .build();//对象创建
//
//
//        //第二步，创建请求对象数据封装体,build模式，构建者，建造者模式，调用方式：链式调用
//        Request request = new Request.Builder()
//                .get()//请求方法：get
//                .url(Api.BANNER_URL)//请求url路径
//                .build();
//
//        //第三步请求
//        Call call = okHttpClient.newCall(request);
//        //第三步，调用异步请求，开启一个子线程（工作线程/work线程）
//        call.enqueue(new Callback() {
//            //失败
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                Toast.makeText(MainActivity.this, "网络可能有问题", Toast.LENGTH_SHORT).show();
//            }
//
//            //响应，成功,返回的数据在子线程，不能更新ui
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//
//                String result = response.body().string();//得到响应体的数据
//
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
//
//
//
//
//                    }
//                });
//
//                 }
//        });

        OkhttpUtils.getInstance().doGet(Api.BANNER_URL, new OkhttpUtils.OkhttpCallback() {
            @Override
            public void onResponse(String result) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
//        String keyrod = "手机";
//
//        OkhttpUtils.getInstance().doGet(Api.PRODUCT_URL + "?keyword="+keyrod+"&count=5&page=1", new OkhttpUtils.OkhttpCallback() {
//            @Override
//            public void onResponse(String result) {
//
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//
//            }
//        });



    }

    @OnClick(R.id.btn_banner2)
    public void getBanner2(View view){

        //okhttp请求接口
        //第一步，创建okhttp的客户端对象（）,总管理,java设计模式建造者模式
        OkHttpClient okHttpClient = new OkHttpClient.Builder()//对象构造
                .writeTimeout(3000, TimeUnit.MILLISECONDS)//写超时
                .readTimeout(3,TimeUnit.SECONDS)//读超时
                .connectTimeout(3,TimeUnit.SECONDS)//连接超时
                .build();//对象创建


        //第二步，创建请求对象数据封装体,build模式，构建者，建造者模式，调用方式：链式调用
        Request request = new Request.Builder()
                .get()//请求方法：get
                .url(Api.BANNER_URL)//请求url路径
                .build();



            //第三步请求
            Call call = okHttpClient.newCall(request);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response response = call.execute();//同步方法，一个窗口买票，不会开启子线程

                        String restult = response.body().string();

                        runOnUiThread(new Runnable() {//
                            @Override
                            public void run() {

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();




    }
}
