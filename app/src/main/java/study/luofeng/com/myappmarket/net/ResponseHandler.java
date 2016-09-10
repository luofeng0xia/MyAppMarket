package study.luofeng.com.myappmarket.net;

/**
 * ResponseHandler
 * Created by weixi on 2016/9/9.
 */
public interface ResponseHandler {
    void handlerError();
    void handlerEmpty();
    void handlerSuccess(String json);
}
