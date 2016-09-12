package study.luofeng.com.myappmarket.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import study.luofeng.com.myappmarket.R;
import study.luofeng.com.myappmarket.bean.AppInfo;
import study.luofeng.com.myappmarket.global.MyApplication;
import study.luofeng.com.myappmarket.global.MyConstant;
import study.luofeng.com.myappmarket.event.OnLoadMoreListener;
import study.luofeng.com.myappmarket.ui.holder.RecyclerHeaderHolder;
import study.luofeng.com.myappmarket.ui.holder.RecyclerLoadHolder;
import study.luofeng.com.myappmarket.ui.holder.RecyclerNormalHolder;
import study.luofeng.com.myappmarket.utils.MyUtils;
import study.luofeng.com.myappmarket.utils.UiUtils;

/**
 * MyRecyclerAdapter
 * Created by weixi on 2016/9/8.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter {

    private ArrayList<AppInfo> list; //网络加载的数据
    private List<String> picList; //头部轮播图片的url集合
    private boolean isLoading = false; //是否正在加载更多
    private OnLoadMoreListener listener; //加载更多监听器
    private static final int TYPE_ITEM_NORMAL = 0; //普通的item
    private static final int TYPE_ITEM_LOAD = 1; //加载更多的item
    private static final int TYPE_ITEM_HEADER = 2; //头部轮询
    private int stateLoad = 0; // 加载更多的状态

    public int getStateLoad() {
        return stateLoad;
    }

    public void isLoaded() {
        isLoading = false;
    }

    public void isLoading() {
        isLoading = true;
    }

    public void setStateLoad(int stateLoad) {
        this.stateLoad = stateLoad;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.listener = listener;
    }


    public MyRecyclerAdapter(ArrayList<AppInfo> list, List<String> picList, RecyclerView recyclerView) {
        this.list = list;
        this.picList = picList;
        LinearLayoutManager llm = recyclerView.getLayoutManager() instanceof LinearLayoutManager ? (LinearLayoutManager) recyclerView.getLayoutManager() : null;
        if (llm != null) {
            loadMore(recyclerView, llm);
        }
    }

    /**
     * 加载更多
     *
     * @param rl  recyclerView
     * @param llm LinearLayoutManager
     */
    private void loadMore(RecyclerView rl, final LinearLayoutManager llm) {
        rl.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalCount = getItemCount(); //一共有多少item
                int lastVisibleItem = llm.findLastVisibleItemPosition(); // 最后一个可见item的索引
                if (!isLoading && lastVisibleItem + 1 >= totalCount) { //不在加载，并且已经滑到最后一个item
                    listener.onLoadMore();
                }
            }

            /**
             * 用来设置滑动不加载图片
             * @param recyclerView recyclerView
             * @param newState 滑动状态 0 停止 1 用户在滑动 2 惯性滑动
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) { //停止
                    Glide.with(MyApplication.context).resumeRequests();
                } else {
                    // 这里应该再加一个用户在屏幕上时是否滑动的配置
                    // 暂时不封装
                    Glide.with(MyApplication.context).pauseRequests();
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        // 根据item的类型创建不同的ViewHolder
        if (viewType == TYPE_ITEM_NORMAL) {
            View itemView = UiUtils.inflate(R.layout.rl_item_normal, parent);
            holder = new RecyclerNormalHolder(itemView);
        } else if (viewType == TYPE_ITEM_HEADER) {
            View itemView = UiUtils.inflate(R.layout.rl_item_header, parent);
            holder = new RecyclerHeaderHolder(itemView);
        } else {
            View itemView = UiUtils.inflate(R.layout.rl_item_load, parent);
            holder = new RecyclerLoadHolder(itemView);
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) { //如果是最后一个item 表示是要加载更多
            return TYPE_ITEM_LOAD;
        } else if (position == 0) {
            return TYPE_ITEM_HEADER;
        } else {
            return TYPE_ITEM_NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RecyclerNormalHolder) {
            //显示item数据
            ((RecyclerNormalHolder) holder).tv_app_name.setText(list.get(position-1).getName());
            ((RecyclerNormalHolder) holder).tv_app_size.setText(MyUtils.getAppSize(list.get(position-1).getSize()));
            ((RecyclerNormalHolder) holder).tv_app_des.setText(list.get(position-1).getDes());
            ((RecyclerNormalHolder) holder).rb_app_star.setRating(list.get(position-1).getStars());
            ((RecyclerNormalHolder) holder).ll_app_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //1 开启服务，传入download url
                    //2 根据下载情况，做界面显示
                }
            });

            // onTouch事件会先在它的父view之前消费，点击然后松手，
            // 调用顺序是1.子view响应touch down 2.父view响应touch up 3.子view响应touch up
            /*((RecyclerNormalHolder) holder).ll_app_download.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    Log.d("#####",view.getClass().getSimpleName()+"action: "+motionEvent.getAction());
                    return true;
                }
            });*/
            String url = MyConstant.BASE_URL + MyConstant.URL_KEY_IMAGE + "?name=" + list.get(position-1).getIconUrl();
            Glide.with(MyApplication.context).load(url).placeholder(R.mipmap.ic_default).into(((RecyclerNormalHolder) holder).iv_app_icon);
        } else if (holder instanceof RecyclerLoadHolder) {
            RecyclerLoadHolder loadHolder = (RecyclerLoadHolder) holder;
            loadHolder.setStateLoad(stateLoad); //设置加载更多的状态
            loadHolder.setLoadVisible(); //设置LoadHolder管理的view的显示
        }else {
            RecyclerHeaderHolder headerHolder = (RecyclerHeaderHolder) holder;
            headerHolder.rollPagerView.setAdapter(new MyLoopHeaderAdapter(headerHolder.rollPagerView,picList));
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 2; //除了集合里的数据，还有一个用来加载更多的item，还有一个头部轮询pager
    }

}
