package study.luofeng.com.myappmarket.net;

import android.os.Message;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import study.luofeng.com.myappmarket.global.MyConstant;
import study.luofeng.com.myappmarket.event.HttpHandler;

/**
 * MyHttpClient
 * Created by weixi on 2016/9/9.
 */
public class MyHttpClient {

    private static MyHttpClient client;
    private OkHttpClient okHttp;

    public OkHttpClient getOkHttp() {
        return okHttp;
    }

    private MyHttpClient(OkHttpClient okHttp) {
        this.okHttp = okHttp;
    }

    /**
     * 创建一个单例的MyHttpClient
     * @return MyHttpClient
     */
    public static MyHttpClient createClient(){
        if (client==null){
            return new MyHttpClient(new OkHttpClient());
        }
        return client;
    }

    /**
     * 拼接url
     * @param modelKey 模块关键字
     * @param index 从第几个数据开始获取
     * @param handler 异步响应
     */
    public void get(String modelKey,int index,AsyncResponseHandler handler){
        String url=MyConstant.BASE_URL+"/"+modelKey+"?index="+index;
        get(url,handler);
    }

    /**
     * get请求
     * @param url HttpUrl
     */
    public void get(String url, AsyncResponseHandler handler){

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        Request request = builder.build();
        get(handler.getHttpHandler(), request);
    }

    /**
     * 统一了url,内部调用
     * @param httpHandler HttpHandler
     * @param request Request
     */
    private void get(final HttpHandler httpHandler, Request request) {
        okHttp.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg=Message.obtain();
                msg.what= MyConstant.HTTP_MSG_WHAT_ERROR;
                httpHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String content=response.body().string();
                if (content.length()==0) {
                    Message msg=Message.obtain();
                    msg.what= MyConstant.HTTP_MSG_WHAT_EMPTY;
                    httpHandler.sendMessage(msg);
                }else {
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg=Message.obtain();
                    msg.what= MyConstant.HTTP_MSG_WHAT_SUCCESS;
                    msg.obj=content;
                    httpHandler.sendMessage(msg);
                }
            }
        });
    }

    /**
     * get请求
     * @param url URL
     */
    public void get(URL url,AsyncResponseHandler handler){
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        Request request = builder.build();
        get(handler.getHttpHandler(),request);
    }

    /**
     * get请求
     * @param url String
     */
    public void get(HttpUrl url,AsyncResponseHandler handler){
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        Request request = builder.build();
        get(handler.getHttpHandler(),request);
    }

    /**
     * 暂不实现
     */
    public void post(){

    }

}
