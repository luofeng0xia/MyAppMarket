package study.luofeng.com.myappmarket.net;

import android.view.View;

/**
 * LoadHelper
 * Created by weixi on 2016/9/9.
 */
public interface LoadHelper {

    void onLoadError();

    void onLoadEmpty();

    void onLoadSuccess(View view);
}
