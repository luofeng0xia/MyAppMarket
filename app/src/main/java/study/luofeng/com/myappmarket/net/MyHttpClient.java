package study.luofeng.com.myappmarket.net;

import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import study.luofeng.com.myappmarket.bean.RequestParams;
import study.luofeng.com.myappmarket.event.HttpHandler;
import study.luofeng.com.myappmarket.global.MyConstant;

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

    // 参数未封装之前写法
    /*public void get(String modelKey,int index,AsyncResponseHandler handler){
        String url=MyConstant.BASE_URL+"/"+modelKey+"?index="+index;
        get(url,handler);
    }*/

    /**
     * 把参数封装之后
     * @param params 参数
     * @param handler handler
     */
    public void get(RequestParams params,AsyncResponseHandler handler){
        get(getUrlFromParams(params),handler);
    }

    /**
     * 拼接url
     * @param params params
     * @return url
     */
    private String getUrlFromParams(RequestParams params){
        StringBuilder sb = new StringBuilder();
        sb.append(MyConstant.BASE_URL);
        String model = params.getModel();
        if (model!=null&&!model.trim().isEmpty()){
            sb.append(model);
        }
        int index = params.getIndex();
        if (index!=-1){
            sb.append("?index=");
            sb.append(index);
        }
        Map<String, String> requestMap = params.getRequestMap();
        if (requestMap.size()>0){
            for (Map.Entry<String,String> entry :
                    requestMap.entrySet()) {
                sb.append("&");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
            }
        }
//        Log.d("url",sb.toString());
        return sb.toString();
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
