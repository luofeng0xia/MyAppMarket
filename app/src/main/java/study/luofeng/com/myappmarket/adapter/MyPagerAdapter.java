package study.luofeng.com.myappmarket.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import study.luofeng.com.myappmarket.R;
import study.luofeng.com.myappmarket.global.MyConstant;
import study.luofeng.com.myappmarket.utils.FragmentFactory;
import study.luofeng.com.myappmarket.utils.UiUtils;

/**
 * MyPagerAdapter
 * Created by weixi on 2016/9/8.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    String[] pagerTab;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        pagerTab=UiUtils.getStringArray(R.array.tab_names);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createFragment(position);
    }

    @Override
    public int getCount() {
        return pagerTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pagerTab[position];
    }
}
