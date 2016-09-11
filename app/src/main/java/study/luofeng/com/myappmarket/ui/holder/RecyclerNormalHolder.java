package study.luofeng.com.myappmarket.ui.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import study.luofeng.com.myappmarket.R;

/**
 * RecyclerNormalHolder
 * Created by weixi on 2016/9/10.
 */
public class RecyclerNormalHolder extends RecyclerView.ViewHolder {

    public TextView tv_app_name,tv_app_size,tv_app_des;
    public RatingBar rb_app_star;
    public ImageView iv_app_icon;
    public LinearLayout ll_app_download;

    public RecyclerNormalHolder(View itemView) {
        super(itemView);
        tv_app_name= (TextView) itemView.findViewById(R.id.tv_app_name);
        tv_app_size= (TextView) itemView.findViewById(R.id.tv_app_size);
        tv_app_des =(TextView) itemView.findViewById(R.id.tv_app_details);
        rb_app_star= (RatingBar) itemView.findViewById(R.id.rb_app_star);
        iv_app_icon= (ImageView) itemView.findViewById(R.id.iv_app_icon);
        ll_app_download= (LinearLayout) itemView.findViewById(R.id.ll_app_download);
//        cv_app= (CardView) itemView.findViewById(R.id.cv_app);
    }
}
