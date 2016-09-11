package study.luofeng.com.myappmarket.net;

import study.luofeng.com.myappmarket.event.LoadHelper;

/**
 * AsyncResSuccessHandler 把错误和空数据直接封装
 * Created by weixi on 2016/9/9.
 */
public abstract class AsyncResSuccessHandler extends AsyncResponseHandler {

    private LoadHelper helper;

    public AsyncResSuccessHandler(LoadHelper helper) {
        this.helper = helper;
    }

    @Override
    public void handlerError() {
        helper.onLoadError();
        //刷新pager
    }

    @Override
    public void handlerEmpty() {
        helper.onLoadEmpty();
        //刷新pager
    }

}
