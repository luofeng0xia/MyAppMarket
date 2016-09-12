package study.luofeng.com.myappmarket.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import study.luofeng.com.myappmarket.bean.AppDetailInfo;
import study.luofeng.com.myappmarket.bean.RequestParams;
import study.luofeng.com.myappmarket.event.LoadHelper;
import study.luofeng.com.myappmarket.global.MyApplication;
import study.luofeng.com.myappmarket.global.MyConstant;
import study.luofeng.com.myappmarket.net.AsyncResSuccessHandler;
import study.luofeng.com.myappmarket.ui.view.LoadPager;

/**
 * AppDetailActivity
 * Created by weixi on 2016/9/12.
 */
public class AppDetailActivity extends MyBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String packageName = getIntent().getStringExtra("packageName");

        LoadPager pager=new LoadPager(MyApplication.context){

            @Override
            public void load(LoadHelper helper) {

                RequestParams params=new RequestParams();
                params.setModel(MyConstant.URL_KEY_DETAIL);
                params.setIndex(0);
                params.putString("packageName",packageName);

                MyApplication.client.get(params,new AsyncResSuccessHandler(helper) {
                    @Override
                    public View createSuccessView(String json) {
                        return AppDetailActivity.this.createSuccessView(json);
                    }
                });
            }
        };
        setContentView(pager);
    }


    private View createSuccessView(String json){
        TextView textView = new TextView(MyApplication.context);
        textView.setText("hehe");
        AppDetailInfo appDetailInfo =  MyApplication.gson.fromJson(json, AppDetailInfo.class);
        return textView;
    }
}
