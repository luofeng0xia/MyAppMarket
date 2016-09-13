package study.luofeng.com.myappmarket.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import study.luofeng.com.myappmarket.R;
import study.luofeng.com.myappmarket.bean.AppDetailInfo;
import study.luofeng.com.myappmarket.global.MyApplication;
import study.luofeng.com.myappmarket.global.MyConstant;
import study.luofeng.com.myappmarket.utils.UiUtils;

/**
 * SafeListAdapter
 * Created by weixi on 2016/9/13.
 */
public class SafeListAdapter extends BaseExpandableListAdapter {

    private List<AppDetailInfo.SafeBean> list;
    private final int groupCount = 1;

    public SafeListAdapter(List<AppDetailInfo.SafeBean> list) {
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return groupCount;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        SafeGroupHolder groupHolder;
        if (convertView == null) {
            convertView = UiUtils.inflate(R.layout.safe_group, parent);
            groupHolder = new SafeGroupHolder(convertView);
            for (int i = 0; i < list.size(); i++) {
                AppDetailInfo.SafeBean safeBean = list.get(i);
                ImageView imageView = new ImageView(MyApplication.context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, UiUtils.dp2pix(30));
                params.leftMargin=UiUtils.dp2pix(8);
                params.rightMargin=UiUtils.dp2pix(8);
                params.topMargin=UiUtils.dp2pix(8);
                params.bottomMargin=UiUtils.dp2pix(8);
                groupHolder.llSafe.addView(imageView,params);
                String url = MyConstant.BASE_URL+MyConstant.URL_KEY_IMAGE+"?name="+safeBean.getSafeUrl();
                Glide.with(MyApplication.context).load(url).into(imageView);
            }
        }else {
            groupHolder = (SafeGroupHolder) convertView.getTag();
        }
        groupHolder.ivIndicator.setBackgroundResource(isExpanded?R.mipmap.arrow_up:R.mipmap.arrow_down);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = UiUtils.inflate(R.layout.safe_child, parent);
            SafeChildHolder childHolder = new SafeChildHolder(convertView);
            childHolder.tvDes.setText(list.get(childPosition).getSafeDes());
            String url = MyConstant.BASE_URL+MyConstant.URL_KEY_IMAGE+"?name="+list.get(childPosition).getSafeDesUrl();
            Glide.with(MyApplication.context).load(url).into(childHolder.ivDes);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class SafeGroupHolder {
        private LinearLayout llSafe;
        private ImageView ivIndicator;

        public SafeGroupHolder(View itemView) {
            itemView.setTag(this);
            llSafe= (LinearLayout) itemView.findViewById(R.id.ll_app_safe);
            ivIndicator = (ImageView) itemView.findViewById(R.id.iv_indicator);
        }
    }

    static class SafeChildHolder {
        private TextView tvDes;
        private ImageView ivDes;

        public SafeChildHolder(View itemView) {
            tvDes = (TextView) itemView.findViewById(R.id.tv_app_safedes);
            ivDes= (ImageView) itemView.findViewById(R.id.iv_app_safedes);
        }
    }
}
