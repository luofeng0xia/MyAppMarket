package study.luofeng.com.myappmarket.ui.fragment;

import study.luofeng.com.myappmarket.event.LoadHelper;

/**
 * 游戏
 * Created by weixi on 2016/9/8.
 */
public class GameFragment extends MyBaseFragment {
    @Override
    protected void loadFromNet(LoadHelper helper) {
        helper.onLoadError();
    }
}
