package study.luofeng.com.myappmarket.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * MyExpandableListView
 * Created by weixi on 2016/9/13.
 */
public class MyExpandableListView extends ExpandableListView{
    public MyExpandableListView(Context context) {
        super(context);
    }

    public MyExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
