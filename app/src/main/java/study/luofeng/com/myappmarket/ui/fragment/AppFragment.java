package study.luofeng.com.myappmarket.ui.fragment;

import study.luofeng.com.myappmarket.event.LoadHelper;

/**
 * 应用
 * Created by weixi on 2016/9/8.
 */
public class AppFragment extends MyBaseFragment {
    @Override
    protected void loadFromNet(LoadHelper helper) {
        helper.onLoadEmpty();
    }
}
