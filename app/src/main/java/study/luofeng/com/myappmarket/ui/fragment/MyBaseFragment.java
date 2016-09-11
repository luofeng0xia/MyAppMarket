package study.luofeng.com.myappmarket.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import study.luofeng.com.myappmarket.global.MyApplication;
import study.luofeng.com.myappmarket.event.LoadHelper;
import study.luofeng.com.myappmarket.ui.view.LoadPager;

/**
 * Pager对应的Fragment的基类
 * Created by weixi on 2016/9/8.
 */
public abstract class MyBaseFragment extends Fragment{

    private View successView;
    protected boolean isSuccess;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        if (successView==null){
            view = new LoadPager(MyApplication.context) {
                @Override
                public void load(LoadHelper helper) {
                    loadFromNet(helper);
                }
            };
        }else {
            view =successView;
        }
        return view;
    }

    /**
     * 从网络加载
     * @param helper 界面显示
     */
    abstract protected void loadFromNet(LoadHelper helper);

    /**
     * 如果界面加载成功，把view保存，避免切换时重新加载
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isSuccess) successView=getView();
    }
}
