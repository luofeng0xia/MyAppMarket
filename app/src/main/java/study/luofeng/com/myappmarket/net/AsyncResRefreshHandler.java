package study.luofeng.com.myappmarket.net;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import study.luofeng.com.myappmarket.global.MyApplication;

/**
 * AsyncResRefreshHandler
 * Created by weixi on 2016/9/10.
 */
public abstract class AsyncResRefreshHandler extends AsyncResponseHandler {

    private String oldJson;
    private SwipeRefreshLayout swipeRefreshLayout;

    public AsyncResRefreshHandler(String oldJson, SwipeRefreshLayout swipeRefreshLayout) {
        this.oldJson=oldJson;
        this.swipeRefreshLayout=swipeRefreshLayout;
    }

    @Override
    public void handlerError() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void handlerEmpty() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void handlerSuccess(String json) {
        if (!oldJson.equals(json)) {
            handlerRefresh(json);
        }else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(MyApplication.context,"已经是最新数据",Toast.LENGTH_SHORT).show();
        }
    }

    public abstract void handlerRefresh(String newJson);
}
