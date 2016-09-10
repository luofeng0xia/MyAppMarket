package study.luofeng.com.myappmarket.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import study.luofeng.com.myappmarket.R;

/**
 * RecyclerLoadHolder
 * Created by weixi on 2016/9/10.
 */
public class RecyclerLoadHolder extends RecyclerView.ViewHolder {

    public TextView tvNoData;
    public TextView tvReLoad;
    public ProgressBar pb;

    //判断上拉加载更多样式的状态
    public static final int STATE_LOADING = 0; //正在加载
    public static final int STATE_LOAD_NODATA = 1; //没有数据
    public static final int STATE_LOAD_ERROR = 2; //加载出错，重新加载

    private int state_load = STATE_LOADING;

    public void setStateLoad(int state_load) {
        this.state_load = state_load;
    }

    public int getState_load() {
        return state_load;
    }

    public RecyclerLoadHolder(View itemView) {
        super(itemView);
        pb = (ProgressBar) itemView.findViewById(R.id.item_load_pb);
        tvNoData = (TextView) itemView.findViewById(R.id.item_load_tv_no);
        tvReLoad = (TextView) itemView.findViewById(R.id.item_load_tv_re);
    }

    public void setLoadVisible(){
        switch (state_load) {
            case STATE_LOADING:
                pb.setVisibility(View.VISIBLE);
                tvReLoad.setVisibility(View.GONE);
                tvNoData.setVisibility(View.GONE);
                break;
            case STATE_LOAD_NODATA:
                pb.setVisibility(View.GONE);
                tvReLoad.setVisibility(View.GONE);
                tvNoData.setVisibility(View.VISIBLE);
                break;
            case STATE_LOAD_ERROR:
                pb.setVisibility(View.GONE);
                tvReLoad.setVisibility(View.VISIBLE);
                tvNoData.setVisibility(View.GONE);
                break;
        }
    }
}
