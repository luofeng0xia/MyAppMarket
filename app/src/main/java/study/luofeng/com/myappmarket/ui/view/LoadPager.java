package study.luofeng.com.myappmarket.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import study.luofeng.com.myappmarket.R;
import study.luofeng.com.myappmarket.net.LoadHelper;
import study.luofeng.com.myappmarket.utils.UiUtils;

/**
 * 加载界面
 * -未加载-加载中-加载失败-数据为空-加载成功-
 * Created by weixi on 2016/9/8.
 */
public abstract class LoadPager extends FrameLayout {

    private View mLoadingPage;
    private View mEmptyPage;
    private View mErrorPage;
    private View mSuccessPage;

    public enum StateLoad {
        LOAD_UNDO,//未加载
        LOADING,//加载中
        LOAD_ERROR,//加载出错
        LOAD_EMPTY,//数据为空
        LOAD_SUCCESS//加载成功
    }

    StateLoad stateLoad = StateLoad.LOAD_UNDO;

    public LoadPager(Context context) {
        this(context, null);
    }

    public LoadPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化loadingPage
     */
    private void initView() {
        //创建加载界面
        if (mLoadingPage==null) {
            mLoadingPage = UiUtils.inflate(R.layout.page_loading);
            addView(mLoadingPage);
        }
        //创建数据为空界面
        if (mEmptyPage==null) {
            mEmptyPage = UiUtils.inflate(R.layout.page_empty);
            addView(mEmptyPage);
        }
        //创建加载失败界面
        if (mErrorPage==null){
            mErrorPage = UiUtils.inflate(R.layout.page_error);
            addView(mErrorPage);
        }
        //设置界面显示
        setViewVisible();
    }

    private void setViewVisible() {
        if (mSuccessPage==null){
            // 判断是否是加载状态，显示加载page
            if (stateLoad==StateLoad.LOAD_UNDO||stateLoad==StateLoad.LOADING){
                stateLoad=StateLoad.LOADING;
                mLoadingPage.setVisibility(VISIBLE);
                load(new MyLoadHelper()); //加载逻辑
            }else {
                mLoadingPage.setVisibility(GONE);
            }
            //设置错误界面
            mErrorPage.setVisibility(stateLoad==StateLoad.LOAD_ERROR?VISIBLE:GONE);

            //设置数据为空界面
            mEmptyPage.setVisibility(stateLoad==StateLoad.LOAD_EMPTY?VISIBLE:GONE);
        }else {
            addView(mSuccessPage);
            mLoadingPage.setVisibility(GONE);
            mEmptyPage.setVisibility(GONE);
            mErrorPage.setVisibility(GONE);
            mSuccessPage.setVisibility(VISIBLE);
        }
    }

    abstract public void load(LoadHelper helper);

    class MyLoadHelper implements LoadHelper{

        @Override
        public void onLoadError() {
            stateLoad=StateLoad.LOAD_ERROR;
            setViewVisible();
        }

        @Override
        public void onLoadEmpty() {
            stateLoad=StateLoad.LOAD_EMPTY;
            setViewVisible();
        }

        @Override
        public void onLoadSuccess(View view) {
            stateLoad=StateLoad.LOAD_SUCCESS;
            mSuccessPage=view;
            setViewVisible();
        }
    }

}
