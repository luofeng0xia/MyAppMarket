package study.luofeng.com.myappmarket.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import study.luofeng.com.myappmarket.R;
import study.luofeng.com.myappmarket.adapter.MyRecyclerAdapter;
import study.luofeng.com.myappmarket.bean.AppInfo;
import study.luofeng.com.myappmarket.bean.HomeInfo;
import study.luofeng.com.myappmarket.event.MyOnItemTouchListener;
import study.luofeng.com.myappmarket.event.RecyclerItemClickHelper;
import study.luofeng.com.myappmarket.global.MyApplication;
import study.luofeng.com.myappmarket.global.MyConstant;
import study.luofeng.com.myappmarket.event.OnLoadMoreListener;
import study.luofeng.com.myappmarket.net.AsyncResRefreshHandler;
import study.luofeng.com.myappmarket.net.AsyncResSuccessHandler;
import study.luofeng.com.myappmarket.net.AsyncResponseHandler;
import study.luofeng.com.myappmarket.event.LoadHelper;
import study.luofeng.com.myappmarket.ui.holder.RecyclerLoadHolder;
import study.luofeng.com.myappmarket.utils.UiUtils;

/**
 * 主页
 * Created by weixi on 2016/9/8.
 */
public class HomeFragment extends MyBaseFragment implements OnLoadMoreListener {

    private MyRecyclerAdapter myRecyclerAdapter;
    private ArrayList<AppInfo> list;

    @Override
    protected void loadFromNet(final LoadHelper helper) {
        list = new ArrayList<>();
        //请求网络
        MyApplication.client.get(MyConstant.URL_KEY_HOME, list.size(), new AsyncResSuccessHandler(helper) {
            @Override
            public void handlerSuccess(String json) {
                helper.onLoadSuccess(createSuccessView(json));
                isSuccess = true; //创建成功
            }
        });
    }

    /**
     * 创建成功界面
     *
     * @return SuccessView
     */
    private View createSuccessView(final String json) {

        list.addAll(getAppInfoList(json));

        View view = UiUtils.inflate(R.layout.page_list);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.page_refresh);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.page_rl);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.context));
        recyclerView.setHasFixedSize(true);

        myRecyclerAdapter = new MyRecyclerAdapter(this.list, recyclerView);
        recyclerView.setAdapter(myRecyclerAdapter);

        // 利用RecyclerView的addOnChildAttachStateChangeListener监听 做的itemClick
        /*RecyclerItemClickHelper.addOn(recyclerView).
        setOnItemClickListener(new RecyclerItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, int position, View itemView) {
                Log.d("*****",itemView.toString());
            }
        });*/

        // 利用RecyclerView的addOnItemTouchListener事件 做的itemClick
        recyclerView.addOnItemTouchListener(new MyOnItemTouchListener(recyclerView, new MyOnItemTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("*****",view.toString()+"position: "+position);
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                return false;
            }
        }));


        myRecyclerAdapter.setOnLoadMoreListener(this); //设置上拉加载更多监听

        // 设置刷新条颜色
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyApplication.client.get(MyConstant.URL_KEY_HOME, 0, new AsyncResRefreshHandler(json, swipeRefreshLayout) {
                    @Override
                    public void handlerRefresh(String newJson) {

                        HomeFragment.this.list.clear();
                        // 更新list中的数据
                        HomeFragment.this.list.addAll(getAppInfoList(newJson));
                        myRecyclerAdapter.notifyDataSetChanged();
                        Toast.makeText(MyApplication.context, "刷新完成", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        return view;
    }

    /**
     * 解析json,获得存储appInfo的集合
     * @param json json
     * @return List
     */
    private List<AppInfo> getAppInfoList(String json) {
        HomeInfo homeInfo = MyApplication.gson.fromJson(json, HomeInfo.class);
        return Arrays.asList(homeInfo.getList());
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

        //设置adapter的isLoading，避免重复加载
        myRecyclerAdapter.isLoading();
        //准备加载更多的数据 这里应该是异步的
        MyApplication.client.get(MyConstant.URL_KEY_HOME, list.size(), new AsyncResponseHandler() {
            @Override
            public void handlerError() {
                myRecyclerAdapter.setStateLoad(RecyclerLoadHolder.STATE_LOAD_ERROR);
                myRecyclerAdapter.notifyDataSetChanged();
                myRecyclerAdapter.isLoaded();
            }

            @Override
            public void handlerEmpty() {
                myRecyclerAdapter.setStateLoad(RecyclerLoadHolder.STATE_LOAD_NODATA);
                myRecyclerAdapter.notifyDataSetChanged();
                myRecyclerAdapter.isLoaded();
            }

            @Override
            public void handlerSuccess(String json) {
                list.addAll(getAppInfoList(json));
                myRecyclerAdapter.notifyDataSetChanged();
                myRecyclerAdapter.isLoaded();
            }
        });
    }
}
