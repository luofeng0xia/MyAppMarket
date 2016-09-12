package study.luofeng.com.myappmarket.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.target.ViewTarget;
import com.google.gson.Gson;

import java.io.InputStream;

import study.luofeng.com.myappmarket.R;
import study.luofeng.com.myappmarket.net.MyHttpClient;

/**
 * 自定义的Application对象，保存一些全局变量
 * Created by weixi on 2016/9/8.
 */
public class MyApplication extends Application {

    public static Context context;
    public static int mainThreadId;
    public static MyHttpClient client;
    public static Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();

        // 全局Gson对象
        gson = new Gson();

        //全局Context对象
        context = getApplicationContext();

        //主线程id
        mainThreadId = Process.myTid();

        //全局访问网络client
        client = MyHttpClient.createClient();

        //设置glide用okHttp访问网络
        Glide.get(context).register(GlideUrl.class, InputStream.class,new OkHttpUrlLoader.Factory(client.getOkHttp()));
        ViewTarget.setTagId(R.id.glide_tag);

    }
}
