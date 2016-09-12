package study.luofeng.com.myappmarket.adapter;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.List;

import study.luofeng.com.myappmarket.global.MyApplication;
import study.luofeng.com.myappmarket.global.MyConstant;

/**
 * MyLoopHeaderAdapter
 * Created by weixi on 2016/9/12.
 */
public class MyLoopHeaderAdapter extends LoopPagerAdapter {

    private List<String> list;

    public MyLoopHeaderAdapter(RollPagerView viewPager, List<String> list) {
        super(viewPager);
        this.list = list;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        String url= MyConstant.BASE_URL+MyConstant.URL_KEY_IMAGE+"?name="+list.get(position);
        ImageView view = new ImageView(MyApplication.context);
        ViewPager.LayoutParams layoutParams=new ViewPager.LayoutParams();
        layoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height= ViewGroup.LayoutParams.MATCH_PARENT;
        view.setLayoutParams(layoutParams);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(MyApplication.context).load(url).into(view);
        return view;
    }

    @Override
    public int getRealCount() {
        return list.size();
    }
}
