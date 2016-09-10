package study.luofeng.com.myappmarket.net;

import study.luofeng.com.myappmarket.message.HttpHandler;

/**
 * AsyncResponseHandler
 * Created by weixi on 2016/9/9.
 */
public abstract class AsyncResponseHandler implements ResponseHandler {

    private HttpHandler httpHandler;

    public AsyncResponseHandler() {
        httpHandler = new HttpHandler(this);
    }

    public HttpHandler getHttpHandler() {
        return httpHandler;
    }

}
