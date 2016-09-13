package study.luofeng.com.myappmarket.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import study.luofeng.com.myappmarket.R;
import study.luofeng.com.myappmarket.adapter.SafeListAdapter;
import study.luofeng.com.myappmarket.adapter.ScreenshotAdapter;
import study.luofeng.com.myappmarket.bean.AppDetailInfo;
import study.luofeng.com.myappmarket.bean.RequestParams;
import study.luofeng.com.myappmarket.event.LoadHelper;
import study.luofeng.com.myappmarket.global.MyApplication;
import study.luofeng.com.myappmarket.global.MyConstant;
import study.luofeng.com.myappmarket.net.AsyncResSuccessHandler;
import study.luofeng.com.myappmarket.ui.view.LoadPager;
import study.luofeng.com.myappmarket.ui.view.MyExpandableListView;
import study.luofeng.com.myappmarket.utils.MyUtils;
import study.luofeng.com.myappmarket.utils.UiUtils;

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
        View view = UiUtils.inflate(R.layout.activity_appdetail);
        AppDetailInfo appDetailInfo =  MyApplication.gson.fromJson(json, AppDetailInfo.class);
        //基本信息模块
        ImageView appIcon = (ImageView) view.findViewById(R.id.iv_app_icon);
        TextView appName = (TextView) view.findViewById(R.id.tv_app_name);
        TextView appVersion = (TextView) view.findViewById(R.id.tv_app_version);
        TextView appDownloadCount = (TextView) view.findViewById(R.id.tv_app_down_count);
        Glide.with(MyApplication.context).load(MyConstant.URL_IMAGE_BASE+appDetailInfo.getIconUrl()).into(appIcon);
        appName.setText(appDetailInfo.getName());
        appVersion.setText("版本："+appDetailInfo.getVersion()+"/"+
                MyUtils.getAppSize(appDetailInfo.getSize())+"/"+
                appDetailInfo.getDate());
        appDownloadCount.setText("下载量："+appDetailInfo.getDownloadNum());

        //安全模块
        MyExpandableListView expandableListView = (MyExpandableListView) view.findViewById(R.id.expandableListView);
        expandableListView.setDivider(null);
        List<AppDetailInfo.SafeBean> safe = appDetailInfo.getSafe();
        expandableListView.setAdapter(new SafeListAdapter(safe));

        //截图模块
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        List<String> screen = appDetailInfo.getScreen();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyApplication.context,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ScreenshotAdapter(screen));

        //评分模块
        TextView tvStar = (TextView) view.findViewById(R.id.tv_app_star);
        RatingBar rbStar = (RatingBar) view.findViewById(R.id.rb_app_star);
        tvStar.setText(appDetailInfo.getStars()+"");
        rbStar.setRating(appDetailInfo.getStars());

        //介绍模块
        TextView des = (TextView) view.findViewById(R.id.tv_app_des);
        TextView author = (TextView) view.findViewById(R.id.tv_app_author);
        des.setText(appDetailInfo.getDes());
        author.setText("来自 "+appDetailInfo.getAuthor());

        setTitle(appDetailInfo.getName());
        return view;
    }
}
