package study.luofeng.com.myappmarket.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jude.rollviewpager.RollPagerView;

import study.luofeng.com.myappmarket.R;

/**
 * RecyclerHeaderHolder
 * Created by weixi on 2016/9/12.
 */
public class RecyclerHeaderHolder extends RecyclerView.ViewHolder{

    public RollPagerView rollPagerView;

    public RecyclerHeaderHolder(View itemView) {
        super(itemView);
        rollPagerView  = (RollPagerView) itemView.findViewById(R.id.roll_pager_view);
    }
}
