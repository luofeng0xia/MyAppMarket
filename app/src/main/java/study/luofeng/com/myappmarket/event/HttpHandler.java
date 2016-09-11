package study.luofeng.com.myappmarket.event;

import android.os.Handler;
import android.os.Message;

import study.luofeng.com.myappmarket.global.MyConstant;
import study.luofeng.com.myappmarket.net.AsyncResponseHandler;

/**
 * HttpHandler 异步处理OkHttp响应
 * Created by weixi on 2016/9/9.
 */
public class HttpHandler extends Handler {

    private AsyncResponseHandler asyncResponseHandler;

    public HttpHandler(AsyncResponseHandler asyncResponseHandler) {
        this.asyncResponseHandler = asyncResponseHandler;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MyConstant.HTTP_MSG_WHAT_EMPTY:
                asyncResponseHandler.handlerEmpty();
                break;
            case MyConstant.HTTP_MSG_WHAT_ERROR:
                asyncResponseHandler.handlerError();
                break;
            case MyConstant.HTTP_MSG_WHAT_SUCCESS:
                asyncResponseHandler.handlerSuccess((String) msg.obj);
                break;
        }
    }
}
