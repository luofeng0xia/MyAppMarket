package study.luofeng.com.myappmarket.utils;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import study.luofeng.com.myappmarket.ui.fragment.AppFragment;
import study.luofeng.com.myappmarket.ui.fragment.GameFragment;
import study.luofeng.com.myappmarket.ui.fragment.HomeFragment;
import study.luofeng.com.myappmarket.ui.fragment.HotFragment;
import study.luofeng.com.myappmarket.ui.fragment.RecommendFragment;
import study.luofeng.com.myappmarket.ui.fragment.CategoryFragment;
import study.luofeng.com.myappmarket.ui.fragment.SubjectFragment;

/**
 * 创建Fragment对象的工厂
 * Created by weixi on 2016/9/8.
 */
public class FragmentFactory {

    //用来存Fragment对象，避免每次都创建实例
    private static Map<Integer, Fragment> map = new HashMap<>();

    /**
     * 根据传入位置创建相应的Fragment
     *
     * @param position 在tab中的位置
     * @return Fragment
     */
    public static Fragment createFragment(int position) {
        Fragment f = map.get(position);
        if (f == null) { //map中没存，根据位置创建
            switch (position) {
                case 0:
                    f = new HomeFragment();
                    break;
                case 1:
                    f = new AppFragment();
                    break;
                case 2:
                    f = new GameFragment();
                    break;
                case 3:
                    f = new SubjectFragment();
                    break;
                case 4:
                    f = new RecommendFragment();
                    break;
                case 5:
                    f = new CategoryFragment();
                    break;
                case 6:
                    f = new HotFragment();
                    break;
            }
            map.put(position, f);//把创建的Fragment存到map中
        }
        return f;
    }
}
