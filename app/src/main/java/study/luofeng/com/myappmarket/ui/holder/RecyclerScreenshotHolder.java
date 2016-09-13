package study.luofeng.com.myappmarket.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import study.luofeng.com.myappmarket.R;

/**
 * RecyclerScreenshotHolder
 * Created by weixi on 2016/9/13.
 */
public class RecyclerScreenshotHolder extends RecyclerView.ViewHolder{

    public ImageView ivScreenshot;
    public RecyclerScreenshotHolder(View itemView) {
        super(itemView);
        ivScreenshot= (ImageView) itemView.findViewById(R.id.iv_screenshot);
    }
}
