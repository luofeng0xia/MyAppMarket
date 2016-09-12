package study.luofeng.com.myappmarket.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import study.luofeng.com.myappmarket.R;
import study.luofeng.com.myappmarket.adapter.MyRecyclerAdapter;
import study.luofeng.com.myappmarket.bean.AppInfo;
import study.luofeng.com.myappmarket.bean.HomeInfo;
import study.luofeng.com.myappmarket.bean.RequestParams;
import study.luofeng.com.myappmarket.event.MyOnItemTouchListener;
import study.luofeng.com.myappmarket.event.RecyclerItemClickHelper;
import study.luofeng.com.myappmarket.global.MyApplication;
import study.luofeng.com.myappmarket.global.MyConstant;
import study.luofeng.com.myappmarket.event.OnLoadMoreListener;
import study.luofeng.com.myappmarket.net.AsyncResRefreshHandler;
import study.luofeng.com.myappmarket.net.AsyncResSuccessHandler;
import study.luofeng.com.myappmarket.net.AsyncResponseHandler;
import study.luofeng.com.myappmarket.event.LoadHelper;
import study.luofeng.com.myappmarket.ui.activity.AppDetailActivity;
import study.luofeng.com.myappmarket.ui.holder.RecyclerLoadHolder;
import study.luofeng.com.myappmarket.ui.holder.RecyclerNormalHolder;
import study.luofeng.com.myappmarket.utils.UiUtils;

/**
 * 主页
 * Created by weixi on 2016/9/8.
 */
public class HomeFragment extends MyBaseFragment implements OnLoadMoreListener {

    private MyRecyclerAdapter myRecyclerAdapter;
    private ArrayList<AppInfo> list;
    private List<String> picList;

    @Override
    protected void loadFromNet(final LoadHelper helper) {
        list = new ArrayList<>();
        picList = new ArrayList<>();
        //请求网络
        MyApplication.client.get(new RequestParams(MyConstant.URL_KEY_HOME,list.size()), new AsyncResSuccessHandler(helper) {
                    @Override
                    public View createSuccessView(String json) {
                        isSuccess=true;
                        return HomeFragment.this.createSuccessView(json);
                    }
                }
            );
    }

    /**
     * 创建成功界面
     *
     * @return SuccessView
     */
    private View createSuccessView(final String json) {

        Map<String,List> map =getHomeMap(json);
        list.addAll(map.get("list"));
        picList.addAll(map.get("picture"));

        View view = UiUtils.inflate(R.layout.page_list);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.page_refresh);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.page_rl);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.context));
        recyclerView.setHasFixedSize(true);

        myRecyclerAdapter = new MyRecyclerAdapter(this.list,this.picList, recyclerView);
        recyclerView.setAdapter(myRecyclerAdapter);

        // 利用RecyclerView的addOnChildAttachStateChangeListener监听 做的itemClick
        // 如果item的内部控件设置了点击事件并点击，则itemClick事件不会被触发
        RecyclerItemClickHelper.addOn(recyclerView,RecyclerNormalHolder.class,1).
        setOnItemClickListener(new RecyclerItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, int position, View itemView) {

                Intent intent = new Intent(HomeFragment.this.getActivity(), AppDetailActivity.class);
                intent.putExtra("packageName",list.get(position).getPackageName());
                startActivity(intent);
            }
        });

        // 利用RecyclerView的addOnItemTouchListener事件 做的itemClick
        // 如果itemView内部控件也需要监听点击事件，会造成冲突，既两个事件都会发生
        /*recyclerView.addOnItemTouchListener(new MyOnItemTouchListener(recyclerView, new MyOnItemTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                return false;
            }
        }));*/


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
                MyApplication.client.get(new RequestParams(MyConstant.URL_KEY_HOME,0), new AsyncResRefreshHandler(json, swipeRefreshLayout) {
                    @Override
                    public void handlerRefresh(String newJson) {

                        HomeFragment.this.list.clear();
                        // 更新list中的数据
                        Map<String, List> newMap = getHomeMap(newJson);
                        HomeFragment.this.list.addAll(newMap.get("list"));
                        HomeFragment.this.picList.addAll(newMap.get("picture"));
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
    private Map<String,List> getHomeMap(String json) {

        Map<String,List> map = new HashMap<>();
        HomeInfo homeInfo = MyApplication.gson.fromJson(json, HomeInfo.class);

        map.put("picture",Arrays.asList(homeInfo.getPicture()));
        map.put("list",Arrays.asList(homeInfo.getList()));
        return map;
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

        //设置adapter的isLoading，避免重复加载
        myRecyclerAdapter.isLoading();
        //准备加载更多的数据 这里应该是异步的
        MyApplication.client.get(new RequestParams(MyConstant.URL_KEY_HOME,list.size()), new AsyncResponseHandler() {
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
                list.addAll(getHomeMap(json).get("list"));
                myRecyclerAdapter.notifyDataSetChanged();
                myRecyclerAdapter.isLoaded();
            }
        });
    }
}
