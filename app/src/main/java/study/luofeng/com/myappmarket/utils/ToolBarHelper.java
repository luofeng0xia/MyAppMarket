package study.luofeng.com.myappmarket.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import study.luofeng.com.myappmarket.R;

/**
 * ToolBarHelper
 * 这个的思路就是在setContentView的时候
 * 把toolbar和子Activity设置的contentView组合在一起
 * 然后把这个组合后的view设置为contentView
 * Created by weixi on 2016/9/12.
 */
public class ToolBarHelper {

    private Toolbar toolbar;
    private FrameLayout container;
    private int[] arrs={
        R.attr.actionBarSize
    };

    /**
     * ToolBarHelper
     * @param context 这里如果是ApplicationContext，toolbar样式无效，设置带？属性则直接error
     * @param contentView 子Activity设置的布局
     */
    public ToolBarHelper(Context context,View contentView) {
        container = new FrameLayout(context);
        container.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //获得toolbar的高度
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(arrs);
        //因为是帧布局，原contentView应该距离状态栏一个toolbar的高度
        params.topMargin = (int) typedArray.getDimension(0,(int)context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        container.addView(contentView, params);
        View view = LayoutInflater.from(context).inflate(R.layout.toolbar,container);
        toolbar= (Toolbar) view.findViewById(R.id.toolbar);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public FrameLayout getContainer() {
        return container;
    }
}
