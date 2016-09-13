package study.luofeng.com.myappmarket.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import study.luofeng.com.myappmarket.R;
import study.luofeng.com.myappmarket.global.MyApplication;
import study.luofeng.com.myappmarket.global.MyConstant;
import study.luofeng.com.myappmarket.ui.holder.RecyclerScreenshotHolder;
import study.luofeng.com.myappmarket.utils.UiUtils;

/**
 * ScreenshotAdapter
 * Created by weixi on 2016/9/13.
 */
public class ScreenshotAdapter extends RecyclerView.Adapter<RecyclerScreenshotHolder>{

    private List<String> list;

    public ScreenshotAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public RecyclerScreenshotHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerScreenshotHolder(UiUtils.inflate(R.layout.screenshot_item,parent));
    }

    @Override
    public void onBindViewHolder(RecyclerScreenshotHolder holder, int position) {
        String url = MyConstant.URL_IMAGE_BASE+list.get(position);
        Glide.with(MyApplication.context).load(url).into(holder.ivScreenshot);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
